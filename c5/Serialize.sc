
trait StrWriter[T]{ def write(input: T): String}
object StrWriter {
    implicit object WriteInt extends StrWriter[Int] {
        def write(int: Int ) = int.toString
    }
    implicit object WriteBoolean extends StrWriter[Boolean] {
        def write(bool: Boolean) = bool.toString
    }
    implicit object WriteDouble extends StrWriter[Double] {
        def write(double: Double ) = double.toString
    }
}
implicit def WriteSeq[T](implicit w: StrWriter[T]) = new StrWriter[Seq[T]] {
    def write(seq: Seq[T]) = "[" + seq.map(w.write).mkString(",") + "]"
}

implicit def ParseTuple[T,V](implicit w1: StrWriter[T], w2: StrWriter[V]) =
    new StrWriter[(T,V)] {
        def write(a: (T, V)) = {
            "[" + w1.write(a._1) + "," + w2.write(a._2) + "]" 
    }
}


def writeToString[T](seq: T)(implicit writer: StrWriter[T]) = {
    writer.write(seq)
}