name := """play-starter"""

version := "1.0-SNAPSHOT"

/**
  * root プロジェクトのビルド定義
  */
lazy val root = (project in file("."))
  .settings(Coverage.Settings)
  .settings(libraryDependencies ++= Dependencies.Application)
  .enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

libraryDependencies += "com.h2database" % "h2" % "1.4.194"
