
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


object ContextManagers{
/*<script>*/import java.io._

def withFileWriter[T](name: String)(f: java.io.BufferedWriter => T) = {
    val br = new BufferedWriter(new FileWriter(name))
    try f(br)
    finally br.close()
}

def withFileReader[T](name: String)(f: java.io.BufferedReader => T) = {
    val br = new BufferedReader(new FileReader(name))
    try f(br)
    finally br.close()
}/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "ContextManagers"
  /*</generated>*/
}
