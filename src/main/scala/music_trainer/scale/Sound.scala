package music_trainer.scale

case class Sound private (note: Note.Value, octave: Int, duration: Float) {
  def this(note: Note, duration: Float) = this(note.note, note.octave, duration)

  def frequency():Double = Note.frequency(note)
}