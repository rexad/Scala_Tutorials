package Machine_learning_video

import breeze.linalg.DenseMatrix
import com.quantifind.charts.Highcharts._
import scala.io.Source
import breeze.plot._
import org.saddle.{Frame, Mat, Vec}

object Demonstration extends App {

  val dm1 = DenseMatrix((0, 1 ,2), (3, 4 ,5))
  println(s"${dm1}")
  println(s"${dm1.t}")

  line(0 to 10)

  val resourcePath = getClass.getResourceAsStream("/boston_housing.data")
  val lines = Source.fromInputStream(resourcePath).getLines.map(x => line2Data(x))

  val data = lines.map(x => line2Data())

  val vecs = data.map(r => row2Vec(r))

  val matr = Mat(vecs.toArray)

  val fr = Frame(matr.transpose)


  def row2Vec(listDouble: List[Double]) :Vector[Double] = {
    val v: Vector[]
  }

  def line2Data(line: String): List[Double] = {

    line
      .split("\\s+")
      .filter(_.length > 0)
      .map(_.toDouble)
      .toList
  }
}
