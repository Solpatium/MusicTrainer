package music_trainer.scale.Exercises

import scala.collection.mutable

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
