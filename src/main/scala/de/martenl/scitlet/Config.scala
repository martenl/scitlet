package de.martenl.scitlet

import java.io.File

object Config {

  def createAt(path:String):Unit = {
    Files.createFile(path+File.separator+"config")
  }
  def isBare():Boolean ={
    true
  }


  def objToStr(configData:ConfigData):String = {
    configData match{
      case ConfigEntry(key,value) => key+":"+value
      case ConfigMap(entries) => entries.map(x=>objToStr(x)).mkString("[")
    }
  }

  def strToObj(data:String):ConfigData = {
    ConfigMap(List())
  }

  abstract class ConfigData()

  case class ConfigEntry(key:String,value:String) extends ConfigData

  case class ConfigMap(entries:List[ConfigData]) extends ConfigData
}
