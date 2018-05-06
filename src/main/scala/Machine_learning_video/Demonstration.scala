package Machine_learning_video

import breeze.linalg.DenseMatrix
import com.quantifind.charts.Highcharts._
import scala.io.Source
import breeze.plot._
import org.saddle.{Frame, Mat, Vec}

object Demonstration extends App {

  val resourcePath = getClass.getResourceAsStream("/boston_housing.data")
  val lines = Source.fromInputStream(resourcePath).getLines

  val data = lines.map(x => line2Data(x))
  val vecs = data.map(r => row2Vec(r))
  val matr = Mat(vecs.toArray)
  val fr = Frame(matr.transpose)

  val output =fr.col(13)

  println(s"${output.mean}")
  println(s"${output.variance}")
  println(s"${output.median}")
  println(s"${output.max}")
  println(s"${output.min}")

  val f = Figure()
  f.width = 800
  f.height = 800

  val columns = Vector("CRIM", "ZN", "INDUS", "CHAS", "NOX", "RM", "AGE", "DIS", "RAD", "TAX", "PTRATIO", "B", "LSTAT", "MEDV")
  //val xs = for (x <- fr.colAt(0).toSeq) yield x._2
  val ys = for (y <- fr.colAt(13).toSeq) yield y._2
  println(ys)

  def row2Vec(row: List[Double]) :Vec[Double] = Vec(row:_*)

  def line2Data(line: String): List[Double] = {

    line
      .split("\\s+")
      .filter(_.length > 0)
      .map(_.toDouble)
      .toList
  }

}
