
package ammonite
package $file.c13
import _root_.ammonite.interp.api.InterpBridge.{
  value => interp
}
import _root_.ammonite.interp.api.InterpBridge.value.{
  exit
}
import _root_.ammonite.interp.api.IvyConstructor.{
  ArtifactIdExt,
  GroupIdExt
}
import _root_.ammonite.runtime.tools.{
  browse,
  grep,
  time,
  tail
}
import _root_.ammonite.repl.tools.{
  desugar,
  source
}
import _root_.ammonite.main.Router.{
  doc,
  main
}
import _root_.ammonite.repl.tools.Util.{
  pathScoptRead
}
import ammonite.$file.c13.{
  MergeSort
}


object TestMergeSort{
/*<script>*/import $file.$        , MergeSort._

val input = Vector("banana", "mandarin", "avocado", "apple", "mango", "cherry", "mangosteen")
/*<amm>*/val res_2 = /*</amm>*/pprint.log(input)
/*<amm>*/val res_3 = /*</amm>*/assert(
  pprint.log(mergeSortParallel(input)) ==
    Vector("apple", "avocado", "banana", "cherry", "mandarin", "mango", "mangosteen")
)

val random = new scala.util.Random(1337)
val shuffledWords = Array.fill(1 * 1000 * 1000)(random.nextInt())

/*<amm>*/val res_6 = /*</amm>*/println("Warming up...")
/*<amm>*/val res_7 = /*</amm>*/mergeSortSequential(shuffledWords)
/*<amm>*/val res_8 = /*</amm>*/mergeSortParallel(shuffledWords)
/*<amm>*/val res_9 = /*</amm>*/mergeSortSequential(shuffledWords)
/*<amm>*/val res_10 = /*</amm>*/mergeSortParallel(shuffledWords)

/*<amm>*/val res_11 = /*</amm>*/println("Benchmarking Sequential vs Parallel Merge Sort...")
val (sequentialResult, sequentialTime) = time{ mergeSortSequential(shuffledWords) }
val (parallelResult, parallelTime) = time{ mergeSortParallel(shuffledWords) }

/*<amm>*/val res_14 = /*</amm>*/pprint.log(parallelTime)
/*<amm>*/val res_15 = /*</amm>*/pprint.log(sequentialTime)
/*<amm>*/val res_16 = /*</amm>*/pprint.log(parallelResult == sequentialResult)
/*<amm>*/val res_17 = /*</amm>*/assert(parallelTime < sequentialTime / 2)/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "TestMergeSort"
  /*</generated>*/
}
