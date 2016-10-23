package de.martenl.scitlet

import java.io.File

abstract sealed class Object(){

}

trait HasPath{
  def getPath():String
}

case class Blob(val path:String,val hash:String,val mode:String) extends Object with HasPath{

  override def toString():String = {
    s"$mode blob $hash $path"
  }

  override def getPath(): String = path
}

case class Tree(val path:String,val hash:String,val mode:String,val objects:List[HasPath]) extends Object with HasPath{

  /*def getChild(path:Array[String]):Option[Object] = {

    val optional = objects.find {
      case Blob(name, _, _) if path.length == 1 => name.equals(path(0))
      case Tree(name, _, _, _) => name.equals(path(0))
      case _ => false
    }
    optional
  }*/
  override def toString():String = {
    s"$path $hash ${objects.size}"
  }

  override def getPath(): String = path
}

case class CommitObject(val tree:String,val hash:String,val commiter:String,val parent:Option[String]) extends Object

object Object{

  def createBlob(path: String): Object = {
    Blob(path,Hasher.hashFile(path), "my-mode")
  }

  def createTree(path: String): Object = {
    Tree("my-path","my-hash","my-mode",List())
  }

  def apply(path:String):Option[Object] = {
    val file = new File(path)
    file.isFile match {
      case true => Some(createBlob(path))
      case _ if file.isDirectory => Some(createTree(path))
      case _ => None
    }
  }

}