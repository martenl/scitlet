package de.martenl.scitlet

import java.io.{File, FileInputStream}
import java.math.BigInteger
import java.security.MessageDigest

/**
  * Created on 03.07.2016.
  */
object Hasher {

  val hasher:MessageDigest = MessageDigest.getInstance("sha-1")

  def hashFile(path:String):String = {
    val file = new File(path)
    val in = new FileInputStream(file)
    val bytes = new Array[Byte](file.length().toInt)
    in.read(bytes)
    in.close()
    hasher.update(bytes)
    String.format("%x",new BigInteger(1,hasher.digest()))
  }

  def hashString(data:String):String = {
    hasher.update(data.getBytes())
    String.format("%x",new BigInteger(1,hasher.digest()))
  }
}
