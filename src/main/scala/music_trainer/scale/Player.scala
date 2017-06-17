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
             val volumeMultiplier: Double = 0.6,
             val channels: Int = 1,
             val sampleRate: Int = 44100,
             val bitsPerSample: Int = 16) extends Wav {

    val MAX_VOLUME = 32768
    val volume = MAX_VOLUME*volumeMultiplier

    def play(sound: Sound) {
        val key = (instrument, sound)
        if( !Player.cached.contains(key) )
            generate(sound)

        playWav( Player.cached(key) )
    }

    def play(track: Track) {
        // Make sure all sounds are generated already, there can be no delay
        for( (_,  sounds) <- track.sounds ) {
            sounds foreach maybeGenerate
        }

        // We need to set a timeout to play sounds with a delay
        for( (time,  sounds) <- track.sounds ) setTimeout(time*1000) {
            sounds foreach play
        }
    }

    def maybeGenerate(sound: Sound) {
        if( !Player.cached.contains((instrument, sound)) )
            generate(sound)
    }

    def generate(sound: Sound) {
        val frequency = sound.frequency*Math.pow(2,sound.octave-4)
        val dampen = instrument.dampen(sampleRate, frequency, volume)
        val attack = instrument.attack()
        val attackLen = (sampleRate * attack);
	    val decayLen = (sampleRate * sound.duration);

        var value : Double = 0
        val data = new Uint8Array(Math.ceil(sampleRate * sound.duration * 2).toInt)
        for (i <- 0 to attackLen.toInt) {
            value = volume * (i.toDouble/(sampleRate*attack)) * instrument.wave(i, sampleRate, frequency)

            data(i << 1) = value.toShort
            data((i << 1) + 1) = (value.toInt >> 8).toShort

        }

        for (i <- attackLen.toInt to decayLen.toInt) {

            value = volume * Math.pow((1-((i-(sampleRate*attack))/(sampleRate*(sound.duration-attack)))),dampen) * instrument.wave(i, sampleRate, frequency)

            data(i << 1) = value.toShort
            data((i << 1) + 1) = (value.toInt >> 8).toShort
        }

        Player.cached((instrument, sound)) = wav(channels, sampleRate, bitsPerSample, data)
    }
}

object Player {
    private val cached : HashMap[Tuple2[Instrument,Sound], String] = new HashMap()
}

trait Wav {
    private def dataURL(content: Seq[Any], dataType: String) = {
        val blobContent = content.asInstanceOf[Seq[js.Any]].toJSArray
        val blobProperties = js.Dynamic.literal(
                "type" -> dataType
            ).asInstanceOf[BlobPropertyBag]
        val blob = new Blob(blobContent, blobProperties)
        URL.createObjectURL(blob)
    }

    private def uint8array(numbers: Int*)  = new Uint8Array(js.Array(numbers: _*))

    def wav(channels: Int, sampleRate: Int, bitsPerSample: Int, data: Uint8Array ) = {
            // TODO: Make more readable
	        // def pack(c,arg){ return [new Uint8Array([arg, arg >> 8]), new Uint8Array([arg, arg >> 8, arg >> 16, arg >> 24])][c]; };
            def pack(c: Int, arg: Int) : Uint8Array = {
                // short syntax doesn't work for some reason
                // ';' expected but 'if' found.
                if (c == 0)
                    return uint8array(arg, arg >> 8)
                else
                    return uint8array(arg, arg >> 8, arg >> 16, arg >> 24)
            }

            val waveData: Seq[Any] = Seq(
				"RIFF",
				pack(1, 4 + (8 + 24/* chunk 1 length */) + (8 + 8/* chunk 2 length */)), // Length
				"WAVE",
				// chunk 1
				"fmt ", // Sub-chunk identifier
				pack(1, 16), // Chunk length
				pack(0, 1), // Audio format (1 is linear quantization)
				pack(0, channels),
				pack(1, sampleRate),
				pack(1, sampleRate * channels * bitsPerSample / 8), // Byte rate
				pack(0, channels * bitsPerSample / 8),
				pack(0, bitsPerSample),
				// chunk 2
				"data", // Sub-chunk identifier
				pack(1, data.length * channels * bitsPerSample / 8), // Chunk length
				data
            )
            dataURL(waveData, "audio/wav")
    }

    def playWav(wavURL: String) {
        js.Dynamic.newInstance(js.Dynamic.global.Audio)(wavURL).play()
    }
}
