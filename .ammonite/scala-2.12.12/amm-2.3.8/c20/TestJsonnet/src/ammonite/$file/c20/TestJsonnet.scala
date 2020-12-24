
package ammonite
package $file.c20
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
import ammonite.$file.c20.{
  Jsonnet
}


object TestJsonnet{
/*<script>*/import $file.$      , Jsonnet.jsonnet

/*<amm>*/val res_1 = /*</amm>*/assert(
  pprint.log(jsonnet(
    """local greeting = "Hello ";
       local person = function (name) {
         "name": name,
         "welcome": greeting + name + "!"
       };
       {
         "person1": person("Alice"),
         "person2": person("Bob"),
         "person3": person("Charlie")
       }"""
  )) ==
  """{
    |  "person1": {
    |    "name": "Alice",
    |    "welcome": "Hello Alice!"
    |  },
    |  "person2": {
    |    "name": "Bob",
    |    "welcome": "Hello Bob!"
    |  },
    |  "person3": {
    |    "name": "Charlie",
    |    "welcome": "Hello Charlie!"
    |  }
    |}""".stripMargin
)
/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "TestJsonnet"
  /*</generated>*/
}
