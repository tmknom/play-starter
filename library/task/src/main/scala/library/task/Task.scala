package library.task

import play.api._

/**
  * 単発タスクを実行する
  *
  * $ sbt "run-main tasks.HogeTask"
  */
trait Task {

  final def run(): Unit = {
    val app = application()
    try {
      task(app)
    } finally {
      Play.stop(app)
    }
  }

  def task(app: Application): Unit

  private def environment: Mode = {
    System.getProperty("play.mode") match {
      case "Prod" => Mode.Prod
      case _ => Mode.Dev
    }
  }

  private def application(): Application = {
    val env = Environment(new java.io.File("."), this.getClass.getClassLoader, environment)
    val context = ApplicationLoader.createContext(env)
    val loader = ApplicationLoader(context)
    loader.load(context)
  }
}
