package machine_learning_with_scala.stats

import predef.Context.ToDouble

class Stats[T: ToDouble](values: Vector[T]) extends MinMax[T] (values) {

}

object Stats {
  final val STATS_EPS = 1e-12
  final val INV_SQRT_2PI = 1.0 / Math.sqrt(2.0 * Math.PI)

  /**
    * Default constructor for statistics
    * @param values vector or array of elements of type T
    */
  def apply[T: ToDouble](values: Vector[T]): Stats[T] = new Stats[T](values)

  /**
    * Default constructor for statistics
    * @param values vector or array of elements of type T
    */
  def apply[T: ToDouble](values: Array[T]): Stats[T] = new Stats[T](values.toVector)

  /**
    * Default constructor for statistics
    * @param values vector or array of elements of type T
    */
  def apply[T: ToDouble](values: Iterator[T]): Stats[T] = new Stats[T](values.toVector)

  /**
    * Compute the Gauss density function for a value given a mean and standard deviation
    * @param mean mean values of the Gauss probability density function
    * @param stdDev standard deviation of the Gauss probability density function
    * @param x  value for which the Gauss probability density function has to be computed
    * @return Gaussian probability
    * @throws IllegalArgumentException if stdDev is close t zero
    */

  final def gauss(mean: Double, stdDev: Double, x: Double): Double = {
    require(
      Math.abs(stdDev) >= STATS_EPS,
      s"Stats.gauss, Gauss standard deviation $stdDev is close to zero"
    )

    val y = (x - mean) / stdDev
    INV_SQRT_2PI * Math.exp(-0.5 * y * y) / stdDev
  }

  final val LOG_2PI = -Math.log(2.0 * Math.PI)
  final def logGauss(mean: Double, stdDev: Double, x: Double): Double = {
    val y = (x - mean) / stdDev
    -LOG_2PI - Math.log(stdDev) - 0.5 * y * y
  }

  val logNormal = logGauss(0.0, 1.0, _: Double)

  /**
    * Compute the Gauss density value with a variable list of parameters
    * @param x list of parameters
    * @return Gaussian probability
    */
  @throws(classOf[IllegalArgumentException])
  final def gauss(x: Double*): Double = {
    require(x.size > 2, s"Stats.gauss Number of parameters ${x.size} is out of range")
    gauss(x(0), x(1), x(2))
  }

  final def logGauss(x: Double*): Double = logGauss(x(0), x(1), x(2))
  final def logGaussSeq(x: Seq[Double]): Double = logGauss(x(0), x(1), x(2))


  /**
    * Compute the Normal (Normalized Gaussian) density (mean = 0, standard deviation = 1.0)
    * @return Gaussian probability
    * @throws IllegalArgumentException if stdDev is close to zero or the number of parameters
    * is less than 3
    */
  val normal = gauss(0.0, 1.0, _: Double)

  /**
    * Compute the Bernoulli density given a mean and number of trials
    * @param mean mean value
    * @param p Number of trials
    */
  final def bernoulli(mean: Double, p: Int): Double = mean * p + (1 - mean) * (1 - p)

  /**
    * Compute the Bernoulli density given a mean and number of trials with a variable list
    * of parameters
    * @param x list of parameters
    * @return Bernoulli probability
    * @throws IllegalArgumentException if the number of parameters is less than 3
    */
  @throws(classOf[IllegalArgumentException])
  final def bernoulli(x: Double*): Double = {
    require(x.size > 2, s"Stats.bernoulli found ${x.size} arguments required > 2")
    bernoulli(x(0), x(1).toInt)
  }
}
