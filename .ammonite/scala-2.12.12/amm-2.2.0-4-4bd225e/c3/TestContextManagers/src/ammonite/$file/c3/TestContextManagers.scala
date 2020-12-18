
package ammonite
package $file.c3
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


object TestContextManagers{
/*<script>*//*<amm>*/val res_0 = /*</amm>*/withFileWriter("File.txt") { writer =>
  writer.write("Hello\n"); writer.write("World!")
}
val result = withFileReader("File.txt") { reader =>
  reader.readLine() + "\n" + reader.readLine()
}
/*<amm>*/val res_2 = /*</amm>*/assert(result == "Hello\nWorld!")
/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "TestContextManagers"
  /*</generated>*/
}
