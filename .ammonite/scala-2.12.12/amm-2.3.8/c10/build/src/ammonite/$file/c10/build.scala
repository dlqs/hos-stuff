
package ammonite
package $file.c10
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
/*<script>*/import mill._

trait FooModule extends Module {

    def srcs = T.source(millSourcePath / "src")
    def resources = T.source(millSourcePath / "resources")

    def concat = T{
        os.write(T.dest / "concat.txt", os.list(srcs().path).map(os.read(_)))
        PathRef(T.dest / "concat.txt")
    }

    def compress = T{
        for (p <- os.list(resources().path)) {
            val copied = T.dest / p.relativeTo(resources().path)
            os.copy(p, copied)
            os.proc("gzip", copied).call()
        }
        PathRef(T.dest)
    }

    def zipped = T{
        val temp = T.dest / "temp"
        os.makeDir(temp)
        os.copy(concat().path, temp / "concat.txt")
        for (p <- os.list(compress().path)) os.copy(p, temp / p.relativeTo(compress().path))
        os.proc("zip", "-r", T.dest / "out.zip", ".").call(cwd = temp)
        PathRef(T.dest / "out.zip")
    }
}

object bar extends FooModule
object qux extends FooModule/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "build"
  /*</generated>*/
}
