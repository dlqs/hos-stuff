
package ammonite
package $file
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
import _root_.mainargs.{
  arg,
  main
}
import _root_.ammonite.repl.tools.Util.{
  PathRead
}
import ammonite.$file.{
  Arithmetic,
  Traversals
}


object TestArithmetic{
/*<script>*/import $file.$         , Arithmetic._
import $file.$         , Traversals._

val t = fastparse.parse("one plus two times three plus four", parser(_)).get.value

/*<amm>*/val res_3 = /*</amm>*/pprint.log(stringify(t))
/*<amm>*/val res_4 = /*</amm>*/assert(stringify(t) == "(((one plus two) times three) plus four)")
/*<amm>*/val res_5 = /*</amm>*/pprint.log(evaluate(t))
/*<amm>*/val res_6 = /*</amm>*/assert(evaluate(t) == 13)
/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "TestArithmetic"
  /*</generated>*/
}
