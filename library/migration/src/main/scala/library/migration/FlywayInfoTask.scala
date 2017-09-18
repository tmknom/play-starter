package library.migration

import library.flyway.Flyway
import library.task.Task
import play.api.{Application, Logger}

/**
  * マイグレーション状態の表示
  *
  * $ sbt "run-main library.migration.FlywayInfoTask"
  */
object FlywayInfoTask extends App with Task {
  run()

  def task(app: Application): Unit = {
    val flyway = Flyway(app.injector)
    Logger.info(flyway.info())
  }

}
