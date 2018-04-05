package machine_learning_with_scala.core

import scala.util.Try

class ETransform[T, A](config : Config) extends ITransform[T, A] {
  override def |> : PartialFunction[T, Try[A]] = ???
}
