
package ammonite
package $file.c7
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


object StreamingDownloadProcessReupload{
/*<script>*/val download = os.proc( "curl", "https://api.github.com/repos/lihaoyi/mill/releases").spawn()
val base64 = os.proc("base64").spawn(stdin = download.stdout)
val gzip = os.proc("gzip").spawn(stdin = base64.stdout)
val tee = os.proc("tee", "foo").spawn(stdin = gzip.stdout)

val upload = os
  .proc("curl", "-X", "PUT", "-d", "@-", "https://httpbin.org/anything")
  .spawn(stdin = tee.stdout)


val contentLength = upload.stdout.lines.filter(_.contains("Content-Length"))
/*<amm>*/val res_6 = /*</amm>*/pprint.log(contentLength)/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "StreamingDownloadProcessReupload"
  /*</generated>*/
}
