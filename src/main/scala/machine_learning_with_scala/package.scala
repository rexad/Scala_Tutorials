
package object predef {

  import Context._

  type DblPair = (Double, Double)
  type DblMatrix = Array[Array[Double]]
  type DblVec = Vector[Double]
  type DblPairVector = Vector[DblPair]
  type Features = Array[Double]
  type VSeries[T] = Vector[Array[T]]
  type DblSeries = Vector[Array[Double]]

  object Context {
    trait ToDouble[T] { def apply(t: T): Double }

    implicit val int2Double = new ToDouble[Int] {
      def apply(t: Int): Double = t.toDouble
    }

    implicit val double2Double = new ToDouble[Double] {
      def apply(t: Double): Double = t
    }

    trait ToFeatures[T] { def apply(xt: Array[T]): Features }

    final class ArrayDouble2Features extends ToFeatures[Double] {
      def apply(ar: Array[Double]): Features = ar
    }

    final class ArrayInt2Features extends ToFeatures[Int] {
      def apply(ar: Array[Int]): Features = ar.map(_.toDouble)
    }
  }
}
