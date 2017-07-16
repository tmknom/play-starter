name := """play-starter"""

version := "1.0-SNAPSHOT"

/**
  * root プロジェクトのビルド定義
  */
lazy val root = (project in file("."))
  .settings(BuildSettings.Settings)
  .settings(Migration.Settings)
  .settings(Coverage.Settings)
  .settings(StaticAnalysis.Settings)
  .settings(StaticAnalysis.PlaySettings)
  .settings(libraryDependencies ++= Dependencies.Application)
  .enablePlugins(PlayScala)
  .enablePlugins(CopyPasteDetector)

resolvers += Resolver.sonatypeRepo("snapshots")
