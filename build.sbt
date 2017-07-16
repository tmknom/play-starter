name := """play-starter"""

version := "1.0-SNAPSHOT"

/**
  * root プロジェクトのビルド定義
  */
lazy val root = (project in file("."))
  .settings(BuildSettings.Settings)
  .settings(Coverage.Settings)
  .settings(libraryDependencies ++= Dependencies.Application)
  .enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")
