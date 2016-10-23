package de.martenl.scitlet

import java.io.File

/**
  * Created on 22.10.2016.
  */
object Head {

  val path:String = Files.combine(Files.scitletPath(),"HEAD")

  def getCurrentHead(): String ={
    Files.read(path).replace("ref: ","")
  }

  def setCurrentHead(value:String): Unit ={

  }

  def getHead(name:String):Option[String] = {
    val refPath = "refs" + File.separator + "heads" + File.separator + name
    Files.existsFile(refPath) match {
      case true => Some(Files.read(refPath).replace("refs: ",""))
      case false => None
    }
  }

  def setHead(name:String,value:String):Unit = {
    val refPath = "refs" + File.separator + "heads" + File.separator + name
    Files.write(refPath,s"refs: $value")
  }
}
