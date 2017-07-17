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
  .aggregate(core).dependsOn(core)
  .aggregate(datetime).dependsOn(datetime)
  .aggregate(spray).dependsOn(spray)
  .aggregate(validation).dependsOn(validation)
  .aggregate(migration).dependsOn(migration)
  .aggregate(filter).dependsOn(filter)
  .aggregate(errorHandler).dependsOn(errorHandler)

resolvers += Resolver.sonatypeRepo("snapshots")

/**
  * 共通ライブラリのビルド定義
  */
import Library._

lazy val core = (project in file("library/core"))
  .settings(BuildSettings.Settings)
  .settings(StaticAnalysis.Settings)
  .settings(libraryDependencies ++= Seq(PlayFramework, ScalatestplusPlay))

lazy val datetime = (project in file("library/datetime"))
  .settings(BuildSettings.Settings)
  .settings(StaticAnalysis.Settings)
  .settings(libraryDependencies ++= Seq(ScalatestplusPlay))

lazy val spray = (project in file("library/spray"))
  .settings(BuildSettings.Settings)
  .settings(StaticAnalysis.Settings)
  .settings(libraryDependencies ++= Seq(SprayJson, ScalatestplusPlay))

lazy val validation = (project in file("library/validation"))
  .settings(BuildSettings.Settings)
  .settings(StaticAnalysis.Settings)
  .aggregate(core).dependsOn(core)
  .settings(libraryDependencies ++= Seq(PlayFramework, ScalatestplusPlay))

lazy val migration = (project in file("library/migration"))
  .settings(BuildSettings.Settings)
  .settings(StaticAnalysis.Settings)
  .settings(libraryDependencies ++= Seq(PlayFramework, PlayJdbcApi, FlywayCore, ScalatestplusPlay))

lazy val filter = (project in file("library/filter"))
  .settings(BuildSettings.Settings)
  .settings(StaticAnalysis.Settings)
  .aggregate(core).dependsOn(core)
  .settings(libraryDependencies ++= Seq(PlayFramework, LogstashLogbackEncoder, ScalatestplusPlay))

lazy val errorHandler = (project in file("library/error_handler"))
  .settings(BuildSettings.Settings)
  .settings(StaticAnalysis.Settings)
  .aggregate(core).dependsOn(core)
  .settings(libraryDependencies ++= Seq(PlayFramework, LogstashLogbackEncoder, ScalatestplusPlay))
