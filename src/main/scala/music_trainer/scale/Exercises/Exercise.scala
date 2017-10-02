package music_trainer.scale.Exercises

import music_trainer.scale.Note
import music_trainer.scale.Track

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
    * this method returns all answers for the exercise as a map[String,List[Answer] ]. One exercise may have more than 1 correct
    * answer, where every answer is an answer for a specific question. Usually the answer for the whole exercise is not correct even
    * if the set of right answers is correct (ex. answers are swapped). Every answer has a field isCorrect which specifies which
    * answer is the correct one.
    */
  def getAnswers:mutable.Map[String,List[Answer]]
  val track: Track
}

class DominantAnswer(var position:Int, var isCorrectAnswer:Boolean) extends Answer{
  override def toString: String = DominantAnswer.getHumanReadAble(position)
  override def isCorrect: Boolean = isCorrectAnswer
}
object DominantAnswer extends AnswerFactory{
  override def generateAnswersSet(correctPosition: Int, answerRange: Int = 3): List[Answer] = {
    (for(i <- 0 to answerRange) yield if(i == correctPosition) new DominantAnswer(i, true) else new DominantAnswer(i, false))(collection.breakOut)
  }

  val getHumanReadAble:mutable.Map[Int,String] = mutable.Map(
    0 -> "7",
    1 -> "6/5",
    2 -> "4/3",
    3 -> "2"
  )

}

class IntervalAnswer(var interval:Int, var isCorrectAnswer:Boolean) extends Answer{
  override def toString: String = IntervalAnswer.getHumanReadAble(interval)
  override def isCorrect: Boolean = isCorrectAnswer
}
object IntervalAnswer extends AnswerFactory{
  /**
    * this method generates list of all possible answers
    * @param correctInterval - this is the correct answer in the answers set
    * @return list of possible answers for the exercise
    */
  def generateAnswersSet(correctInterval: Int, answerRange: Int):List[Answer] = {
    var correctIntervalAbs = math.abs(correctInterval)
    (for (i <- 0 to answerRange) yield if(i == correctIntervalAbs) new IntervalAnswer(i, true) else new IntervalAnswer(i,false))(collection.breakOut)
  }
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

trait AnswerFactory{def generateAnswersSet(correctAnswer:Int, answerRange: Int):List[Answer]}
trait Answer{def isCorrect:Boolean}

object ExerciseHelper {
  /**
    * this method generate a random note
    * @param excludeNotesRange - list of octaves that are going to be excluded from the octaves list for the random note
    * @return Random Note
    */
  def generateRandomNote(excludeNotesRange:List[Note]): Note = {
    var octave = Random.nextInt(4) + 4
    val noteNum = Random.nextInt(12)

    if(excludeNotesRange.length == 2){
      val min = math.min(excludeNotesRange.last.toAbsoluteInt, excludeNotesRange.head.toAbsoluteInt) - 12
      val max = math.max(excludeNotesRange.last.toAbsoluteInt, excludeNotesRange.head.toAbsoluteInt) + 12

      while(12 * octave + noteNum > min && 12 * octave + noteNum < max) octave = Random.nextInt(4) + 4
    }
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

  def getSimpleInterval(note1:Note, note2:Note): Int = math.abs(Note.toInt(note1.note) - Note.toInt(note2.note)) % 12

}

object ExerciseTypes extends Enumeration{
  type ExerciseTypes = Value
  val BaseSquareInterval, DominantEasy, DominantMedium, DominantHard, DualInterval,
      SingleIntervalEasyFirst, SingleIntervalMediumFirst, SingleIntervalHardFirst,
      SingleIntervalEasySecond, SingleIntervalMediumSecond, SingleIntervalHardSecond = Value

  def toString(hardness: Value): String =
    hardness match {
      case BaseSquareInterval => "Kwadrat interwałowy"
      case DominantEasy => "Dominanty Septymowe - poziom 1"
      case DominantMedium => "Dominanty Septymowe - poziom 2"
      case DominantHard => "Dominanty Septymowe - poziom 3"
      case DualInterval => "Interwał podwójny"
      case SingleIntervalEasyFirst => "Pojedynczy interwał w obrębie oktawy - poziom 1"
      case SingleIntervalMediumFirst => "Pojedynczy interwał w obrębie oktawy - poziom 2"
      case SingleIntervalHardFirst => "Pojedynczy interwał w obrębie oktawy - poziom 3"
      case SingleIntervalEasySecond => "Pojedynczy interwał w obrębie 2 oktaw - poziom 1"
      case SingleIntervalMediumSecond => "Pojedynczy interwał w obrębie 2 oktaw - poziom 2"
      case SingleIntervalHardSecond => "Pojedynczy interwał w obrębie 2 oktaw - poziom 3"
    }

  def content(exerciseType: Value): String =
    exerciseType match {
      case BaseSquareInterval => "Ćwiczenie polega na określeniu interwałów melodycznych oraz harmonicznych pomiędzy 4 dźwiękami (melodyczny górny oraz dolny, a także harmoniczny pierwszy oraz drugi)"
      case DominantEasy => "Ćwiczenie polega na rozpoznawaniu przewrotów akordów septymowych. Na tym poziomie akord będzie pojawiał się w wolnym tempie od w kolejności dźwięków od góry lub od dołu"
      case DominantMedium => "Ćwiczenie polega na rozpoznawaniu przewrotów akordów septymowych. Na tym poziomie dźwięki akordu nie będą pojawiać się w kolejności, a tempo będzie szybsze"
      case DominantHard => "Ćwiczenie polega na rozpoznawaniu przewrotów akordów septymowych. Na tym poziomie tempo będzie szybkie, a dźwięki akordu będą zwykle pojawiać się w różnej kolejności"
      case DualInterval => "Ćwiczenie polega na określeniu 2 interwałów melodycznych granych w tym samym czasie (jeden górny oraz jeden dolny)"
      case SingleIntervalEasyFirst => "Ćwiczenie polega na rozpoznawaniu interwałów prostych w obrębie jednej oktawy, które grane są melodycznie lub harmonicznie. Interwały będą grane w tempie wolnym"
      case SingleIntervalMediumFirst => "Ćwiczenie polega na rozpoznawaniu interwałów prostych w obrębie jednej oktawy, które grane są melodycznie lub harmonicznie. Interwały będą grane w tempie szybszym"
      case SingleIntervalHardFirst => "Ćwiczenie polega na rozpoznawaniu interwałów prostych w obrębie jednej oktawy, które grane są melodycznie lub harmonicznie. Interwały będą grane w tempie szybkim"
      case SingleIntervalEasySecond => "Ćwiczenie polega na rozpoznawaniu interwałów prostych w obrębie dwóch oktaw, które grane są melodycznie lub harmonicznie. Interwały będą grane w tempie wolnym"
      case SingleIntervalMediumSecond => "Ćwiczenie polega na rozpoznawaniu interwałów prostych w obrębie dwóch oktaw, które grane są melodycznie lub harmonicznie. Interwały będą grane w tempie szybszym"
      case SingleIntervalHardSecond => "Ćwiczenie polega na rozpoznawaniu interwałów prostych w obrębie dwóch oktaw, które grane są melodycznie lub harmonicznie. Interwały będą grane w tempie szybkim"
    }
}
