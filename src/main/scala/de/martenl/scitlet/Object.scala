package de.martenl.scitlet

import java.io.File

abstract sealed class Object()

case class Blob(val path:String,val hash:String,val mode:String) extends Object

case class Tree(val path:String,val hash:String,val mode:String,val objects:List[Object]) extends Object {

  def getChild(path:Array[String]):Option[Object] = {

    val optional = objects.find((o:Object)
    => o match {
        case Blob(name,_,_) if path.length==1 => name.equals(path(0))
        case Tree(name,_,_,_)  => name.equals(path(0))
        case _  => false
    })

    optional
  }
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
    if(new File(path).isFile ){
     return Some(createBlob(path))
    }else if(new File(path).isDirectory){
      Some(createTree(path))
    }
    None
  }

}