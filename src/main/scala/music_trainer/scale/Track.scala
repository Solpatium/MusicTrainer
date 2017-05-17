package scale

import scala.collection.SortedMap
import scala.collection.mutable.TreeMap
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scale.Note._
import scale._

// Class used for playing multiple sounds at specific time
class Track {
    val sounds : TreeMap[Double, Set[Sound]] = new TreeMap()

    def add(time: Double, sound: Sound) {
        val set = if ( sounds.contains(time) ) sounds(time) else new HashSet[Sound]()
        sounds(time) = set+sound
    }

    def add(time: Double, note: Note, octave: Int, duration: Float) {
        val sound = Sound(note, octave, duration)
        add(time, sound) 
    }
}