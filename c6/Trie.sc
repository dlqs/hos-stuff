class ImmutableTrie(s: Seq[String]) {
  class Node(index: Int, inputs: Seq[String]) {
      val hasValue = inputs.exists( _.length == index)
      val children = {
        val filteredInputs = inputs.filter(_.length > index)
        for((childChar, childInputs) <- filteredInputs.groupBy(_.charAt(index))) // "b", ("abc", "abd")
        yield (childChar, new Node(index + 1, childInputs)) 
      }
  }

  val root = new Node(0, s)
  // [("a",  ["abc", "abd"]), ("b", ["bcd"])]
  // []
  
  def contains(s: String): Boolean = {
    val current = root
    val index = 0
    while (current.inputs.contains(s) && current.nonEmpty) {
        current = current.children.find(_._1 == s.charAt(index))
        index = index + 1
    }
    current.hasValue
  }
  def prefixesMatchingString(s: String): Set[String] = {
    Set("")
  }
  def stringsMatchingPrefix(s: String): Set[String] = {
    Set("")

  }
}