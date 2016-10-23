package de.martenl.scitlet

object Utils {

  def getAllIndices(data:String,c:String):List[Int] = {
    def getAllIndicesHelp(start:Int,accu:List[Int]):List[Int] = {
      start match {
        case -1 => accu
        case pos:Int => getAllIndicesHelp(data.indexOf(c,start+1),accu ::: List(start))
      }
    }
    getAllIndicesHelp(0,List())
  }

  def getAllIndices(data:String,c:Char):List[Int] = getAllIndices(data,c.toString)

}
