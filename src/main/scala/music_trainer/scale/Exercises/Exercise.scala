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
    * this method returns answers for the exercise as a map[String,Int]. One exercise may have more than 1 answer,
    * where every answer is an answer for a specific question. Usually the answer for the whole exercise is not correct even
    * if the set of answers is correct (ex. answers are swapped).
    */
  def getAnswers:mutable.Map[String,Int]
}




