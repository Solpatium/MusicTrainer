package music_trainer.webapp

import music_trainer.scale.Exercises._
import music_trainer.scale.Track
import org.scalajs.jquery.jQuery
import music_trainer.visualization.Visualization
import org.scalajs.dom
import org.scalajs.dom.Window
import org.scalajs.dom.html

import scala.scalajs.js.JSApp
import music_trainer.scale._

object MusicTrainer extends JSApp {
  def main(): Unit = {
    scaleDemo()
  }

  def scaleDemo() {
    val vis = new Visualization(dom.document.getElementById(Visualization.id).asInstanceOf[html.Canvas], Array(0, 0.1, 0.2) )
    val $notes = jQuery("#notes")
    val $octave = jQuery("#octave")
    val $octavePlayer = jQuery("#whole-octave")
    val $instrument = jQuery("#instrument")
    val $dualExercise = jQuery("#dual-exercise")
    val $singleExercise = jQuery("#single-exercise")
    val $squareExercise = jQuery("#square-exercise")
    val $dominantExercise = jQuery("#dominant-exercise")
    val $stop = jQuery("#stop")

    var player = new Player(new Instrument("Piano"))
    
    // Add all available instruments
    for( (instrument,_) <- Instrument.list) {
      $instrument append s"""<option value="$instrument">$instrument</option>""" 
    }
    // Add handler for instrument change
    $instrument change (() => {
      val instrument = new Instrument($instrument.value().asInstanceOf[String])
      player = new Player(instrument)
    })
    // Select first instrument
    val firstInstrument = $instrument.children(":first").value().asInstanceOf[String]
    println(firstInstrument)
    $instrument.value( firstInstrument ).trigger("change")

    $stop click (() => player.stop )

    var octave = 6
    $octave change (() => {
      octave = $octave.value().asInstanceOf[String].toInt
    })

    val length = 1
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
      val exercise = new DualIntervalExercise()
      player.play(exercise.track)
      import DualIntervalExercise.{ANSWER_BOTTOM_NAME, ANSWER_TOP_NAME}
      println(ANSWER_TOP_NAME + ": " +
//        This is how you get correct, human-Readable answer
        exercise.getAnswers(ANSWER_TOP_NAME).filter(_.isCorrect).head)
      println(ANSWER_BOTTOM_NAME + ": " + 
        exercise.getAnswers(ANSWER_BOTTOM_NAME).filter(_.isCorrect).head)
    })

    $singleExercise click(() => {
      val exercise = new SingleIntervalExercise()
      player.play(exercise.track)
      println(SingleIntervalExercise.ANSWER + ": " + exercise.getAnswers(SingleIntervalExercise.ANSWER).filter(_.isCorrect).head)
    })

    $squareExercise click (() => {
      val exercise = new BaseSquareIntervalExercise()
      player.play(exercise.track)
      for (answer <- exercise.getAnswers.keys) {
        println(answer + ": " + exercise.getAnswers(answer).filter(_.isCorrect).head)
      }
    })

    $dominantExercise click (() => {
      val exercise = new DominantExercise(3)
      player.play(exercise.track)
      for (answer <- exercise.getAnswers.keys) {
        println(answer + ": " + exercise.getAnswers(answer).filter(_.isCorrect).head)
      }
    })

  }
}
