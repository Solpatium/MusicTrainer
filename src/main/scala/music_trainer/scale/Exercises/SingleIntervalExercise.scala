package music_trainer.scale.Exercises
import music_trainer.scale.{Note, Player, Sound, Track}

import scala.collection.mutable
import scala.util.Random

/**
  * Created by maksymilian on 08/06/2017.
  */
class SingleIntervalExercise(val player: Player) extends Exercise{
  val intervalNum: Int = Random.nextInt(24) - 12
  val interval: (Note, Note) = generateInterval
  val track:Track = generateTrack
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
  override def getAnswers: mutable.Map[String, List[Answer]] = mutable.Map(SingleIntervalExercise.ANSWER -> ExerciseHelper.generateAnswersSet(intervalNum, 12))

  private def generateInterval: (Note,Note) = {
    val note:Note = ExerciseHelper generateRandomNote Nil
    (note, ExerciseHelper.generateMatchingNote(note, intervalNum))
  }

  private def generateTrack:Track = {
    val track = new Track
    var time = 0.0.toFloat
    val duration = Random.nextFloat() + SingleIntervalExercise.MINIMAL_DURATION
    val delay = Random.nextFloat()

    track.add(time,new Sound(interval._1,duration))
    time += delay
    track.add(time, new Sound(interval._2, duration))

    track
  }
}

object SingleIntervalExercise {
  val ANSWER: String = "Answer"
  val MINIMAL_DURATION: Float = 0.8.toFloat
}
