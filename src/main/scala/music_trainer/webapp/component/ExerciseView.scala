package music_trainer.webapp.component

import music_trainer.scale.Exercises._
import music_trainer.scale.Track
import music_trainer.scale._

import angulate2.std._
import angulate2.core.EventEmitter

import scala.scalajs.js
import js.annotation._

import scala.collection.mutable


@Component(
  selector = "exercise-view",
  templateUrl = "resources/exercise-view.html"
)
class ExerciseView(){
  @Output
  var returnMenu = new EventEmitter[Int]()

  @ViewChild("answerslistid")
  var answersForm: AnswersList = _

  var num: Int = 0
  var numOfExercises: Int = 1

  var isValidated: Boolean = false

  var player = new Player(Piano, volumeMultiplier=0.7)
  var exercise: Exercise = new DualIntervalExcercise(player)
  var score: Int = 0
  var tasks: Int = 0
  var answersBlocks: Int = 0

  var answersList: js.Array[AnswerOptions] = js.Array[AnswerOptions]()

  def changeExercise(newNum: Int, newNumOfExercises: Int){
    num = newNum
    score = 0
    numOfExercises = newNumOfExercises
    tasks = 0
    nextTask()
  }

  def stopExercise(){
    num = 0
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
    exercise = new DualIntervalExcercise(player)

    import DualIntervalExcercise.{ANSWER_BOTTOM_NAME, ANSWER_TOP_NAME}
    println(ANSWER_TOP_NAME + ": " +
//        This is how you get correct, human-Readable answer
      exercise.getAnswers(ANSWER_TOP_NAME).filter(_.isCorrect).map(answer => Answer.getHumanReadAble(answer.interval)).head)
    println(ANSWER_BOTTOM_NAME + ": " +
      exercise.getAnswers(ANSWER_BOTTOM_NAME).filter(_.isCorrect).map(answer => Answer.getHumanReadAble(answer.interval)).head)

    val answers = exercise.getAnswers
    val answersName = answers.keys
    for(name <- answersName){
      val options = for (o <- answers(name)) yield AnswerOption(Answer.getHumanReadAble(o.interval), o.interval.toString, o.isCorrect)
      answersList.push(AnswerOptions(name, name, options))
    }
    answersBlocks = answersName.size
    playExercise()
  }

  def playExercise(){
    exercise.play()
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
