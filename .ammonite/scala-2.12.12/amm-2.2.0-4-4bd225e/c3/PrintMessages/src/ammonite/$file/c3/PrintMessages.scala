
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


object PrintMessages{
/*<script>*/class Msg(val id: Int, val parent: Option[Int], val txt: String)
def printMessages(messages: Array[Msg]): Unit = {
    def helper(parent: Option[Int], spaces: String) {
        println("helper called with parent: " + parent)
        for (msg <- messages) {
            if (msg.parent == parent) {
                println(spaces + msg.id + " " + msg.txt)
                helper(Some(msg.id), spaces + " ")
                // helper(0, " ")
            }
        }
    }
    helper(None, "")
}/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "PrintMessages"
  /*</generated>*/
}
