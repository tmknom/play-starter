// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.1")

/**
  * カバレッジ
  *
  * @see https://github.com/scoverage/sbt-scoverage
  */
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

/**
  * がバレッジ＆静的解析：Codacy
  *
  * 事前準備として Codacy 上で発行したトークンを CirclecCI の Environment Variables に
  * CODACY_PROJECT_TOKEN という名前で保存しておく必要がある。
  *
  * @see https://support.codacy.com/hc/en-us/articles/207279819-Coverage
  * @see https://github.com/codacy/sbt-codacy-coverage
  */
addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.8")
