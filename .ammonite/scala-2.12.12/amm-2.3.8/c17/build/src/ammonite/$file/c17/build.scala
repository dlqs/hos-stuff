
package ammonite
package $file.c17
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


object build{
/*<script>*/import mill._, scalalib._
trait SyncModule extends ScalaModule {
  def scalaVersion = "2.13.2"
  def ivyDeps = Agg(
    ivy"com.lihaoyi::upickle:1.2.0",
    ivy"com.lihaoyi::os-lib:0.7.1"
  )
}
object shared extends SyncModule
object sync extends SyncModule{
  def moduleDeps = Seq(shared)
  def resources = T.sources{
    os.copy(agent.assembly().path, T.dest / "agent.jar")
    super.resources() ++ Seq(PathRef(T.dest))
  }
  object test extends Tests{ // Test Suite
    def testFrameworks = Seq("utest.runner.Framework")
    def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.4")
  }   
}
object agent extends SyncModule{
  def moduleDeps = Seq(shared)
}/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "build"
  /*</generated>*/
}
