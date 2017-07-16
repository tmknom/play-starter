/**
  * Scala/Javaの設定
  */

import sbt.Keys._
import sbt._

// noinspection TypeAnnotation
object BuildSettings {
  val Settings = Seq(
    /**
      * Scalaのバージョン
      */
    scalaVersion := "2.12.2",

    /**
      * システムのタイムゾーンを指定
      *
      * scalikejdbc などの一部のライブラリでは、システムのタイムゾーンがそのまま使われるため
      * JVM のタイムゾーンは明示的に指定しておく。
      *
      * 本番環境で動かすときには sbt 経由でアプリケーションを起動するわけではないので
      * 起動オプションに明示的に指定する必要があることに注意。
      */
    javaOptions += "-Duser.timezone=Asia/Tokyo",

    /**
      * logback でログをファイル出力するか：conf/logback.xmlで参照する値
      *
      * 値がセットされていると、ログがファイルに出力される。
      * sbt 経由でアプリケーションを起動した場合のみ、ログをファイル出力する。
      */
    javaOptions += "-Dlogback.appender.file=true",

    /**
      * logback の application のログレベル：conf/logback.xmlで参照する値
      *
      * ローカル環境では DEBUG レベルのほうが便利なので、明示的に指定している。
      * 本番環境などでは conf/logback.xml で記述されたデフォルト値である INFO レベルで出力する。
      */
    javaOptions += "-Dlogback.loglevel.application=DEBUG",

    /**
      * logback の scalikejdbc のログレベル：conf/logback.xmlで参照する値
      *
      * ローカル環境では DEBUG レベルのほうが便利なので、明示的に指定している。
      * 本番環境などでは conf/logback.xml で記述されたデフォルト値である INFO レベルで出力する。
      */
    javaOptions += "-Dlogback.loglevel.scalikejdbc=DEBUG",

    /**
      * テスト時の設定ファイルの切り替え
      */
    javaOptions in Test += "-Dconfig.file=conf/test.conf",

    /**
      * ScalaTest のオプション設定
      *
      * -oD : テストケースごとに実行時間の表示
      * -eI : 失敗したテストを最後にまとめて表示
      *
      * @see http://www.scalatest.org/user_guide/using_scalatest_with_sbt
      */
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD", "-eI")
  )
}
