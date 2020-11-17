import scala.math.pow
def retry[T](max: Int, delay: Int)(f: => T): T = {
    var tries = 0
    var delay = 500
    var exponent = 2
    var result: Option[T] = None
    var currTime = 0
    var rng = new scala.util.Random()
    while (result == None) {
      try { result = Some(f) }
      catch {case e: Throwable =>
        tries += 1 
        if (tries > max) throw e
        else {
            delay = delay * exponent
            currTime = rng.nextInt(delay)
            println(s"the delay is $currTime")
            Thread.sleep(currTime)
            println(s"failed, retry #$tries") }
        } 
    }
    result.get 
}