class Msg(val id: Int, val parent: Option[Int], val txt: String)
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
}