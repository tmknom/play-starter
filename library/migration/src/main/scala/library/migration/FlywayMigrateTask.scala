package library.migration


import library.flyway.Flyway
import library.task.Task
import play.api.{Application, Logger}

/**
  * マイグレーション実行
  *
  * flyway-sbtのタスクをそのまま使おうと思ったんだけど
  * sbt distで固めたリソースだけではsbtコマンドを単発実行できなくて
  * 仕方がないのでplayのアプリケーションとして初期化して
  * main関数を差し替えて実行することにした
  *
  * $ sbt "run-main library.migration.FlywayMigrateTask"
  */
object FlywayMigrateTask extends App with Task {
  run()

  def task(app: Application): Unit = {
    val flyway = Flyway(app.injector)
    // マイグレーション前の状態
    Logger.info(flyway.info())

    // マイグレーション実行
    // 適用されたマイグレーションの数が返る
    val applied = flyway.migrate()
    Logger.info(applied.toString + " migrations were applied")

    // マイグレーション後の状態
    Logger.info(flyway.info())
  }

}

// FlywayCleanTaskはproductionで実行するとヤバイので定義しない
