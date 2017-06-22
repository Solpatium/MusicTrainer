package music_trainer.scale.Exercises

import music_trainer.scale.{Note, Player, Sound, Track}

import scala.collection.mutable
import scala.util.Random


/**
  * DualIntervalPlayer is an implementation for Exercise - simple excercise with 2 intervals played simultanously
 *
  * @param player - with this player the exercise will be played
  */
case class DualIntervalExcercise(player:Player) extends Exercise {
  import DualIntervalExcercise._
  private val intervalNum = Random.nextInt(24) - 12 :: Random.nextInt(24) - 12 :: Nil
  var firstInterval: (Note, Note) = generateInterval(intervalNum.head)
  var secondInterval: (Note, Note) = generateInterval(intervalNum(1), firstInterval._1.octave :: firstInterval._2.octave :: Nil)
  var track:Track = generateTrack()


  override def play(): Unit = this.player.play(track)

  override def getAnswers: mutable.Map[String,List[Answer]] = {
    import ExerciseHelper._
    if(firstInterval._1.octave > secondInterval._1.octave) {
      val answers = mutable.Map(ANSWER_TOP_NAME -> generateAnswersSet(intervalNum.head, 12))
      answers.put(ANSWER_BOTTOM_NAME, generateAnswersSet(intervalNum(1), 12))
      answers
    } else {
      val answer = mutable.Map(ANSWER_BOTTOM_NAME -> generateAnswersSet(intervalNum(1), 12))
      answer.put(ANSWER_TOP_NAME, generateAnswersSet(intervalNum.head, 12))
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

  private def generateInterval(interval:Int, takenOctaves:List[Int] = List()): (Note, Note) = {
    val firstNote = ExerciseHelper generateRandomNote takenOctaves
    (firstNote, ExerciseHelper generateMatchingNote(firstNote, interval))
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
}