package music_trainer.scale

import scala.scalajs.js
import scala.collection.mutable.HashMap
import scala.scalajs.js.typedarray.Uint8Array
import js.JSConverters._
import org.scalajs.dom.raw.Blob
import org.scalajs.dom.raw.URL
import org.scalajs.dom.raw.BlobPropertyBag

import scala.scalajs.js.timers._


class Player(val instrument: Instrument,
             val velocity: Short = 127) {
    val properties = js.Dynamic.literal(
        "soundfontUrl" -> Instrument.source,
        "instrument" -> instrument.codeName,
        "onsuccess" -> (() => {
            MIDI.programChange(0, instrument.code-1)
        })
    );
    MIDI.loadPlugin(properties)    

    def play(sound: Sound) {
        MIDI.noteOn(0, sound.midiValue, velocity, 0)
        MIDI.noteOff(0, sound.midiValue, sound.duration)
    }

    def play(track: Track) {
        // We need to set a timeout to play sounds with a delay
        for( (time,  sounds) <- track.sounds ) setTimeout(time*1000) {
            sounds foreach play
        }
    }

    def stop() {
        // Not ready yet
    }
}

import js.annotation._

@js.native
@JSGlobal("MIDI")
object MIDI extends js.Object {
    def programChange(channel: Int, instrument: Int): Unit = js.native;
    def loadPlugin(options: js.Dynamic): Unit = js.native;
    def noteOn(channel: Int, note: Int, velocity: Int, delay: Double): Unit = js.native;
    def noteOff(channel: Int, note: Int, delay: Double): Unit = js.native;
}
