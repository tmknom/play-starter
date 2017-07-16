[![CircleCI](https://circleci.com/gh/tmknom/play-starter.svg?style=svg)](https://circleci.com/gh/tmknom/play-starter)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4a63301e1b2e460492411c8d513c70ab)](https://www.codacy.com/app/tmknom/play-starter)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/4a63301e1b2e460492411c8d513c70ab)](https://www.codacy.com/app/tmknom/play-starter)

# play-starter

## テスト

```
sbt test
```

## マイグレーション

```
sbt flywayMigrate
sbt flywayClean # 全部消したい場合
```

## 起動

```
sbt run
```

## 静的解析：Scalastyle

```
sbt scalastyle
```

## 依存ライブラリのバージョンアップチェック

```
sbt dependencyUpdates
```

## コードの統計情報

```
sbt stats
```

## データベースの起動

```
mysql.server start
```
