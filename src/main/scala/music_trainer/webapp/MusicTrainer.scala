package tutorial.webapp

import org.scalajs.jquery.jQuery
import scala.scalajs.js.JSApp
import scale.Note
import scale.Sound
import scale.Player
import scale.Piano
import scale.Track

object MusicTrainer extends JSApp {
  def main(): Unit = {
    scaleDemo()
  }

  def scaleDemo() {
    val $notes = jQuery("#notes")
    val $octave = jQuery("#octave")
    val $octavePlayer = jQuery("#whole-octave")

    var octave = 4
    $octave change (() => {
      octave = $octave.value().asInstanceOf[Int]
    })


    val length = 2
    val player = new Player(Piano)
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
