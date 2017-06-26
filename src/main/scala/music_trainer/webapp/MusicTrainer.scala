package music_trainer.webapp

import music_trainer.scale.Exercises.{Answer, DualIntervalExcercise}
import music_trainer.scale.Track
import music_trainer.scale._

import angulate2.std._
import angulate2.platformBrowser.BrowserModule

import music_trainer.webapp.component._

@NgModule(
  imports = @@[BrowserModule],
  declarations = @@[AppComponent,ButtonComponent,ExerciseList,ExerciseItem,ExerciseView],
  bootstrap = @@[AppComponent]
)
class AppModule {

}

@Component(
  selector = "my-app",
  template = "<h1>Hello Angular!<my-button></my-button></h1><exercise-view #ev (returnMenu)=\"menuSel($event)\"></exercise-view><exercise-list *ngIf=\"num == 0\" (menuSelected)=\"menuSel($event)\"></exercise-list>"
)
class AppComponent {

  @ViewChild("ev")
  var exerciseView: ExerciseView = _

  var num = 0

  def menuSel(item: Int){
    num = item
    exerciseView.changeExercise(num)
  }
}

@Component(
  selector = "my-button",
  template = "<button (click)=\"clk()\">Play whole octave using track</button>"
)
class ButtonComponent {
  var player = new Player(Piano, volumeMultiplier=0.7)
  val length = 2
  var octave = 4

  def clk(){
    val track = new Track()
    var time = 0.0
    for( note <- Note.values ) {
      track.add(time, Sound(note, octave, length))
      // Uncomment this to play 2 sounds at the same time
      // track.add(time, Sound(Note.A, octave, length))
      time += 0.8
    }
    player play track
  }
}


 /*
import org.scalajs.jquery.jQuery

import scala.scalajs.js.JSApp
import music_trainer.scale._

object MusicTrainer extends JSApp {
  def main(): Unit = {
    scaleDemo()
  }

  def scaleDemo() {
    val $notes = jQuery("#notes")
    val $octave = jQuery("#octave")
    val $octavePlayer = jQuery("#whole-octave")
    val $instrument = jQuery("#instrument")
    val $exercise = jQuery("#simple-exercise")

    var player = new Player(Piano, volumeMultiplier=0.7)
    $instrument change (() => {
      val instrument = if($instrument.value().asInstanceOf[String] == "Piano") Piano else Organ
      player = new Player(instrument, volumeMultiplier=0.7)
    })

    var octave = 4
    $octave change (() => {
      octave = $octave.value().asInstanceOf[Int]
    })

    val length = 2
    // Playing single note
    for( note <- Note.values ) {
      val name = note.toString. replaceAllLiterally("$hash", "#")
      println(name)
      val button = jQuery(s"""<button value="$name">$name</name>""").click(()=>{
        player play Sound(note, octave, length)
      })
      $notes.append(button)
    }

    // Playing whole octave
    $octavePlayer click (() => {
      val track = new Track()
      var time = 0.0
      for( note <- Note.values ) {
        track.add(time, Sound(note, octave, length))
        // Uncomment this to play 2 sounds at the same time
        // track.add(time, Sound(Note.A, octave, length))
        time += 0.8
      }
      player play track
    })
    $exercise click (() => {
      val exercise = new DualIntervalExcercise(player)
      exercise.play()
      import DualIntervalExcercise.{ANSWER_BOTTOM_NAME, ANSWER_TOP_NAME}
      println(ANSWER_TOP_NAME + ": " +
//        This is how you get correct, human-Readable answer
        exercise.getAnswers(ANSWER_TOP_NAME).filter(_.isCorrect).map(answer => Answer.getHumanReadAble(answer.interval)).head)
      println(ANSWER_BOTTOM_NAME + ": " +
        exercise.getAnswers(ANSWER_BOTTOM_NAME).filter(_.isCorrect).map(answer => Answer.getHumanReadAble(answer.interval)).head)
    })
  }
}
// */
