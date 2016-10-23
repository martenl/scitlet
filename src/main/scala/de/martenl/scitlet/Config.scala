package de.martenl.scitlet

import java.io.File

import de.martenl.scitlet.Utils._

object Config {

  def createAt(path:String):Unit = {
    Files.createFile(path+File.separator+"config")
  }

  def isBare():Boolean ={
    //read("core")("bare")
    true
  }

  def read():ConfigMap = {
    read(Files.scitletPath()+File.separator+"config")
  }

  def read(configFilePath:String):ConfigMap={
    val configFileContent = Files.read(configFilePath)
    for(line <- configFileContent.split("\n")){
      line.charAt(0) match {
        case '['  => println(s"config entry name: ${line.replace("[","").replace("]","")}")
        case '\t' => println(s"config entry property name: ${line.split("=")(0)} value: ${line.split("=")(1)}")
      }
    }
    ConfigMap(List())
  }

  def objToStr(configData:ConfigData):String = {
    configData match{
      case ConfigEntry(key,value) => key+":"+value
      case ConfigMap(entries) => entries.map(x=>objToStr(x)).mkString("[")
    }
  }

  def strToObj(data:String):ConfigData = {
    val indices = getAllIndices(data,"[")
    ConfigMap(List())
  }

  abstract class ConfigData()

  case class ConfigEntry(key:String,value:String) extends ConfigData

  case class ConfigMap(entries:List[ConfigData]) extends ConfigData {

  }
}
