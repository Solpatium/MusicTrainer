package music_trainer.scale.Exercises

import music_trainer.scale.{Note, Player, Sound, Track}

import scala.collection.mutable
import scala.util.Random


/**
  * DualIintervalPlayer is an implementation for Exercise - simple excercise with 2 intervals played simultanously
 *
  * @param player - with this player the excercise will be played
  */
case class DualIntervalExcercise(player:Player) extends Exercise {
  import DualIntervalExcercise._
  private val intervalNum = Random.nextInt(12) :: Random.nextInt(12) :: Nil
  var firstInterval: (Note, Note) = generateInterval(intervalNum.head)
  var secondInterval: (Note, Note) = generateInterval(intervalNum(1), firstInterval._1.octave :: firstInterval._2.octave :: Nil)
  var track:Track = generateTrack()


  override def play(): Unit = this.player.play(track)

  override def getAnswers: mutable.Map[String,Int] = {
    if(firstInterval._1.octave > secondInterval._1.octave) {
      var answer = mutable.Map(ANSWER_TOP_NAME -> intervalNum.head)
      answer.put(ANSWER_BOTTOM_NAME, intervalNum(1))
      answer
    } else {
      var answer = mutable.Map(ANSWER_BOTTOM_NAME -> intervalNum(1))
      answer.put(ANSWER_TOP_NAME, intervalNum.head)
      answer
    }
  }

  private def generateTrack(): Track = {
    var track = new Track()
    var time = 0.0

    var duration = Random.nextFloat()
    duration = if (duration > 0.5) duration else duration + 0.5.toFloat

    var delay = Random.nextFloat()

    track.add(time, new Sound(firstInterval._1, duration))
    track.add(time, new Sound(secondInterval._1, duration))
    time += delay
    track.add(time, new Sound(firstInterval._2, duration))
    track.add(time, new Sound(secondInterval._2, duration))

    track
  }

  private def generateInterval(interval:Int, takenOctaves:List[Int] = List()): (Note, Note) = {
    var firstNote = generateRandomNote(takenOctaves)
    (firstNote, generateMatchingNote(firstNote, interval))
  }

  private def generateRandomNote(excludeOctaves:List[Int]): Note = {
    var octave = Random.nextInt(5) + 1
    while(excludeOctaves.contains(octave)) octave = Random.nextInt(5) + 1
    var note = Random.nextInt(12)
    new Note(Note.fromInt(note), octave)
  }

  private def generateMatchingNote(note: Note, interval: Int):Note = {
    var newNoteOctave:Int = note.octave + ((Note.toInt(note.note) + interval) / 12)
    var newNoteValue = (Note.toInt(note.note) + interval) % 12
    new Note(Note.fromInt(newNoteValue), newNoteOctave)
  }
}

object DualIntervalExcercise {
  val ANSWER_BOTTOM_NAME = "bottom"
  val ANSWER_TOP_NAME = "top"
}