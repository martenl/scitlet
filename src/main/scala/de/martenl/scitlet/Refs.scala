package de.martenl.scitlet

import java.io.File

object Refs {

  def createAt(path:String):Unit = {
    Files.createDirectory(path+File.separator+"refs")
  }

  def setRemoteHead(remoteName:String,headName:String,value:String):Unit = {
    val refPath = "refs" + File.separator + "remote" + File.separator + remoteName + File.separator + headName
    Files.write(refPath,value)
  }
}
