package de.martenl.scitlet

import java.io.{BufferedOutputStream, File, FileInputStream, FileOutputStream}
import java.util.zip.{ZipEntry, ZipOutputStream}

/**
  * Created on 03.07.2016.
  */
object Zipper {

  def zipFile(path:String,destination:String,fileName:String):Unit = {
    val file = new File(path)
    val in = new FileInputStream(file)
    val bytes = new Array[Byte](file.length().toInt)
    in.read(bytes)
    in.close()
    Files.createDirectory(destination)
    val destFile = destination+File.separator+fileName
    if(Files.existsFile(destination+File.separator+fileName)){
      return
    }
    val stream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)))
    stream.putNextEntry(new ZipEntry(fileName))
    stream.write(bytes)
    stream.close()
  }
}
