package com.tests.music_trainer
import music_trainer.scale.Exercises.ExerciseHelper
import music_trainer.scale.Note
import org.scalatest._

/**
  * Created by maksymilian on 22/06/2017.
  */
class ExerciseHelperTest extends FlatSpec with Matchers{

  "ExerciseHelper" should "generate Answers From 2 Notes" in {
    ExerciseHelper.getSimpleInterval(new Note(Note.`A#`,4),new Note(Note.C,4)) should be (10)
  }

}