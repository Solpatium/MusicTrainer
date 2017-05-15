package scale
import scala.collection.immutable.HashMap

object Note extends Enumeration {
    type Note = Value
    val `C`, `C#`, `D`, `D#`, `E`, `F`, `F#`, `G`, `G#`, `A`, `A#`, `B` = Value 
    val frequencies = HashMap(
        `C`  -> 261.63,
        `C#` -> 277.18,
        `D`  -> 293.66,
        `D#` -> 311.13,
        `E`  -> 329.63,
        `F`  -> 346.23,
        `F#` -> 369.99,
        `G`  -> 392.00,
        `G#` -> 415.30,
        `A`  -> 440.00,
        `A#` -> 466.16,
        `B`  -> 493.88
    )

    def frequency(note: Value) = frequencies(note)
}