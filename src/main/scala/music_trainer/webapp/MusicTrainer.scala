package tutorial.webapp

import org.scalajs.jquery.jQuery
import scala.scalajs.js.JSApp
import scale._

object MusicTrainer extends JSApp {
  def main(): Unit = {
    scaleDemo()
  }

  def scaleDemo() {
    val $notes = jQuery("#notes")
    val $octave = jQuery("#octave")
    val $octavePlayer = jQuery("#whole-octave")
    val $instrument = jQuery("#instrument")

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
  }
}
