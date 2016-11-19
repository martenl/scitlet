package de.martenl.scitlet

object Head {

  val path:String = Files.combine(Files.scitletPath(),"HEAD")
  val headsPath = Files.combine(Files.scitletPath(),"refs","heads")

  def get(): Option[String] ={
    Files.existsFile(path) match{
      case true => getByPath(Files.combine(Files.scitletPath(),Files.read(path).replace("ref: ","")))
      case false => None
    }

  }

  def set(name:String,value:String): Unit ={
    Files.write(path,s"ref: refs/heads/$name")
    setByName(name,value)
  }

  def getByPath(refPath:String):Option[String] = {
    Files.existsFile(refPath) match {
      case true => Some(Files.read(refPath).replace("refs: ",""))
      case false => None
    }
  }

  def getByName(name:String):Option[String] = {
    val refPath = Files.combine(headsPath,name)
    getByPath(refPath)
  }

  def setByPath(refPath:String, value:String):Unit = {
    Files.createDirectory(headsPath)
    Files.write(refPath,s"$value\n")
  }

  def setByName(name:String, value:String):Unit = {
    val refPath = Files.combine(headsPath,name)
    setByPath(refPath,value)
  }
}
