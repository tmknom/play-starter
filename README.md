[![CircleCI](https://circleci.com/gh/tmknom/play-starter.svg?style=svg)](https://circleci.com/gh/tmknom/play-starter)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4a63301e1b2e460492411c8d513c70ab)](https://www.codacy.com/app/tmknom/play-starter)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/4a63301e1b2e460492411c8d513c70ab)](https://www.codacy.com/app/tmknom/play-starter)

# play-starter

## 新しいプロジェクトの始め方

### GitHubに <project_name> というリポジトリを作成

```
git clone git@github.com:tmknom/play-starter.git <project_name>
cd <project_name>
rm -rf .git
git init
git add .
git commit -m "Initial commit"
git remote add origin git@github.com:tmknom/<project_name>.git
git push -u origin master
```

### プロジェクト名変更

* build.sbt の `name` をカッコいい名前に変更する

### （必要なら）データベース作成

```
mysql -u root -e 'CREATE DATABASE db_production CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;'
mysql -u root -e 'CREATE DATABASE db_test CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;'
```

### Codacy

* GitHubとのヒモ付を有効化
* CircleCIに環境変数にセットするトークンを払い出し
 * CODACY_PROJECT_TOKEN

### CircleCIに設定する

* GitHubとのヒモ付を有効化
* CircleCIに環境変数追加
 * CACHE_KEY
 * CODACY_PROJECT_TOKEN

### 本番の環境変数設定

* APPLICATION_SECRET
  * sbt playGenerateSecret で生成すると良い


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

### コピペチェック

```
sbt cpd
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
