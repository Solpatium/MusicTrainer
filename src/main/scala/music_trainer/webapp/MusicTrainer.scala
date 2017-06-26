package music_trainer.webapp

import music_trainer.scale.Exercises.{Answer, BaseSquareIntervalExercise, DualIntervalExcercise, SingleIntervalExercise}
import music_trainer.scale.Track
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
    val $dualExercise = jQuery("#dual-exercise")
    val $singleExercise = jQuery("#single-exercise")
    val $squareExercise = jQuery("#square-exercise")

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

    $dualExercise click (() => {
      val exercise = new DualIntervalExcercise(player)
      exercise.play()
      import DualIntervalExcercise.{ANSWER_BOTTOM_NAME, ANSWER_TOP_NAME}
      println(ANSWER_TOP_NAME + ": " +
//        This is how you get correct, human-Readable answer
        exercise.getAnswers(ANSWER_TOP_NAME).filter(_.isCorrect).map(answer => Answer.getHumanReadAble(answer.interval)).head)
      println(ANSWER_BOTTOM_NAME + ": " + 
        exercise.getAnswers(ANSWER_BOTTOM_NAME).filter(_.isCorrect).map(answer => Answer.getHumanReadAble(answer.interval)).head)
    })

    $singleExercise click(() => {
      val exercise = new SingleIntervalExercise(player)
      exercise.play()
      println(SingleIntervalExercise.ANSWER + ": " + exercise.getAnswers(SingleIntervalExercise.ANSWER).filter(_.isCorrect).map(answer => Answer.getHumanReadAble(answer.interval)).head)
    })

    $squareExercise click (() => {
      val exercise = new BaseSquareIntervalExercise(player)
      exercise.play()
      for (answer <- exercise.getAnswers.keys) {
        println(answer + ": " + exercise.getAnswers(answer).filter(_.isCorrect).map(ans => Answer.getHumanReadAble(ans.interval)).head)
      }
    })
  }
}
