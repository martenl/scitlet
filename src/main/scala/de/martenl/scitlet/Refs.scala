package de.martenl.scitlet

import java.io.File

/**
 * Created on 08.01.2016.
 */
object Refs {

  def createAt(path:String):Unit = {
    Files.createDirectory(path+File.separator+"refs")
  }
}

class Refs {

}
