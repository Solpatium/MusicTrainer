package music_trainer.scale.Exercises

import music_trainer.scale.{Note, Player, Sound, Track}

import scala.collection.mutable
import scala.util.Random


/**
  * DualIntervalPlayer is an implementation for Exercise - simple excercise with 2 intervals played simultaneously
  */
case class DualIntervalExercise() extends Exercise {
  import DualIntervalExercise._
  private val intervalNum = Random.nextInt(24) - 12 :: Random.nextInt(24) - 12 :: Nil
  val firstInterval: (Note, Note) = generateInterval(intervalNum.head)
  val secondInterval: (Note, Note) = generateInterval(intervalNum(1), firstInterval._1 :: firstInterval._2:: Nil)
  val track:Track = generateTrack()

  override def getAnswers: mutable.Map[String,List[Answer]] = {
    import IntervalAnswer._
    if(firstInterval._1.octave > secondInterval._1.octave ||
      (firstInterval._1.octave == secondInterval._1.octave && firstInterval._1.note > secondInterval._1.note)) {
      val answers = mutable.Map(ANSWER_TOP_NAME -> generateAnswersSet(intervalNum.head, 12))
      answers.put(ANSWER_BOTTOM_NAME, generateAnswersSet(intervalNum(1), 12))
      answers
    } else {
      val answer = mutable.Map(ANSWER_TOP_NAME -> generateAnswersSet(intervalNum(1), 12))
      answer.put(ANSWER_BOTTOM_NAME, generateAnswersSet(intervalNum.head, 12))
      answer
    }
  }

  private def generateTrack(): Track = {
    val track = new Track()
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

  private def generateInterval(interval:Int, takenNotes:List[Note] = List()): (Note, Note) = {
    val firstNote = ExerciseHelper generateRandomNote takenNotes
    (firstNote, ExerciseHelper generateMatchingNote(firstNote, interval))
  }
}

object DualIntervalExercise {
  val ANSWER_BOTTOM_NAME = "Interwał dolny:"
  val ANSWER_TOP_NAME = "Interwał górny"
  /**
    * minimal note duration
    */
  val MINIMAL_DURATION: Float = 1.5.toFloat
  /**
    * minimal delay between intervals
    */
  val MINIMAL_DELAY: Float = 0.4.toFloat
}
