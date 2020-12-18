import $file.FetchLinksAsync, FetchLinksAsync._
import $ivy.`com.lihaoyi::castor:0.1.7`
import scala.concurrent._
import scala.collection.mutable.ArrayDeque

sealed trait Msg
case class Start(title: String) extends Msg
case class Fetch(titles: Seq[String], depth: Int) extends Msg

class Crawler(maxDepth: Int, complete: Promise[Set[String]], diskActor: DiskActor, maxConcurrency: Int)
             (implicit cc: castor.Context) extends castor.SimpleActor[Msg] {
  var seen = Set.empty[String]
  var outstanding = 0
  var queue = new ArrayDeque[String]
  def run(msg: Msg) = msg match{
    case Start(title) => handle(Seq(title), 0)
    case Fetch(titles, depth) =>
      outstanding -= 1
      handle(titles, depth)
  }
  def fetch(title: String, depth:Int) = {
    outstanding += 1
    this.sendAsync(fetchLinksAsync(title).map(Fetch(_, depth + 1)))
  }
  def handle(titles: Seq[String], depth: Int): Unit = {
    queue.appendAll(titles)
    val (first, rest) = queue.splitAt(maxConcurrency)
    queue = rest
    for(title <- first if !seen.contains(title)) {
      if (depth < maxDepth) {
        diskActor.send(title)
        fetch(title, depth)
      }
      pprint.log(title)
      seen += title
    }
    if (!queue.isEmpty) {
      handle(Seq(), depth)
    } else {
      if (outstanding == 0) {
       complete.success(seen)
      }
    }
  }
}

def fetchAllLinksAsync(startTitle: String, depth: Int, maxConcurrency: Int): Future[Set[String]] = {
  val complete = Promise[Set[String]]
  implicit val cc = new castor.Context.Test()
  val diskActor = new DiskActor(os.pwd / "log.txt")

  val crawler = new Crawler(depth, complete, diskActor, maxConcurrency)

  crawler.send(Start(startTitle))
  complete.future
}

class DiskActor(logPath: os.Path)
               (implicit cc: castor.Context) extends castor.SimpleActor[String]{
  def run(s: String) = {
    os.write.append(logPath, s + "\n", createFolders = true)
  }
}






