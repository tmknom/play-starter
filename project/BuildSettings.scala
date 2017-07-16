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
    scalaVersion := "2.12.2"
  )
}
