package de.martenl.scitlet

import java.io.{File, FileInputStream, FileOutputStream}
import java.util.zip._

object Zipper {

  def zipFile(path:String,destination:String,fileName:String):Unit = {
    val content = Files.read(path)
    val bytes = content.getBytes
    val deflater = new Deflater()
    deflater.setInput(bytes)
    val bytesToWrite = new Array[Byte](100)
    Files.createDirectory(destination)
    val destFile = destination+File.separator+fileName
    val out = new FileOutputStream(destFile)
    while(!deflater.finished()){
      deflater.deflate(bytesToWrite)
      out.write(bytesToWrite)
    }
    out.close()
  }

  def unzipFile(path:String):String = {
    Files.existsFile(path) match {
      case true => {
        val bytes = getBytes(path)
        val inflater = new Inflater()
        inflater.setInput(bytes)
        val builder = new StringBuilder()
        val output = new Array[Byte](1024)
        while (!inflater.finished()){
          inflater.inflate(output)
          builder.append(new String(output))
        }
        println(builder.toString().trim.size)
        builder.toString().trim
      }
      case false => "Nothing found"
    }
  }

  def getBytes(path:String):Array[Byte] = {
    val reader:FileInputStream = new FileInputStream(path)
    val bytes = new Array[Byte]( reader.available())
    reader.read(bytes)
    bytes
  }
}
