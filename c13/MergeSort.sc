import scala.concurrent._, duration.Duration.Inf, java.util.concurrent.Executors
implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))

def mergeSortHelper[T: Ordering](items: IndexedSeq[T]): Future[IndexedSeq[T]] = {
  if (items.length <= 16) 
    Future {
        mergeSortSequential(items)
    }
  else {
    val (left, right) = items.splitAt(items.length / 2)

    // (1 :: Nil).map(_ + 1) == 2 :: Nil
    // (1 :: Nil).flatMap { (_ + 1) :: Nil }

    mergeSortHelper(left).zip(mergeSortHelper(right)).map {
      case (l, r) => merge(l, r)
    }
    return Future {
        val leftResult = Await.result(mergeSortHelper(left), Inf)
        val rightResult = Await.result(mergeSortHelper(right), Inf)
        merge(leftResult, rightResult)
    }
  }
}
def mergeSortParallel[T: Ordering](items: IndexedSeq[T]): IndexedSeq[T] = {
    Await.result(mergeSortHelper(items), Inf)
}

//println(mergeSortParallel(Vector(1, 2,3)))

def merge[T: Ordering](sortedLeft: IndexedSeq[T], sortedRight: IndexedSeq[T]): IndexedSeq[T] = {
    val output = IndexedSeq.newBuilder[T]
    var (leftIdx, rightIdx) = (0, 0)
    while (leftIdx < sortedLeft.length || rightIdx < sortedRight.length) {
      val takeLeft = (leftIdx < sortedLeft.length, rightIdx < sortedRight.length) match {
        case (true, false) => true
        case (false, true) => false
        case (true, true) => Ordering[T].lt(sortedLeft(leftIdx), sortedRight(rightIdx))
      }
      if (takeLeft) {
        output += sortedLeft(leftIdx)
        leftIdx += 1
      } else {
        output += sortedRight(rightIdx)
        rightIdx += 1
      }
    }
    output.result()
}

def mergeSortSequential[T: Ordering](items: IndexedSeq[T]): IndexedSeq[T] = {
  if (items.length <= 1) items
  else {
    val (left, right) = items.splitAt(items.length / 2)
    return merge(mergeSortSequential(left), mergeSortSequential(right))
  }
}