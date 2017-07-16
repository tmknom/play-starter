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
    javaOptions += "-Dlogback.loglevel.scalikejdbc=DEBUG"
  )
}
