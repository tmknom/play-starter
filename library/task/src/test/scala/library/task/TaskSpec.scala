package library.task

import org.scalatestplus.play.PlaySpec
import play.api.Application

class TaskSpec extends PlaySpec {
  "Task#run" should {
    "例外がスローされないこと" in {
      TestTask.run()
    }
  }

  private object TestTask extends App with Task {
    def task(app: Application): Unit = {}
  }

}
