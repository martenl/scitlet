package de.martenl.scitlet

import java.math.BigInteger
import java.security.MessageDigest

/**
  * Created on 03.07.2016.
  */
object Hasher {

  val hasher:MessageDigest = MessageDigest.getInstance("sha-1")

  def hashFile(path:String):String = {
    val content = Files.read(path)
    val store = s"blob ${content.length}\\0$content"
    hashString(store)
  }

  def hashTree(content: String) = {
    val store = s"tree ${content.length}\\0$content"
    hashString(store)
  }

  private def hashString(data:String):String = {
    hasher.update(data.getBytes())
    String.format("%x",new BigInteger(1,hasher.digest()))
  }
}
