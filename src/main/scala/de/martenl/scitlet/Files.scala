package de.martenl.scitlet

import java.io._

import scala.io.Source

object Files {

  def inRepo():Boolean = {
    scitletPath().isDefined
  }

  def assertInRepo():Unit = {
    assert(inRepo(),"Not in repo")
  }

  def pathFromRepoRoot(path:String):String = {
    ""
  }

  def write(path:String,content:String):Unit = {
    val file = new File(path)
    val bufferedWriter = new BufferedWriter(new FileWriter(file))
    bufferedWriter.write(content)
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

  def scitletPath(path:String=""):Option[String] = {
    def scitletDir(dir:String):Option[String] = {
      val file = new File(dir)
      if(file.exists()){

      }
      file.isDirectory()

      None
    }

    scitletDir("") match {
      case Some(computedPath:String) => Some(computedPath+File.separator+path)
      case _ => None
    }
  }

  def workingCopyPath(path:String):String = {
    ""
  }

  def lsRecursive(path:String):List[String] = {
    val file = new File(path)
    if(!file.exists){
      List()
    }else if(file.isFile){
      List(path)
    }else{
      file.listFiles().toList.flatMap(x => lsRecursive(x.getAbsolutePath))
    }
  }

  def nestFlatTree(filesToContent:Map[String,String]):Unit = {

  }

  def flattenNestedTree(tree:String):Map[String,String] = {
    Map()
  }


}
