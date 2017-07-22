package library.spray

import library.spray.TestUserJsonProtocol._
import org.scalatestplus.play.PlaySpec
import spray.json._

class SnakeCaseJsonNamingSpec extends PlaySpec {
  "SnakeCaseJsonNaming#extractFieldNames" should {
    "JSONのキー名がスネークケース（アンダースコア区切り）へ変換されること" in {
      val actual = TestUser("Jotaro Kujo", 30).toJson // scalastyle:ignore
      actual.compactPrint mustBe """{"user_name":"Jotaro Kujo","age":30}"""
    }
  }
}

private final case class TestUser(userName: String, age: Int)

private object TestUserJsonProtocol extends SnakeCaseJsonNaming {
  implicit val format: RootJsonFormat[TestUser] = jsonFormat2(TestUser)
}
