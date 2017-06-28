package music_trainer.scale

import scala.collection.immutable.HashMap

class Note(val note: Note.NoteEnum, val octave: Int){
  def toAbsoluteInt: Int = Note.toInt(this.note) + 12 * this.octave

}

object Note extends Enumeration {
    type NoteEnum = Value
    val `C`, `C#`, `D`, `D#`, `E`, `F`, `F#`, `G`, `G#`, `A`, `A#`, `B` = Value
    val frequencies: Map[Note.NoteEnum, Double] = HashMap(
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
    val intValues: Map[Note.NoteEnum, Int] = HashMap(
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

  def frequency(note: Value): Double = frequencies(note)

  def toInt(note: Value): Int = intValues(note)

  def fromInt(value: Int):Note.NoteEnum = ValueByInt(value)

  def fromAbsoluteInt(value: Int) = new Note(fromInt(value%12), value / 12)

//  def getElem[Note](xs: List[Note], compareFun: (Note, Note) => Note): Note = {
//    @annotation.tailrec def iter(xs: List[Note], bestFitElem: Note): Note = xs match {
//      case List() => bestFitElem
//      case h :: t => iter(t, compareFun(h,bestFitElem))
//    }
//    xs match {
//      case Nil => new Note()
//      case h :: t => iter(t, h)
//    }
//  }
//
//  def +(x:Note, y:Note): Note = {
//    var resNumNote = (toInt(x.note) + 12 * x.octave) + (toInt(y.note) + 12 * y.octave)
//    new Note(Note.fromInt(resNumNote % 12), resNumNote / 12)
//  }
//  def -(x:Note, y:Note): Note = {
//    var resNumNote = (toInt(x.note) + 12 * x.octave) - (toInt(y.note) + 12 * y.octave)
//    new Note(Note.fromInt(resNumNote % 12), resNumNote / 12)
//  }
}