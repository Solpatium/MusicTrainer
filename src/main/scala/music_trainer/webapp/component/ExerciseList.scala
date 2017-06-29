package music_trainer.webapp.component

import angulate2.std._
import angulate2.core.EventEmitter

import scala.scalajs.js
import js.annotation._

import music_trainer.scale.Instrument

import scala.collection.immutable.HashMap

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

@ScalaJSDefined
trait InstrumentDefinition extends js.Object {
  val id: String
  val name: String
}
object InstrumentDefinition {
  def apply(id: String, name: String): InstrumentDefinition =
    js.Dynamic.literal(id = id, name = name).asInstanceOf[InstrumentDefinition]
}

@ScalaJSDefined
trait SelectedOption extends js.Object {
  val numOfExercises: Int
  val id: Int
  val instrument: String
}
object SelectedOption {
  def apply(numOfExercises: Int, id: Int, instrument: String): SelectedOption =
    js.Dynamic.literal(numOfExercises = numOfExercises, id = id, instrument = instrument).asInstanceOf[SelectedOption]
}


@Component(
  selector = "exercise-list",
  templateUrl = "resources/exercise-list.html"
)
class ExerciseList(){

  @Output
  var menuSelected = new EventEmitter[SelectedOption]()

  import music_trainer.scale.Exercises.ExerciseTypes
  import music_trainer.scale.Exercises.ExerciseTypes._

  val options = js.Array(
                          ExerciseItemDefinition(ExerciseTypes.toString(DualInterval), Hardness.Easy, "Latwe cwiczenie", DualInterval.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(DominantEasy), Hardness.Easy, "Trudne cwiczenie", DominantEasy.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(DominantMedium), Hardness.Medium, "Trudne cwiczenie", DominantMedium.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(DominantHard), Hardness.Hard, "Trudne cwiczenie", DominantHard.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(BaseSquareInterval), Hardness.Medium, "Średnie cwiczenie", BaseSquareInterval.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(SingleIntervalEasyFirst), Hardness.Easy, "Trudne cwiczenie", SingleIntervalEasyFirst.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(SingleIntervalMediumFirst), Hardness.Medium, "Trudne cwiczenie", SingleIntervalMediumFirst.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(SingleIntervalHardFirst), Hardness.Hard, "Trudne cwiczenie", SingleIntervalHardFirst.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(SingleIntervalEasySecond), Hardness.Easy, "Trudne cwiczenie", SingleIntervalEasySecond.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(SingleIntervalMediumSecond), Hardness.Medium, "Trudne cwiczenie", SingleIntervalMediumSecond.id),
                          ExerciseItemDefinition(ExerciseTypes.toString(SingleIntervalHardSecond), Hardness.Hard, "Trudne cwiczenie", SingleIntervalHardSecond.id)
                        )

  val instrumentNames = HashMap(
      "Piano" -> "Pianion",
      "Clavinet" -> "clavinet/Klarnet?????",
      "Guitar" -> "Gitara"
  )

  var instruments = js.Array[InstrumentDefinition]()

  for(i <- Instrument.list.keys)
    instruments.push(InstrumentDefinition(i, instrumentNames(i)))

}


@Component(
  selector = "exercise-item",
  templateUrl = "resources/exercise-item.html"
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
  var menuEm: EventEmitter[SelectedOption] = _

  @Input
  var instrument: String = _

  def selected(numOfExercises: String){
    println(instrument)
    menuEm.emit(SelectedOption(numOfExercises.toInt, optionId, instrument))
  }
}
