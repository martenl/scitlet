package de.martenl.scitlet

import java.io.{File, FileInputStream, FileOutputStream}
import java.util.zip._

object Zipper {

  def zipFile(path:String,destination:String,fileName:String):Unit = {
    zipData(Files.read(path),destination,fileName)
  }

  def zipData(data:String,destination:String,fileName:String)={
    val bytes = data.getBytes
    val bytesToWrite = new Array[Byte](bytes.length)
    val deflater = new Deflater()
    deflater.setInput(bytes)
    deflater.finish()
    val compressedDataLength = deflater.deflate(bytesToWrite)
    deflater.end()
    Files.createDirectory(destination)
    val destFile = destination+File.separator+fileName
    val out = new FileOutputStream(destFile)
    out.write(bytesToWrite,0,compressedDataLength)
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
        def inflate(): String ={
          inflater.inflate(output)
          builder.append(new String(output))
          inflater.finished() match {
            case true => inflater.inflate(output)
              builder.append(new String(output))
              builder.toString()
            case false => inflate()
          }
        }
        inflate()
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
