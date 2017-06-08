package music_trainer.scale.Exercises

import music_trainer.scale.{Note, Player, Sound, Track}

import scala.collection.mutable
import scala.util.Random


/**
  * DualIntervalPlayer is an implementation for Exercise - simple excercise with 2 intervals played simultanously
 *
  * @param player - with this player the excercise will be played
  */
case class DualIntervalExcercise(player:Player) extends Exercise {
  import DualIntervalExcercise._
  private val intervalNum = Random.nextInt(24) - 12 :: Random.nextInt(24) - 12 :: Nil
  var firstInterval: (Note, Note) = generateInterval(intervalNum.head)
  var secondInterval: (Note, Note) = generateInterval(intervalNum(1), firstInterval._1.octave :: firstInterval._2.octave :: Nil)
  var track:Track = generateTrack()


  override def play(): Unit = this.player.play(track)

  override def getAnswers: mutable.Map[String,List[Answer]] = {
    if(firstInterval._1.octave > secondInterval._1.octave) {
      val answers = mutable.Map(ANSWER_TOP_NAME -> generateAnswer(intervalNum.head))
      answers.put(ANSWER_BOTTOM_NAME, generateAnswer(intervalNum(1)))
      answers
    } else {
      val answer = mutable.Map(ANSWER_BOTTOM_NAME -> generateAnswer(intervalNum(1)))
      answer.put(ANSWER_TOP_NAME, generateAnswer(intervalNum.head))
      answer
    }
  }

  private def generateTrack(): Track = {
    var track = new Track()
    var time = 0.0

    var duration = Random.nextFloat()
    duration = duration + MINIMAL_DURATION

    var delay = Random.nextFloat() + MINIMAL_DELAY

    track.add(time, new Sound(firstInterval._1, duration))
    track.add(time, new Sound(secondInterval._1, duration))

    if(Random.nextBoolean()) time += delay
    track.add(time, new Sound(firstInterval._2, duration))
    if(time == 0.0) time +=delay
    track.add(time, new Sound(secondInterval._2, duration))

    track
  }

  private def generateInterval(interval:Int, takenOctaves:List[Int] = List()): (Note, Note) = {
    val firstNote = generateRandomNote(takenOctaves)
    (firstNote, generateMatchingNote(firstNote, interval))
  }

  private def generateRandomNote(excludeOctaves:List[Int]): Note = {
    var octave = Random.nextInt(2) + 4
    while(excludeOctaves.contains(octave)) octave = Random.nextInt(5) + 1
    val noteNum = Random.nextInt(12)
    new Note(Note.fromInt(noteNum), octave)
  }

  private def generateMatchingNote(note: Note, interval: Int):Note = {
    val absNewNote = (12 * note.octave) + Note.toInt(note.note) - interval
    new Note(Note.fromInt(absNewNote % 12), absNewNote / 12)
  }
}

object DualIntervalExcercise {
  val ANSWER_BOTTOM_NAME = "bottom"
  val ANSWER_TOP_NAME = "top"
  /**
    * minimal note duration
    */
  val MINIMAL_DURATION: Float = 1.0.toFloat
  /**
    * minimal delay between intervals
    */
  val MINIMAL_DELAY: Float = 0.4.toFloat

  /**
    * this method generates list of all possible answers
    * @param correctInterval - this is the correct answer in the answers set
    * @return list of possible answers for the exercise
    */
  private def generateAnswer(correctInterval:Int):List[Answer] = {
    var correctIntervalAbs = math.abs(correctInterval)
    (for (i <- 0 to 12) yield if(i == correctIntervalAbs) new Answer(i, true) else new Answer(i,false))(collection.breakOut)
  }
}