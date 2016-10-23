package de.martenl.scitlet

import java.io.File

import de.martenl.scitlet.domain._

object Scitlet {

  lazy val index:Index = Index(Files.scitletPath()+File.separator+"index")

  def cmd(command:Command):Unit = {
    command match {
      case Init() => init()
      case Add(path) => add(path)
      case Rm(path,options) => rm(path,options)
      case Commit(options) => commit()
    }
  }

  def init():Unit = {
    val directoryPath:String = Files.getWorkingDirectory()
    val scitletPath: String = directoryPath + File.separator + ".scitlet"
    Files.createDirectory(scitletPath)
    Objects.createAt(scitletPath)
    Refs.createAt(scitletPath)
    Index.createAt(scitletPath)
    Files.createFile(scitletPath+File.separator+"HEAD")
    Config.createAt(scitletPath)
    println(s"initing git repository at ${directoryPath}")
  }

  def add(path:String):Unit = {
    //are we in a scitlet repository?
    if(!Files.inRepo()){
      println("You must be in a scitlet repository to add a file.\nTo create a scitlet repository run scitlet init")
      System.exit(1)
    }
    //does the file exist?
    val absolutePath:String = Files.absolutePath(path)
    if(!Files.existsFile(absolutePath)){
      println(s"${path} does not exist")
      System.exit(1)
    }
    val reachableFiles:List[String] = Files.lsRecursive(absolutePath)
    reachableFiles
      .map(filePath => Files.pathFromRepoRoot(filePath))
      .foreach(
        filePath =>{
            //was the file already added?
          if(index.contains(filePath)){
            println(s"File ${filePath} was already added")
            System.exit(1)
          }
          println(s"adding ${filePath} to git repository")
          val absoluteFilePath:String = Files.absolutePath(filePath)
          val hash:String = Objects.addFile(absoluteFilePath)
          index.add(filePath,hash).save()
      }
    )
  }

  def rm(path:String,options:List[String]):Unit = {
    println(s"removing ${path} from git repository")
    index.remove(path)
  }

  def commit():Unit = {
    println(index.computeTreeGraph())
  }

  def branch():Unit = {

  }

  def checkout():Unit = {

  }

  def diff():Unit = {

  }

  def remote():Unit = {

  }

  def fetch():Unit = {

  }

  def merge():Unit = {

  }

  def pull():Unit = {

  }

  def push():Unit = {

  }

  def status():Unit = {

  }

  def cloneRepository():Unit = {

  }

  def updateIndex():Unit = {

  }

  def writeTree():Unit = {

  }

  def updateRef():Unit = {

  }

}
