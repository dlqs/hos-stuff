package sync

import Rpc.subPathRw

object Sync {
  def main(args: Array[String]): Unit = {
    val src = os.Path(args(0), os.pwd)
    val dest = os.Path(args(1), os.pwd)
    val agentExecutable = os.temp(os.read.bytes(os.resource / "agent.jar"))
    os.perms.set(agentExecutable, "rwx------")
    val agent = os.proc(agentExecutable).spawn(cwd = dest)
    val agentIn = new java.io.DataOutputStream(new java.util.zip.GZIPOutputStream(agent.stdin, true))
    lazy val agentOut = new java.io.DataInputStream(new java.util.zip.GZIPInputStream(agent.stdout))

    def callAgent[T: upickle.default.Reader](rpc: Rpc): () => T = {
      Shared.send(agentIn, rpc)
      () => Shared.receive[T](agentOut)
    }

    val subPaths = os.walk(src).map(_.subRelativeTo(src))

    def pipelineCalls[T: upickle.default.Reader](rpcFor: os.SubPath => Option[Rpc]) = {
      val buffer = collection.mutable.Buffer.empty[(os.RelPath, () => T)]
      for (p <- subPaths; rpc <- rpcFor (p)) buffer.append((p, callAgent[T](rpc)))
      buffer.map{case (k, v) => (k, v())}.toMap
    }

    val existsMap = pipelineCalls[Boolean](p => Some(Rpc.Exists(p)))
    val isDirMap = pipelineCalls[Boolean](p => Some(Rpc.IsDir(p)))
    val readMap = pipelineCalls[Array[Byte]]{p =>
      if (existsMap(p) && !isDirMap(p)) Some(Rpc.ReadBytes(p))
      else None
    }
    val destPath: Seq[os.SubPath] = callAgent[Seq[os.SubPath]](Rpc.ListDest()).apply()
    val diffPath = destPath diff subPaths
    for (p <- diffPath) {
      callAgent[Unit](Rpc.Delete(p)).apply()
    }
    pipelineCalls[Unit]{ p =>
      if (os.isDir(src / p)) None
      else {
        val localBytes = os.read.bytes(src / p)
        if (readMap.get(p).exists(java.util.Arrays.equals(_, localBytes))) None
        else Some(Rpc.WriteOver(localBytes, p))
      }
    }
  }
}