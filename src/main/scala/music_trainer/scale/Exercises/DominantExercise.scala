package music_trainer.scale.Exercises
import music_trainer.scale.{Note, Sound, Track}

import scala.collection.mutable
import scala.util.Random

/**
  * Created by maksymilian on 27/06/2017.
  * @param difficultyLevel - this sets the difficulty level of the exercise. Possible levels:
  *                        0 - easy
  *                        1 - medium
  *                        2 - hard
  */
class DominantExercise(val difficultyLevel: Int) extends Exercise{
  if(!(0 to 2).toList.contains(difficultyLevel)) throw new IllegalArgumentException("difficultyLevel must be 0, 1, or 2")
  import DominantExercise._
  val position: Int = Random.nextInt(4)
  var accord:List[Note] = generateAccord()
  val track:Track = generateTrack()

  def generateTrack():Track = {
    if(Random.nextBoolean() || difficultyLevel == 0){
      if(Random.nextBoolean()) {
        accord = accord.sortWith((x: Note, y: Note) => x.toAbsoluteInt > y.toAbsoluteInt)
      } else {
        accord = accord.sortWith((x: Note, y: Note) => x.toAbsoluteInt < y.toAbsoluteInt)
      }
    }
    val track:Track = new Track()
    var delay:Float = 0
    if(Random.nextBoolean()) {
      delay = Random.nextFloat() / (difficultyLevel + 1)
    }
    val duration:Float = (MINIMAL_DURATION / (difficultyLevel + 1)) + Random.nextFloat()
    var time = 0.0
    for(note <- accord){
      track.add(time, new Sound(note,duration))
      time += delay
    }
    track
  }

  def generateAccord():List[Note] = {
    var octave = 4 + Random.nextInt(4)
    var accordBase = Random.nextInt(12)
    var accord: List[Note] = Nil
    for(i <- 0 until position){
      accord = Note.fromAbsoluteInt((octave + 1) * 12 + accordBase + intervals(i)) :: accord
    }
    accord = Note.fromAbsoluteInt((octave - 1) * 12 + accordBase + intervals(position)) :: accord
    for(i <- position + 1 to 3){
      accord = Note.fromAbsoluteInt(octave * 12 + accordBase + intervals(i)) :: accord
    }
    Random.shuffle(accord)
  }

  /**
    * this method returns all answers for the exercise as a map[String,List[Answer] ]. One exercise may have more than 1 correct
    * answer, where every answer is an answer for a specific question. Usually the answer for the whole exercise is not correct even
    * if the set of right answers is correct (ex. answers are swapped). Every answer has a field isCorrect which specifies which
    * answer is the correct one.
    */
  override def getAnswers: mutable.Map[String, List[Answer]] = {
    mutable.Map(ANSWER_NAME -> DominantAnswer.generateAnswersSet(this.position))
  }
}


object DominantExercise {
  val intervals = List(0,4,7,10)
  val ANSWER_NAME = "Przewrót dominanty:"
  /**
    * minimal duration of a
    */
  val MINIMAL_DURATION:Float = 2.toFloat
}
