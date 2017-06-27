package music_trainer.scale

case class Sound private (note: Note.Value, octave: Int, duration: Double = 1) {
  def this(note: Note, duration: Float) = this(note.note, note.octave, duration)

  def frequency():Double = Note.frequency(note)

  def midiValue(): Int = (octave-1)*12 + Note.toInt(note);
}