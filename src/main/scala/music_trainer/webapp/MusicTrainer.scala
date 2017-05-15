package tutorial.webapp

import org.scalajs.jquery.jQuery
import scala.scalajs.js.JSApp
import scale.Note
import scale.Sound
import scale.Player
import scale.Piano

object MusicTrainer extends JSApp {
  def main(): Unit = {
    scaleDemo()
  }

  def scaleDemo() {
    val $notes = jQuery("#notes");
    val $octave = jQuery("#octave")

    var octave = 4
    $octave.change(()=>{
      octave = $octave.value().asInstanceOf[Int]
    })

    val length = 2
    val player = new Player(Piano)
    for( note <- Note.values ) {
      val name = note.toString. replaceAllLiterally("$hash", "#")
      println(name)
      val button = jQuery(s"""<button value="$name">$name</name>""").click(()=>{
        player.play(Sound(note, octave, length))
      })
      $notes.append(button)
    }
  }
}
