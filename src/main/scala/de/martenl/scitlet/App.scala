package de.martenl.scitlet

/**
 * Created by Marten on 23.12.2015.
 */
object App {

  def main(args:Array[String]): Unit ={
    Files.lsRecursive("C:\\data\\myFolder").foreach(x => println(x))
  }
}
