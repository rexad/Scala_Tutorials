package machine_learning_with_scala.core


import machine_learning_with_scala.util.FileUtils

object Design {

  trait Config {
    def write(content : String): Boolean =
      FileUtils.write(content, Config.RELATIVE_PATH, getClass.getSimpleName)
    def >> : Boolean = false
  }

  object Config {
    private val RELATIVE_PATH = "configs/"
    def read(className: String): Option[String] = FileUtils.read(RELATIVE_PATH, className)
  }
}
