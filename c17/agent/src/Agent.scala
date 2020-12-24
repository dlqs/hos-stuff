package sync

import Rpc.subPathRw

object Agent {
  def main(args: Array[String]): Unit = {
    val input = new java.io.DataInputStream(new java.util.zip.GZIPInputStream(System.in))
    val output = new java.io.DataOutputStream(new java.util.zip.GZIPOutputStream(System.out, true))
    while (true) try {
      val rpc = Shared.receive[Rpc](input)
      rpc match {
        case Rpc.ListDest() =>
          Shared.send(output, os.walk(os.pwd).map(_.subRelativeTo(os.pwd)))
        case Rpc.Delete(path) =>
          Shared.send(output, os.remove(os.pwd / path))
        case Rpc.IsDir(path) => Shared.send(output, os.isDir(os.pwd / path))
        case Rpc.Exists(path) => Shared.send(output, os.exists(os.pwd / path))
        case Rpc.ReadBytes(path) => Shared.send(output, os.read.bytes(os.pwd / path))
        case Rpc.WriteOver(bytes, path) =>
          os.remove.all(os.pwd / path)
          Shared.send(output, os.write.over(os.pwd / path, bytes, createFolders = true))
      }
    } catch {case e: java.io.EOFException => System.exit(0)}
  }
}