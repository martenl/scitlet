package de.martenl.scitlet

import java.io._

import scala.collection.mutable
import scala.io.Source

object Files {

  val parentPathName:String = ".scitlet"

  def inRepo():Boolean = {
    checkForScitletPath(getWorkingDirectory()).isDefined
  }

  def assertInRepo():Unit = {
    assert(inRepo(),"Not in repo")
  }

  def pathFromRepoRoot(path:String):String = {
    checkForScitletPath(path) match {
      case Some(repoPath) => path.replace(repoPath,"")
      case None => path
    }
  }

  def absolutePath(path:String):String = {
    checkForScitletPath(getWorkingDirectory()) match {
      case Some(repoPath) => combine(repoPath,path)
      case None => path
    }
  }

  def write(path:String,content:String):Unit = {
    val file = new File(path)
    val bufferedWriter = new BufferedWriter(new FileWriter(file))
    bufferedWriter.write(content)
    bufferedWriter.close()
  }

  def writeFilesFromTree(tree:String,prefix:String):Unit = {

  }

  def rmEmptyDirs(path:String):Unit = {
    val dir = new File(path)
    if(dir.isDirectory){
      dir.listFiles().foreach(file => rmEmptyDirs(file.getAbsolutePath))
      if(dir.listFiles().isEmpty){
        dir.delete()
      }
    }
  }

  def read(path:String):String = {
    Source.fromFile(path).getLines().mkString("\n")
  }

  def scitletPath(path:String=""):String = {
    checkForScitletPath(path) match {
      case Some(foundPath) => combine(foundPath,parentPathName)
      case None =>  combine(getWorkingDirectory(),parentPathName)
    }

  }

  def checkForScitletPath(path:String):Option[String] = {
    val hasScitletPath = (parentPath:String) => {
      new File(combine(parentPath,parentPathName)).exists()
    }
    createParentPaths(path).find(hasScitletPath)
  }

  def workingCopyPath(path:String):String = {
    ""
  }

  def lsRecursive(path:String):List[String] = {
    val file = new File(path)
    file.exists() match {
      case false => List()
      case _ if(file.isFile) => List(path)
      case _ => file.listFiles().toList.flatMap(x => lsRecursive(x.getAbsolutePath))
    }
  }

  def nestFlatTree(filesToContent:Map[String,String]):Unit = {

  }

  def flattenNestedTree(tree:String):Map[String,String] = {
    Map()
  }

  def getWorkingDirectory():String = System.getProperties.getProperty("user.dir")

  def existsFile(path:String):Boolean = new File(path).exists()

  def createFile(path:String):Unit = {
    if(!existsFile(path)){
      new File(path).createNewFile()
    }
  }

  def createDirectory(path:String):Unit = {
    if(!existsFile(path)){
      new File(path).mkdir()
    }
  }

  def createParentPaths(originalPath:String):Array[String] = {
    val b = mutable.Buffer[String]()
    var f = new File(originalPath)
    while(f != null){
      b.append(f.getAbsolutePath)
      f = f.getParentFile
    }
    b.toArray
  }

  def combine(part:String*):String = {
    part.mkString(java.io.File.separator)
  }
}
