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
    |Exercise {{num}}
    |</h1>
    |<div><button (click)="playExercise()">Play</button></div>
    |<button (click)="returnMenu.emit(0)">Menu</button>
    |</div>
    """.stripMargin
)
class ExerciseView(){
  @Output
  var returnMenu = new EventEmitter[Int]()

  var num: Int = 0

  var player = new Player(Piano, volumeMultiplier=0.7)
  var exercise: Exercise = new DualIntervalExcercise(player)

  def changeExercise(newNum: Int){
    num = newNum
    if(num != 0)
      nextTask()
  }

  def nextTask(){
    exercise = new DualIntervalExcercise(player)
    playExercise()
  }

  def playExercise(){
    exercise.play()
  }

}
