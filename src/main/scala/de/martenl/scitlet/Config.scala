package de.martenl.scitlet

class Config {


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

  abstract case class ConfigData()

  case class ConfigEntry(key:String,value:String) extends ConfigData

  case class ConfigMap(entries:List[ConfigData]) extends ConfigData
}
