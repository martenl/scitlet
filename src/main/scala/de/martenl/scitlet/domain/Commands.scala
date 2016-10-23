package de.martenl.scitlet.domain

/**
  * Created on 16.05.2016.
  */
abstract sealed class Command

case class Error(msg:String) extends Command

case class Init() extends Command

case class Add(file:String) extends Command

case class Rm(file:String,options:List[String]) extends Command

case class Commit(options:List[String]) extends Command

case class Branch(name:String) extends Command

case class Checkout(reference:String) extends Command

case class Diff(reference:String,reference2:String) extends Command

case class Remote() extends Command

case class Fetch(remoteBranch:String,branch:String) extends Command

case class Merge() extends Command

case class Pull() extends Command

case class Push() extends Command

case class Status() extends Command

case class Clone(repoPath:String) extends Command

case class UpdateIndex() extends Command

case class WriteTree() extends Command

case class UpdateRef() extends Command

object Command{

  def apply(args:Option[Array[String]]):Command = {
    args match {
      case None => Error("No command")
      case Some(arguments) if arguments.length == 0 =>Error("No command")
      case Some(arguments) => parseCommand(arguments)
    }
  }

  def parseCommand(args: Array[String]) = {
    val cmd:String = args(0).toLowerCase
    cmd match {
      case "init" => Init()
      case "add" if args.length >= 2 => Add(args(1))
      case "add" => Error("No file to add")
      case "commit" => Commit(args.drop(1).toList)
      case "rm" if args.length >= 2 => Rm(args(1),args.drop(2).toList)
      case "rm" => Error("No file to remove")
      case _ => Error("Unknown command")
    }
  }
}
