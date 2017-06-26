package music_trainer.webapp.component

import angulate2.std._
import angulate2.core.EventEmitter

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


@ScalaJSDefined
trait ExerciseItemDefinition extends js.Object {
  val title: String
  val level: String
  val content: String
  val optionId: Int
}
object ExerciseItemDefinition {
  def apply(title: String, level: Hardness.Hardness, content: String, optionId: Int): ExerciseItemDefinition =
    js.Dynamic.literal(title = title, level = Hardness.toString(level), content = content, optionId = optionId).asInstanceOf[ExerciseItemDefinition]
}


@Component(
  selector = "exercise-list",
  template =
  """<div>
    |<ul class="list" id="exercises-list">
    |<exercise-item *ngFor="let ex of options"
    |[(title)]="ex.title"
    |[(level)]="ex.level"
    |[(content)]="ex.content"
    |[(optionId)]="ex.optionId"
    |[(menuEm)]="menuSelected">
    |</exercise-item>
    |</ul>
    |</div>
    """.stripMargin
)
class ExerciseList(){

  @Output
  var menuSelected = new EventEmitter[Int]()

  val options = js.Array(
                          ExerciseItemDefinition("Cw1", Hardness.Easy, "Latwe cwiczenie", 1),
                          ExerciseItemDefinition("Cw2", Hardness.Hard, "Trudne cwiczenie", 2)
                        )

}


@Component(
  selector = "exercise-item",
  template =
  """<li class="exercise">
    |<span class={{level}}></span>
    |<h4 class="exercise-title">{{title}}</h4>
    |<p class="exercise-content">{{content}}</p>
    |<button class="button" (click)="selected()">wybierz</button>
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

  @Input
  var optionId: Int = _

  @Input
  var menuEm: EventEmitter[Int] = _

  def selected(){
    menuEm.emit(optionId)
  }
}
