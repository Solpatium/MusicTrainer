package music_trainer.webapp.component

import music_trainer.scale.Exercises._
import music_trainer.scale.Track
import music_trainer.scale._

import angulate2.std._
import angulate2.core.EventEmitter

import scala.scalajs.js
import js.annotation._

@Component(
  selector = "exercise-view",
  template =
  """<div *ngIf="num != 0">
    |<h1>
    |Exercise {{num}} {{numOfExercises}}
    |</h1>
    |<answers-list [(isValidated)]="isValidated" [(answersList)]="answersList"></answers-list>
    |<div><button (click)="playExercise()">Play</button></div>
    |<button *ngIf="!isValidated" (click)="isValidated=true">Check</button>
    |<button *ngIf="isValidated" (click)="nextTask()">Next</button>
    |<button (click)="returnMenu.emit(0)">Menu</button>
    |</div>
    """.stripMargin
)
class ExerciseView(){
  @Output
  var returnMenu = new EventEmitter[Int]()

  var num: Int = 0
  var numOfExercises: Int = 0

  var isValidated: Boolean = false

  var player = new Player(Piano, volumeMultiplier=0.7)
  var exercise: Exercise = new DualIntervalExcercise(player)

  var answersList: js.Array[AnswerOptions] = js.Array[AnswerOptions]()

  def changeExercise(newNum: Int, newNumOfExercises: Int){
    num = newNum
    numOfExercises = newNumOfExercises
    nextTask()
  }

  def stopExercise(){
    num = 0
  }

  def nextTask(){
    isValidated = false
    answersList = js.Array[AnswerOptions]()
    exercise = new DualIntervalExcercise(player)
    val answers = exercise.getAnswers
    val answersName = answers.keys
    for(name <- answersName){
      val options = for (o <- answers(name)) yield AnswerOption(Answer.getHumanReadAble(o.interval), o.interval.toString, o.isCorrect)
      answersList.push(AnswerOptions(name, name, options))
    }
    playExercise()
  }

  def playExercise(){
    exercise.play()
  }

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
  template =
  """<div>
    |<ul [ngClass]="{'list': 1, 'answers': 1, 'validated': isValidated }">
    |<answer-box *ngFor="let answer of answersList"
    |[(title)]="answer.title"
    |[(answerName)]="answer.name"
    |[(options)]="answer.options"
    |[(isValidated)]="isValidated"
    |#{{answer.name}}></answer-box>
    |</ul>
    |</div>
    """.stripMargin
)
class AnswersList(){
  @Input
  var isValidated: Boolean = _

  @Input
  var answersList: js.Array[AnswerOptions] = _

  def getAnswer(): List[Int] = {
    var answers = List[Int]()
    for(answerBox <- answersList){

      @ViewChild(answerBox.name)
      var answerView: AnswerBox = null

      answers = answerView.getAnswer() :: answers
    }
    return answers
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

@Component(
  selector = "answer-box",
  template =
  """<li class="container answer-wrapper">
    |<h4 class="answer-title">{{title}}</h4>
    |<ng-container *ngFor="let option of options">
    |<input class="hidden-radio" type="radio" name={{answerName}} id={{answerName}}{{option.id}} value="option.id" (change)="onSelectionChange(option.isCorrect)">
    |<label for={{answerName}}{{option.id}} [ngClass]="{'button': 1, 'answer': 1, 'good': option.isCorrect}">{{option.name}}</label>
    |</ng-container>
    |</li>
    """.stripMargin
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

  var points = 0

  def onSelectionChange(isCorrect: Boolean){
    if(isCorrect) points = 1
    else points = -1
  }

  def getAnswer(): Int = {
    return points
  }

}
