package machine_learning_with_scala.core

import machine_learning_with_scala.core.Design.Config

import scala.util.Try

abstract class ETransform[T, A](config : Config) extends ITransform[T, A] {

  self =>

  override def map[B](f: A => B): ETransform[T, B] = new ETransform[T, B] (config) {
    override def |> : PartialFunction[T, Try[B]] = ???
  }

  def flatMap[B](f: A => ETransform[T, B]): ETransform[T, B] = new ETransform[T, B] (config) {
    override def |> : PartialFunction[T, Try[B]] = ???
  }

  def andThen[B](tr: ETransform[T, B]): ETransform[T, B] = new ETransform[T, B] (config) {
    override def |> : PartialFunction[T, Try[B]] = ???
  }

}
