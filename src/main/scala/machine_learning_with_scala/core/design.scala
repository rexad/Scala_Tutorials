package machine_learning_with_scala.core

object Design {

  trait Config {
    def write(content : String): Boolean = FileUtils.write
  }
}
