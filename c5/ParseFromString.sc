import scala.collection.mutable.Stack
import scala.collection.mutable.ArrayBuffer
trait StrParser[T]{ def parse(s: String): T }
object StrParser{
  implicit object ParseInt extends StrParser[Int]{
    def parse(s: String) = s.toInt
  }
  implicit object ParseBoolean extends StrParser[Boolean]{
    def parse(s: String) = s.toBoolean
  }
  implicit object ParseDouble extends StrParser[Double]{
    def parse(s: String) = s.toDouble
  }
}

implicit def ParseTuple[T, V](implicit p1: StrParser[T], p2: StrParser[V]) =
  new StrParser[(T, V)]{
    def parse(s: String) = {
      val Array(left, right) = s.split('=')
      (p1.parse(left), p2.parse(right))
    }
  }

implicit def ParseSeq[T](implicit p: StrParser[T]) = new StrParser[Seq[T]]{
  def parse(s: String) = {
      var stack = new Stack[Int]
      val result = new ArrayBuffer[T]
      for ((c, i) <- s.zipWithIndex) {
          c match {
              case '[' => {
                stack.push(i.toInt)
                
              }
              case ']' => {
                  val opening = stack.pop()
                  val mid = s.substring(opening + 1, i)
                  val res = mid.split(',').toSeq.map(p.parse)
                  result.add(res)
              }
              case _ => 
          }
      }
      result.toSeq
  }
}

def parseFromString[T](s: String)(implicit parser: StrParser[T]) = {
  parser.parse(s)
}
  
// [(0, 11),(1, 5),(7, 10)]
// val arr = [[0, 6], 12]
// [arr[i -1] + 1, arr[i]]  =  substr[1,6] 
// substr[7, 12]

println(parseFromString[Seq[Int]]("[[1,2],[3, 4]]"))
//println(parseFromString[Seq[Int]]("[[1,2,3,4,5],[true,false,true]]"))