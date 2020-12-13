def sync(src: os.Path, dest: os.Path) = {
  for (srcSubPath <- os.walk(src)) {
    val subPath = srcSubPath.subRelativeTo(src)
    val destSubPath = dest / subPath
    (os.isDir(srcSubPath), os.isDir(destSubPath)) match {
      case (false, true) | (true, false) =>
        os.copy.over(srcSubPath, destSubPath, createFolders = true)
      case (false, false)
        if !os.exists(destSubPath)
        || !os.read.bytes(srcSubPath).sameElements(os.read.bytes(destSubPath)) =>

        os.copy.over(srcSubPath, destSubPath, createFolders = true)
        

      case _ => // do nothing
    }
  }
  val srcSubPaths = os.walk(src).map(_.subRelativeTo(src)).toSet

  for (destSubPath <- os.walk(dest)) {
      val destPath = destSubPath.subRelativeTo(dest)
      if (!srcSubPaths.contains(destPath)) {
          os.remove(dest / destPath)
      }
      
  }
}
//  1 2 3
//    2 3 4


// src/cat/dog
// { cat/dog }

// dest/cat/dog