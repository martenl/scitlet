package de.martenl.scitlet

/**
 * Created by Marten on 23.12.2015.
 */

import de.martenl.scitlet.domain.{Command, Error}

object App {

  def main(args:Array[String]): Unit = {
    val cmd:Command = Command(args)
    cmd match {
      case Error(msg) => println(msg)
      case _ => Scitlet.cmd(cmd)
    }
  }
}
