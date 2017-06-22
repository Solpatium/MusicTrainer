package music_trainer.scale.Exercises

import music_trainer.scale.Note

import scala.collection.mutable
import scala.util.Random

/**
  * Created by maksymilian on 06/06/2017.
  */

/**
  * This trait is a trait for all the excercises
  */
trait Exercise {
  /**
   * use this method to play the excercise
   */
  def play():Unit
  /**
    * this method returns all answers for the exercise as a map[String,List[Answer] ]. One exercise may have more than 1 correct
    * answer, where every answer is an answer for a specific question. Usually the answer for the whole exercise is not correct even
    * if the set of right answers is correct (ex. answers are swapped). Every answer has a field isCorrect which specifies which
    * answer is the correct one.
    */
  def getAnswers:mutable.Map[String,List[Answer]]
}

class Answer(var interval:Int, var isCorrect:Boolean){
  override def toString: String = Answer.getHumanReadAble(interval)
}
object Answer{
  val getHumanReadAble:mutable.Map[Int,String] = mutable.Map(
    0 -> "1",
    1 -> "2>",
    2 -> "2",
    3 -> "3>",
    4 -> "3",
    5 -> "4",
    6 -> "4<",
    7 -> "5",
    8 -> "6>",
    9 -> "6",
    10 -> "7",
    11 -> "7<",
    12 -> "8",
    13 -> "9>",
    14 -> "9",
    15 -> "10>",
    16 -> "10",
    17 -> "11",
    18 -> "11<",
    19 -> "12",
    20 -> "13>",
    21 -> "13",
    22 -> "14",
    23 -> "14<",
    24 -> "15"
  )
}

object ExerciseHelper {
  /**
    * this method generate a random note
    * @param excludeOctaves - list of octaves that are going to be excluded from the octaves list for the random note
    * @return Random Note
    */
  def generateRandomNote(excludeOctaves:List[Int]): Note = {
    var octave = Random.nextInt(2) + 4
    while(excludeOctaves.contains(octave)) octave = Random.nextInt(5) + 1
    val noteNum = Random.nextInt(12)
    new Note(Note.fromInt(noteNum), octave)
  }

  /**
    * This method generates a note that is a matching by interval note for the param 'note'
    * @param note - base note
    * @param interval - interval from the base note
    * @return new matchin Note
    */
  def generateMatchingNote(note: Note, interval: Int):Note = {
    val absNewNote = (12 * note.octave) + Note.toInt(note.note) + interval
    new Note(Note.fromInt(absNewNote % 12), absNewNote / 12)
  }

  /**
    * this method generates list of all possible answers
    * @param correctInterval - this is the correct answer in the answers set
    * @return list of possible answers for the exercise
    */
  def generateAnswersSet(correctInterval: Int, answerRange: Int):List[Answer] = {
    var correctIntervalAbs = math.abs(correctInterval)
    (for (i <- 0 to answerRange) yield if(i == correctIntervalAbs) new Answer(i, true) else new Answer(i,false))(collection.breakOut)
  }
}


