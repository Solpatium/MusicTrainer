package music_trainer.webapp.component

import music_trainer.scale.Exercises._
import music_trainer.scale.Track
import music_trainer.scale._

import angulate2.std._
import angulate2.core.EventEmitter

import scala.scalajs.js
import js.annotation._

import scala.collection.mutable

import org.scalajs.dom
import org.scalajs.dom.html
import music_trainer.visualization.Visualization
import scala.scalajs.js.timers._

@Component(
  selector = "exercise-view",
  templateUrl = "resources/exercise-view.html"
)
class ExerciseView(){
  @Output
  var returnMenu = new EventEmitter[Int]()

  @ViewChild("answerslistid")
  var answersForm: AnswersList = _

  var num: Int = -1
  var numOfExercises: Int = 1

  var isValidated: Boolean = false

  var player = new Player(new Instrument("Piano"))
  var exercise: Exercise = new DualIntervalExercise()
  var score: Int = 0
  var tasks: Int = 0
  var answersBlocks: Int = 0
  var title: String = ""
  var visual: Visualization = _

  var answersList: js.Array[AnswerOptions] = js.Array[AnswerOptions]()

  def changeExercise(newNum: Int, newNumOfExercises: Int, newInstrument: String){
    num = newNum
    score = 0
    numOfExercises = newNumOfExercises
    tasks = 0
    var player = new Player(new Instrument(newInstrument))

    title = ExerciseTypes.toString(ExerciseTypes(num))

    nextTask()
  }

  def stopExercise(){
    num = -1
  }

  def check(){
    isValidated = true
    val answersList = answersForm.getAnswer()
    if(answersList.forall( a => a == Correctnes.Correct ) && answersList.size == answersBlocks)
      score += 1
  }

  def nextTask(){
    tasks += 1
    isValidated = false
    if(answersForm != js.undefined) answersForm.resetAnswers()
    answersList = js.Array[AnswerOptions]()

    ExerciseTypes(num) match {
      case ExerciseTypes.DualInterval => exercise = new DualIntervalExercise()
      case ExerciseTypes.DominantEasy => exercise = new DominantExercise(0)
      case ExerciseTypes.DominantMedium => exercise = new DominantExercise(1)
      case ExerciseTypes.DominantHard => exercise = new DominantExercise(2)
      case ExerciseTypes.SingleIntervalEasyFirst => exercise = new SingleIntervalExercise(0)
      case ExerciseTypes.SingleIntervalMediumFirst => exercise = new SingleIntervalExercise(1)
      case ExerciseTypes.SingleIntervalHardFirst => exercise = new SingleIntervalExercise(2)
      case ExerciseTypes.SingleIntervalEasySecond => exercise = new SingleIntervalExercise(3)
      case ExerciseTypes.SingleIntervalMediumSecond => exercise = new SingleIntervalExercise(4)
      case ExerciseTypes.SingleIntervalHardSecond => exercise = new SingleIntervalExercise(5)
      case ExerciseTypes.BaseSquareInterval => exercise = new BaseSquareIntervalExercise()
    }

    val answers = exercise.getAnswers
    val answersName = answers.keys
    for(name <- answersName){
      val options = for (o <- answers(name)) yield AnswerOption(o.toString(), o.toString(), o.isCorrect)
      answersList.push(AnswerOptions(name, name, options))
    }
    answersBlocks = answersName.size
    setTimeout(1) {
      playExercise()
    }
  }

  def playExercise(){
    var canvas = dom.document.getElementById("visualizer").asInstanceOf[html.Canvas]
    if(canvas != null){
      var visual = new Visualization(canvas, exercise.track.sounds.keys)
      visual.play
    }
    player.play(exercise.track)
  }

  def stopPlay(){
    println("Stop play")
  }

}


object Correctnes extends Enumeration{
  type Correctnes = Value
  val Correct, Unchecked, Wrong = Value
}

@ScalaJSDefined
trait AnswerOptions extends js.Object {
  val title: String
  val name: String
  val options: js.Array[AnswerOption]
}
object AnswerOptions {
  def apply(title: String, name: String, options: List[AnswerOption]): AnswerOptions = {
    var optionsArray = js.Array[AnswerOption]()
    for(o <- options) optionsArray.push(o)
    js.Dynamic.literal(title = title, name = name, options = optionsArray).asInstanceOf[AnswerOptions]
  }
}

@Component(
  selector = "answers-list",
  templateUrl = "resources/answers-list.html"
)
class AnswersList(){
  @Input
  var isValidated: Boolean = _

  @Input
  var answersList: js.Array[AnswerOptions] = _

  var answers: mutable.Map[String, Correctnes.Value] = mutable.Map()

  def getAnswer(): Iterable[Correctnes.Value] = {
    return answers.values
  }

  def updateAnswer(newAnswer: AnswerMessage){
    answers += (newAnswer.name -> Correctnes(newAnswer.correctnes))
  }

  def resetAnswers(){
    answers.clear()
  }
}


@ScalaJSDefined
trait AnswerOption extends js.Object {
  val name: String
  val id: String
  val isCorrect: Boolean
}
object AnswerOption {
  def apply(name: String, id: String, isCorrect: Boolean): AnswerOption =
    js.Dynamic.literal(name = name, id = id, isCorrect = isCorrect).asInstanceOf[AnswerOption]
}

@ScalaJSDefined
trait AnswerMessage extends js.Object {
  val name: String
  val correctnes: Int
}
object AnswerMessage {
  def apply(name: String, correctnes: Correctnes.Correctnes): AnswerMessage =
    js.Dynamic.literal(name = name, correctnes = correctnes.id).asInstanceOf[AnswerMessage]
}

@Component(
  selector = "answer-box",
  templateUrl = "resources/answer-box.html"
)
class AnswerBox(){
  @Input
  var title: String = _

  @Input
  var answerName: String = _

  @Input
  var options: js.Array[AnswerOption] = _

  @Input
  var isValidated: Boolean = _

  @Output
  var answerChanged = new EventEmitter[AnswerMessage]()

  answerChanged.emit(AnswerMessage(answerName, Correctnes.Unchecked))

  def onSelectionChange(isCorrect: Boolean){
    if(isCorrect) answerChanged.emit(AnswerMessage(answerName, Correctnes.Correct))
    else answerChanged.emit(AnswerMessage(answerName, Correctnes.Wrong))
  }

}
