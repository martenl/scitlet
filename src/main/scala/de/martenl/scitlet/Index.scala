package de.martenl.scitlet


import java.io.{BufferedWriter, File, FileWriter}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source



object Index {

  def createAt(path:String) = {
    Files.createFile(path+File.separator+"index")
  }

  def stringToIndexEntry(line:String):IndexEntry = {
    val splitted = line.split(" ")
    IndexEntry(splitted(0),splitted.drop(1).mkString(" "))
  }

  def apply(indexPath:String):Index = {
    if(!Files.existsFile(indexPath)){
      createAt(indexPath)
    }
    val source = Source.fromFile(indexPath)
    val entries:mutable.Buffer[IndexEntry] = source.getLines().map(x => stringToIndexEntry(x.toString)).toBuffer
    source.close()
    new Index(entries,indexPath)
  }
}

class Index(val entries:mutable.Buffer[IndexEntry], val path:String) {

  val addedEntries:mutable.Buffer[IndexEntry] = new ListBuffer[IndexEntry]

  def remove(path: String): Unit = {

  }

  def contains(path: String): Boolean = {
    val inEntries:Boolean = entries.exists(entry => entry.getName().equals(path))
    val inAddedEntries:Boolean = addedEntries.exists(entry => entry.getName().equals(path))
    inEntries || inAddedEntries
  }

  def indexOf(path:String) = {
    if(entries.exists(_.getName().equals(path))){
      entries.indexWhere(_.getName().startsWith(path))
    }else if(addedEntries.exists(_.getName().equals(path))){
      entries.size + addedEntries.indexWhere(_.getName().equals(path))
    }else{
      -1
    }

  }

  def add(name:String,hash:String):Index = {
    addedEntries += IndexEntry(name,hash)
    this
  }

  def save(): Unit ={
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file,true))
    addedEntries.foreach(entry => bw.write(entry.toString()+"\n"))
    addedEntries.remove(0,addedEntries.length)
    bw.close()
  }

  def computeTreeGraph():Object = {
    val directories = entries.map(indexEntry => indexEntry.getName().substring(0,indexEntry.getName().lastIndexOf(File.separator)))

    val allDirectories = directories
      .flatMap(directory => directory :: directoryToSuperDirectories(directory))
      .distinct
      .filterNot(_.equals(""))

    val dirToFileMap = entries.groupBy((x:IndexEntry)=>{
      val path = x.getName()
      val lastIndex = path.lastIndexOf(File.separator)
      path.substring(0,lastIndex)
    })

    val dirToSubdir = allDirectories.groupBy(directory => {
      val indexRaw = directory.lastIndexOf(File.separator)
      val index = if(indexRaw != -1) indexRaw else 0
      directory.substring(0,index)
    })

    def buildTreeNode(path: String):Tree = {
      val blobs:Seq[HasPath] = dirToFileMap.getOrElse(path,List()).map(entry => Blob(entry.getName(),entry.hash,"")).toList
      val subDirs:Seq[HasPath] = dirToSubdir.getOrElse(path,List()).map(subDir =>buildTreeNode(subDir))
      val children = (subDirs ++ blobs).sortWith((a,b)=>indexOf(a.getPath())>indexOf(b.getPath()) ).toList
      val hash = Hasher.hashTree(children.map(_.toString()).mkString("\n"))
      Tree(path,hash,"",children)
    }

    buildTreeNode("")
  }

  def directoryToSuperDirectories(directory:String):List[String] = {
    def getIndices(data:String):List[Int] = getIndicesH(data,0,List())
    def getIndicesH(data:String,lastIndex:Int,indices:List[Int]):List[Int] = {
      data.indexOf(File.separator,lastIndex) match {
        case -1 => indices
        case newIndex => getIndicesH(data,newIndex+1,newIndex :: indices)
      }
    }
    val indices:List[Int] = getIndices(directory)
    indices.map(index => directory.substring(0,index))
  }
}

case class IndexEntry(name:String,hash:String){

  def getName():String = name

  override def toString(): String ={
    s"$name $hash"
  }
}
