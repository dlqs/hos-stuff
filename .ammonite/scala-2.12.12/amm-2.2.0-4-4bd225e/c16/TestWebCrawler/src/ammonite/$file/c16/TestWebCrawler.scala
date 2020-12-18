
package ammonite
package $file.c16
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
import ammonite.$file.c16.{
  WebCrawler
}


object TestWebCrawler{
/*<script>*/import $file.$         , WebCrawler._
import scala.concurrent._, duration.Duration.Inf

val depth0Results = pprint.log(Await.result(fetchAllLinksAsync("Singapore", 0), Inf))
val depth1Results = pprint.log(Await.result(fetchAllLinksAsync("Singapore", 1), Inf))
val depth2Results = pprint.log(Await.result(fetchAllLinksAsync("Singapore", 2), Inf))
//val depth3Results = pprint.log(Await.result(fetchAllLinksAsync("Singapore", 3), Inf))

/*<amm>*/val res_5 = /*</amm>*/pprint.log(depth0Results.size)
/*<amm>*/val res_6 = /*</amm>*/pprint.log(depth1Results.size)
/*<amm>*/val res_7 = /*</amm>*/pprint.log(depth2Results.size)
//pprint.log(depth3Results.size)

/*<amm>*/val res_8 = /*</amm>*/assert(depth0Results == Set("Singapore"))
/*<amm>*/val res_9 = /*</amm>*/assert(
  depth1Results ==
  Set(
    "1954 National Service riots",
    "16th Summit of the Non-Aligned Movement",
    "126 Squadron, Republic of Singapore Air Force",
    "+65",
    "1915 Singapore Mutiny",
    "1962 Merger Referendum of Singapore",
    "13th Parliament of Singapore",
    "Singapore",
    "1964 race riots in Singapore",
    "1959 Singaporean general election",
    ".sg"
  )
)

/*<amm>*/val res_10 = /*</amm>*/assert(depth1Results.subsetOf(depth2Results))
/*<amm>*/val res_11 = /*</amm>*/assert(depth1Results.size < depth2Results.size)

//assert(depth2Results.subsetOf(depth3Results))
//assert(depth2Results.size < depth3Results.size)
/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "TestWebCrawler"
  /*</generated>*/
}
