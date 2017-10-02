package music_trainer.scale.Exercises
import music_trainer.scale.{Note, Player, Sound, Track}

import scala.collection.mutable
import scala.util.Random

/**
  * Created by maksymilian on 08/06/2017.
  * @param difficultyLevel - simple parameter to set the difficulty level of the playback for the exercise -
  *                        the higher the harder exercise gets. Minimal value is 0, max = 5.
  *                        Values 0 to 2 limit the intervals to the 1 octave
  *                        3 to 5 limit the intervals to 2 octaves
  */
class SingleIntervalExercise(val difficultyLevel:Int) extends Exercise{
  if(difficultyLevel < 0 || difficultyLevel > 5) throw new IllegalArgumentException("difficultyLevel must be in range 0 to 5")
  var intervalNum: Int = Random.nextInt(24) - 12
  if(difficultyLevel > 2) intervalNum = Random.nextInt(48) - 24
  val interval: (Note, Note) = generateInterval
  val track:Track = generateTrack

  /**
    * this method returns all answers for the exercise as a map[String,List[Answer] ]. One exercise may have more than 1 correct
    * answer, where every answer is an answer for a specific question. Usually the answer for the whole exercise is not correct even
    * if the set of right answers is correct (ex. answers are swapped). Every answer has a field isCorrect which specifies which
    * answer is the correct one.
    */
  override def getAnswers: mutable.LinkedHashMap[String, List[Answer]] = mutable.LinkedHashMap(SingleIntervalExercise.ANSWER ->
    (if(difficultyLevel <= 2) IntervalAnswer.generateAnswersSet(intervalNum, 12) else IntervalAnswer.generateAnswersSet(intervalNum, 24)))

  private def generateInterval: (Note,Note) = {
    val note:Note = ExerciseHelper generateRandomNote Nil
    (note, ExerciseHelper.generateMatchingNote(note, intervalNum))
  }

  private def generateTrack:Track = {
    val track = new Track
    var time = 0.toFloat
    val duration = (Random.nextFloat() + SingleIntervalExercise.MINIMAL_DURATION) / (difficultyLevel % 3 + 1)
    val delay = (Random.nextFloat() * 2) / (difficultyLevel % 3 + 1)

    track.add(time,new Sound(interval._1, duration))
    time += delay
    track.add(time, new Sound(interval._2, duration))

    track
  }
}

object SingleIntervalExercise {
  val ANSWER: String = "Interwał:"
  val MINIMAL_DURATION: Float = 1.2.toFloat
}
