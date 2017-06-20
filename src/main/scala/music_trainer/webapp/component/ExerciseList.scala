package music_trainer.webapp.component

import angulate2.std._

import scala.scalajs.js
import js.annotation._

/*
object ExerciseItem{
  val Hardness: Map[String, String] = HashMap(
    "easy"  -> "hardness hardness-easy",
    "medium"  -> "hardness hardness-medium",
    "hard"  -> "hardness hardness-hard"
  )
}*/

object Hardness extends Enumeration{
  type Hardness = Value
  val Easy, Medium, Hard = Value

  def toString(hardness: Value): String =
    hardness match {
      case Easy => "hardness hardness-easy"
      case Medium => "hardness hardness-medium"
      case Hard => "hardness hardness-hard"
    }
}

@Component(
  selector = "exercise-list",
  template =
  """<ul class="list">
    |<exercise-item *ngFor="let ex of options"
    |[(title)]="ex.title"
    |[(level)]="ex.level"
    |[(content)]="ex.content">
    |</exercise-item>
    |</ul>
    """.stripMargin
)
class ExerciseList(){

  @ScalaJSDefined
  trait ExerciseItemDefinition extends js.Object {
    val title: String
    val level: String
    val content: String
  }
  object ExerciseItemDefinition {
    def apply(title: String, level: Hardness.Hardness, content: String): ExerciseItemDefinition =
      js.Dynamic.literal(title = title, level = Hardness.toString(level), content = content).asInstanceOf[ExerciseItemDefinition]
  }

  val options = js.Array(
                          ExerciseItemDefinition("Cw1", Hardness.Easy, "Latwe cwiczenie"),
                          ExerciseItemDefinition("Cw2", Hardness.Hard, "Trudne cwiczenie")
                        )

}


@Component(
  selector = "exercise-item",
  template =
  """<li class="exercise">
    |<span class={{level}}></span>
    |<h4 class="exercise-title">{{title}}</h4>
    |<p class="exercise-content">{{content}}</p>
    |<button class="button">wybierz</button>
    |</li>
    """.stripMargin
)
class ExerciseItem() {
  @Input
  var title: String = _

  @Input
  var level: String = _

  @Input
  var content: String = _
}
