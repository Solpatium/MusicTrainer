package music_trainer.scale

import scala.collection.immutable.HashMap

class Note(val note: Note.Note, val octave: Int) { }

object Note extends Enumeration {
    type Note = Value
    val `C`, `C#`, `D`, `D#`, `E`, `F`, `F#`, `G`, `G#`, `A`, `A#`, `B` = Value
    val frequencies: Map[Note.Note, Double] = HashMap(
      `C`  -> 261.63,
      `C#` -> 277.18,
      `D`  -> 293.66,
      `D#` -> 311.13,
      `E`  -> 329.63,
      `F`  -> 346.23,
      `F#` -> 369.99,
      `G`  -> 392.00,
      `G#` -> 415.30,
      `A`  -> 440.00,
      `A#` -> 466.16,
      `B`  -> 493.88
    )
    val intValues: Map[Note.Note, Int] = HashMap(
      `C`  -> 0,
      `C#` -> 1,
      `D`  -> 2,
      `D#` -> 3,
      `E`  -> 4,
      `F`  -> 5,
      `F#` -> 6,
      `G`  -> 7,
      `G#` -> 8,
      `A`  -> 9,
      `A#` -> 10,
      `B`  -> 11
    )

  val ValueByInt: Map[Int, Note.Value] = Map() ++ intValues.map(_.swap)

  def frequency(note: Value):Double = frequencies(note)

  def toInt(note: Value):Int = intValues(note)

  def fromInt(value: Int):Note.Note = ValueByInt(value)
}