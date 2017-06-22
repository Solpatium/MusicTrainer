package music_trainer.scale.Exercises
import music_trainer.scale.{Note, Player, Sound, Track}

import scala.collection.mutable
import scala.util.Random

/**
  * BaseSquareIntervalExercise is an implementation for Exercise - exercise with 4 notes and 4 intervals (2 melodical, 2 harminic)
  *
  * @param player - with this player the exercise will be played
  */
class BaseSquareIntervalExercise(val player: Player) extends Exercise {
  import BaseSquareIntervalExercise._
  private val melodicIntervalNum:List[Int] = Random.nextInt(24) - 12 :: Random.nextInt(24) - 12 :: Nil
  var firstInterval: (Note, Note) = generateInterval(melodicIntervalNum.head)
  var secondInterval: (Note, Note) = generateInterval(melodicIntervalNum(1), firstInterval._1.octave :: firstInterval._2.octave :: Nil)
  private val harmonicIntervalNum =
    ExerciseHelper.getSimpleInterval(firstInterval._1, secondInterval._1) ::
      ExerciseHelper.getSimpleInterval(firstInterval._2, secondInterval._2) :: Nil
  var track:Track = generateTrack()

  /**
    * use this method to play the excercise
    */
  override def play(): Unit = player.play(track)

  /**
    * this method returns all answers for the exercise as a map[String,List[Answer] ]. One exercise may have more than 1 correct
    * answer, where every answer is an answer for a specific question. Usually the answer for the whole exercise is not correct even
    * if the set of right answers is correct (ex. answers are swapped). Every answer has a field isCorrect which specifies which
    * answer is the correct one.
    */
  override def getAnswers: mutable.Map[String, List[Answer]] = {
    import ExerciseHelper._
    val answers = mutable.Map(ANSWER_HARMONIC_FIRST_NAME -> generateAnswersSet(harmonicIntervalNum.head, 12))
    answers.put(ANSWER_HARMONIC_SECOND_NAME, generateAnswersSet(harmonicIntervalNum(1), 12))
    if(firstInterval._1.octave > secondInterval._1.octave) {
      answers.put(ANSWER_TOP_NAME, generateAnswersSet(melodicIntervalNum.head, 12))
      answers.put(ANSWER_BOTTOM_NAME, generateAnswersSet(melodicIntervalNum(1), 12))
    } else {
      answers.put(ANSWER_BOTTOM_NAME, generateAnswersSet(melodicIntervalNum(1), 12))
      answers.put(ANSWER_TOP_NAME, generateAnswersSet(melodicIntervalNum.head, 12))
    }
    answers
  }

  private def generateInterval(interval:Int, takenOctaves:List[Int] = List()): (Note, Note) = {
    val firstNote = ExerciseHelper generateRandomNote takenOctaves
    (firstNote, ExerciseHelper generateMatchingNote(firstNote, interval))
  }

  private def generateTrack(): Track = {
    val track = new Track()
    var time = 0.0

    var duration = Random.nextFloat()
    duration = duration + MINIMAL_DURATION

    var delay = Random.nextFloat() + MINIMAL_DELAY

    track.add(time, new Sound(firstInterval._1, duration))
    track.add(time, new Sound(secondInterval._1, duration))

    time += delay
    track.add(time, new Sound(firstInterval._2, duration))
    track.add(time, new Sound(secondInterval._2, duration))

    track
  }
}

object BaseSquareIntervalExercise {
  val ANSWER_TOP_NAME = "Top"
  val ANSWER_BOTTOM_NAME = "Bottom"
  val ANSWER_HARMONIC_FIRST_NAME = "First Harmonic"
  val ANSWER_HARMONIC_SECOND_NAME = "Second Harmonic"
  /**
    * minimal note duration
    */
  val MINIMAL_DURATION: Float = 1.0.toFloat
  /**
    * minimal delay between intervals
    */
  val MINIMAL_DELAY: Float = 0.4.toFloat
}