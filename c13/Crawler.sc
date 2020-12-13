import scala.concurrent._, duration.Duration.Inf, java.util.concurrent.Executors
import $ivy.`org.asynchttpclient:async-http-client:2.5.2`
import scala.concurrent._
implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))

def fetchAllLinksAsync(startTitle: String, depth: Int, maxConcurrency: Int): Future[Set[String]] = {
  def rec(current: Set[String], seen: Set[String], recDepth: Int): Future[Set[String]] = {
    if (recDepth >= depth) Future.successful(seen)
    else {
        val (currTitles, restTitles) = current.splitAt(maxConcurrency)
        rec(restTitles, seen, recDepth + 1)
        val futures = for (title <- currTitles) yield fetchLinksAsync(title)
        Future.sequence(futures).map{nextTitleLists =>
          val nextTitles = nextTitleLists.flatten
          rec(nextTitles.filter(!seen.contains(_)), seen ++ nextTitles, recDepth + 1)
        }.flatten
    }
  }
  rec(Set(startTitle), Set(startTitle), 0)
}

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