package music_trainer.scale

import scala.scalajs.js.Math
import scala.collection.immutable.HashMap


object Instrument {
    val source = "http://gleitz.github.io/midi-js-soundfonts/FluidR3_GM/"
    val list = HashMap(
        "Piano" -> (2, "bright_acoustic_piano"),
        "Clavinet" -> (8, "clavinet"),
        "Guitar" -> (26, "acoustic_guitar_steel"),
        // Violin sounds ok only with short length (max 0.25)
        // "Violin" -> (41, "violin") 
    )
}

class Instrument(val name: String) {
    if( !Instrument.list.contains(name) ) {
        throw new IllegalArgumentException("Instrument not found") 
    }
    val codeName: String = Instrument.list(name)._2
    val code: Int = Instrument.list(name)._1
}