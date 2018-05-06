package machine_learning_with_scala.stats

import machine_learning_with_scala.util.DisplayUtils
import predef.Context.ToDouble
import org.apache.log4j.Logger
import predef.DblVec

import scala.util.Try

class MinMax[@specialized(Double) T: ToDouble](val values: Vector[T]) {

  require(values.nonEmpty, "MinMax: Cannot initialize stats with undefined values")

  def this(values: Array[T]) = this(values.toVector)

  case class ScaleFactors(low: Double, high: Double, ratio: Double)

  private val logger = Logger.getLogger("MinMax")

  private[this] val zero = (Double.MaxValue, -Double.MaxValue)
  private[this] var scaleFactors: Option[ScaleFactors] = None

  /**
    * Computation of minimum and maximum values of a vector during instantiation
    */
  val (min, max): (Double, Double) = values./:(zero) { (m, x) => {
    val _x = implicitly[ToDouble[T]].apply(x)
    (if (_x < m._1) _x else m._1, if (_x > m._2) _x else m._2)
    }
  }

  @throws(classOf[IllegalStateException])
  final def normalize(low: Double = 0.0, high: Double = 1.0): DblVec =
    setScaleFactors(low, high).map(scale => {
      values.map(x => (implicitly[ToDouble[T]].apply(x) - min) * scale.ratio + scale.low)
    })
      .getOrElse(throw new IllegalStateException("MinMax.normalize normalization params undefined"))

  private def setScaleFactors(low: Double, high: Double): Option[ScaleFactors] =
    if (high < low + MinMax.STATS_EPS) {
      DisplayUtils.none(s"MinMax.set found high - low = $high - $low <= 0 required > ", logger)
    }
    else {
      val ratio = (high - low) / (max - min)
      if (ratio < MinMax.STATS_EPS) {
        DisplayUtils.none(s"MinMax.set found ratio $ratio required > EPS ", logger)
      } else {
        scaleFactors = Some(ScaleFactors(low, high, ratio))
        scaleFactors
      }
    }
}



object MinMax {

  final val STATS_EPS = 1e-12
  final val INV_SQRT_2PI = 1.0 / Math.sqrt(2.0 * Math.PI)

  def apply[T: ToDouble](values: Vector[T]): Try[MinMax[T]] = Try(new MinMax[T](values))
}
