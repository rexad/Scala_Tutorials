package machine_learning_with_scala.util

import org.apache.log4j.Logger

import scala.io.Source._
import scala.util.{Failure, Success, Try}

object FileUtils {
  private val logger = Logger.getLogger("FileUtils")

  def read(toFile: String, className: String): Option[String] =
    Try(fromFile(toFile).mkString).toOption

  def write(content: String, pathName: String, className: String): Boolean = {
    import java.io.PrintWriter


    var printWriter: Option[PrintWriter] = None
    var status = false
    Try {
      printWriter = Some(new PrintWriter(pathName))
      printWriter.foreach(_.write(content))
      printWriter.foreach(_.flush)
      printWriter.foreach(_.close)
      status = true
    } match {
      // Catch and display exception description and return false
      case Failure(e) =>
        error(s"$className.write failed for $pathName", logger, e)
        if (printWriter.isDefined) printWriter.foreach(_.close)
        status
      case Success(s) => status
    }
  }
}
