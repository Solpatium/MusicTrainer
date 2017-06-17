package music_trainer.scale

import scala.collection.immutable.{HashSet, Set}
import scala.collection.mutable

// Class used for playing multiple sounds at specific time
class Track {
    val sounds : mutable.ListMap[Double, Set[Sound]] = new mutable.ListMap()

    def add(time: Double, sound: Sound) {
        val set = if ( sounds.contains(time) ) sounds(time) else new HashSet[Sound]()
        sounds(time) = set+sound
    }

    def add(time: Double, note: Note.Value, octave: Int, duration: Float) {
        val sound = Sound(note, octave, duration)
        add(time, sound)
    }
}
