import $ivy.`com.lihaoyi::scalatags:0.9.1`, scalatags.Text.all._
import $ivy.`com.atlassian.commonmark:commonmark:0.13.1`
import java.util.Calendar;
import java.text.SimpleDateFormat;    



@main def main(targetGitRepo: String = ""): Unit = {
  interp.watch(os.pwd / "post")
  val postInfo = os
    .list(os.pwd / "post")
    .map{ p =>
      val s"$prefix - $suffix.md" = p.last
      val format = new SimpleDateFormat("y-M-d")
      val publishedDate = format.format(os.mtime(p))

      (prefix, suffix, p, publishedDate)
    }
    .sortBy(_._1.toInt)
  println(postInfo)
  def mdNameToHtml(name: String) = name.replace(" ", "-").toLowerCase + ".html"

  val bootstrapCss = link(
    rel := "stylesheet",
    href := "https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.css"
  )

  os.remove.all(os.pwd / "out")
  os.makeDir.all(os.pwd / "out" / "post")

  for ((_, suffix, path, publishedDate) <- postInfo) {
    val parser = org.commonmark.parser.Parser.builder().build()
    val document = parser.parse(os.read(path))
    val renderer = org.commonmark.renderer.html.HtmlRenderer.builder().build()
    val output = renderer.render(document)
    val filePath = os.pwd / "out" / "post" / mdNameToHtml(suffix)

    os.write(
      filePath,
      doctype("html")(
        html(
          head(bootstrapCss),
          body(
            h1(a("Blog", href := "../index.html"), " / ", suffix),
            raw(output)
          ),
          p( "Written on " + publishedDate),

        )

      )
    )
  }

  os.write(
    os.pwd / "out" / "index.html",
    doctype("html")(
      html(
        head(bootstrapCss),
        body(
          h1("Blog"),
          for ((_, suffix, _, publishedDate) <- postInfo) 
          yield frag(
            h2(a(href := ("post/" + mdNameToHtml(suffix)))(suffix)),
            p( "Written on " + publishedDate)
          )
        )
      )
    )
  )

  if (targetGitRepo != "") {
    os.proc("git", "init").call(cwd = os.pwd / "out")
    os.proc("git", "add", "-A").call(cwd = os.pwd / "out")
    os.proc("git", "commit", "-am", ".").call(cwd = os.pwd / "out")
    os.proc("git", "push", targetGitRepo, "HEAD", "-f").call(cwd = os.pwd / "out")
  }
}