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
    /*entries
      .map(indexEntry => (indexEntry.getName(),indexEntry))
      .sortBy((_._1))
      .foldLeft()()*/
    val directories = entries.map(indexEntry => indexEntry.getName().substring(0,indexEntry.getName().lastIndexOf(File.separator)))
    val parents = directories.map(directory => directory.substring(0,directory.lastIndexOf(File.separator))).distinct
    val root:Object = Tree(File.separator,"unhashed","mode",List())
    val addEntryToTree = (tree:Object,entry:IndexEntry)=>{
      val path:Array[String] = entry.getName().split(File.separator)

    }
    val dirToFileMap = entries.groupBy((x:IndexEntry)=>{
      val path = x.getName()
      val lastIndex = path.lastIndexOf(File.separator)
      path.substring(0,lastIndex)
    })
    dirToFileMap.keys.foreach(println(_))
    Blob("","","")
  }
}

case class IndexEntry(name:String,hash:String){

  def getName():String = name

  override def toString(): String ={
    s"${name} ${hash}"
  }
}
