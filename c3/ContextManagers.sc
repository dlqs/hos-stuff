import java.io._

def withFileWriter[T](name: String)(f: java.io.BufferedWriter => T) = {
    val br = new BufferedWriter(new FileWriter(name))
    try f(br)
    finally br.close()
}

def withFileReader[T](name: String)(f: java.io.BufferedReader => T) = {
    val br = new BufferedReader(new FileReader(name))
    try f(br)
    finally br.close()
}