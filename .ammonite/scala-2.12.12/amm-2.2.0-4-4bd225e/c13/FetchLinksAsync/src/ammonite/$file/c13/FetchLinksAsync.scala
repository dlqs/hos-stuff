
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


object FetchLinksAsync{
/*<script>*/import $ivy.$                                            
import scala.concurrent._

val asyncHttpClient = org.asynchttpclient.Dsl.asyncHttpClient()

def fetchLinksAsync(title: String)(implicit ec: ExecutionContext): Future[Seq[String]] = {
  val p = Promise[String]
  val listenableFut = asyncHttpClient.prepareGet("https://en.wikipedia.org/w/api.php")
    .addQueryParam("action", "query").addQueryParam("titles", title)
    .addQueryParam("prop", "links").addQueryParam("format", "json")
    .execute()

  listenableFut.addListener(() => p.success(listenableFut.get().getResponseBody), null)
  val scalaFut: Future[String] = p.future
  scalaFut.map{ responseBody =>
    for{
      page <- ujson.read(responseBody)("query")("pages").obj.values.toSeq
      links <- page.obj.get("links").toSeq
      link <- links.arr
    } yield link("title").str
  }
}
/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "FetchLinksAsync"
  /*</generated>*/
}
