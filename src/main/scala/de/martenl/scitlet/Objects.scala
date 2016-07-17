package de.martenl.scitlet

object Objects {

  def createAt(path:String):Unit = {
    val objectsPath:String = Files.combine(path,"objects")
    Files.createDirectory(objectsPath)
    Files.createDirectory(Files.combine(objectsPath,"info"))
    Files.createDirectory(Files.combine(objectsPath,"pack"))
  }
}

class Objects {

  def addFile(path:String):String = {
    val objectFile = Hasher.hashFile(path)
    val directoryName = objectFile.substring(0,2)
    val fileName = objectFile.substring(2)
    val destination:String = Files.scitletPath() + Files.combine("","objects",directoryName)
    Zipper.zipFile(path,destination,fileName)
   objectFile
  }

  def store(scitletObject:Object):Unit={

  }
}
