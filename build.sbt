name := """play-starter"""

version := "1.0-SNAPSHOT"

/**
  * root プロジェクトのビルド定義
  */
lazy val root = (project in file("."))
  .settings(BuildSettings.Settings)
  .settings(Coverage.Settings)
  .settings(StaticAnalysis.Settings)
  .settings(StaticAnalysis.PlaySettings)
  .settings(libraryDependencies ++= Dependencies.Application)
  .enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")
