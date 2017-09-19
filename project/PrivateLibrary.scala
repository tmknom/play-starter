/**
  * 独自実装した共通ライブラリの依存定義
  */

import sbt._

// noinspection TypeAnnotation
object PrivateLibrary {
  lazy val LibraryOrganization = "library"
  lazy val LibraryVersion = "1.0.0-SNAPSHOT"

  val Core = LibraryOrganization %% "core" % LibraryVersion
  val Datetime = LibraryOrganization %% "datetime" % LibraryVersion
  val Spray = LibraryOrganization %% "spray" % LibraryVersion
  val Controller = LibraryOrganization %% "controller" % LibraryVersion
  val Validation = LibraryOrganization %% "validation" % LibraryVersion
  val Flyway = LibraryOrganization %% "flyway" % LibraryVersion
  val Migration = LibraryOrganization %% "migration" % LibraryVersion
  val Filter = LibraryOrganization %% "filter" % LibraryVersion
  val Errorhandler = LibraryOrganization %% "errorhandler" % LibraryVersion
  val Test = LibraryOrganization %% "test" % LibraryVersion

  val Dependencies = Seq(
    Core,
    Datetime,
    Spray,
    Controller,
    Validation,
    Flyway,
    Migration,
    Filter,
    Errorhandler,
    Test
  )
}
