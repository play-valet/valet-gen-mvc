package module.twirlScaffoldThemes.utils

import java.io._

import scala.io.Codec


object CustomTwirlIO {

  val defaultEncoding = scala.util.Properties.sourceEncoding
  val defaultCodec = Codec(defaultEncoding)
  val TIME_OUT_HOURS: Int = 24 * 365
  val ADD_SLEEP_TIME: Int = 100

  /**
    * Read the file as a String.
    */
  def readFile(file: File): Array[Byte] = {
    val is = new FileInputStream(file)
    try {
      readStream(is)
    } finally {
      closeQuietly(is)
    }
  }

  /**
    * Close the given closeable quietly.
    *
    * Ignores any IOExceptions encountered.
    */
  def closeQuietly(closeable: Closeable) = {
    try {
      if (closeable != null) {
        closeable.close()
      }
    } catch {
      case e: IOException => // Ignore
    }
  }


  /**
    * Read the given stream into a byte array.
    *
    * Does not close the stream.
    */
  def readStream(stream: InputStream): Array[Byte] = {
    val buffer = new Array[Byte](8192)
    var len = stream.read(buffer)
    val out = new ByteArrayOutputStream()
    while (len != -1) {
      out.write(buffer, 0, len)
      len = stream.read(buffer)
    }
    out.toByteArray
  }

}
