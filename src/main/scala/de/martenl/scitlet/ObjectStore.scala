package de.martenl.scitlet

object ObjectStore {

  def createAt(path:String):Unit = {
    val objectsPath:String = Files.combine(path,"objects")
    Files.createDirectory(objectsPath)
    Files.createDirectory(Files.combine(objectsPath,"info"))
    Files.createDirectory(Files.combine(objectsPath,"pack"))
  }

  def store(scitletObject:Object):Unit={
    scitletObject match {
      case Blob(path,hash,mode) => storeFile(path)
      case tree:Tree => storeTree(tree)
      case commitObject:CommitObject => storeCommitObject(commitObject)
    }
  }

  def storeFile(path:String):String = {
    val objectFile = Hasher.hashFile(path)
    //val directoryName = objectFile.substring(0,2)
    //val fileName = objectFile.substring(2)
    val (directoryName,fileName) = computeLocationPath(objectFile)
    val destination:String = Files.scitletPath() + Files.combine("","objects",directoryName)
    Zipper.zipFile(path,destination,fileName)
    objectFile
  }

  def storeTree(tree: Tree): String = {
    println(tree.getPath())
    val objectFile = Hasher.hashTree(tree.toString())
    println(objectFile)
    tree.objects.foreach {
      case t: Tree => storeTree(t)
      case _ => ;
    }
    //val directoryName = tree.getPath().substring(0,2)
    //val fileName = tree.getPath().substring(2)
    val (directoryName,fileName) = computeLocationPath(objectFile)
    val destination:String = Files.scitletPath() + Files.combine("","objects",directoryName)
    Zipper.zipData(objectFile,destination,fileName)
    tree.getPath()
  }

  def storeCommitObject(commitObject: CommitObject):String = {

    ""
  }
  def computeLocationPath(path:String): Tuple2[String,String] = {
    (path.substring(0,2),path.substring(2))
  }
}
