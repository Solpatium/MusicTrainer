package scale

import org.scalajs.dom.raw.Blob
import scala.scalajs.js
import scala.scalajs.js.Math
import scale.Note._

case class Sound private (val note: Note, octave: Int, val duration: Float) {
  def frequency() = Note.frequency(note)
}