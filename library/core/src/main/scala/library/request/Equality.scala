package library.request

private[request] object Equality {
  /**
    * 型安全な equals の実装
    *
    * Scala 標準の == メソッドは型安全でないため WartRemover が警告を出す。
    * Scalactic などを導入し、型安全な === メソッドを導入することも検討したが、
    * 共通ライブラリに、不要な依存関係を増やしたくなかったので、独自で型チェックも含めて行う比較メソッドを導入した。
    *
    * 実装時点では request パッケージでしか使わないため request パッケージに置いているが、
    * もし、他の場所でも使いたい場合は、アクセス範囲を library に変更することを想定してる。
    * 当然、そのときには、本クラスの置き場も request パッケージ以外になっているだろう。
    *
    * @return 型も含めて一致していたらtrue
    */
  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def typeSafeEquals[A](self: A, other: A): Boolean = {
    self == other
  }
}
