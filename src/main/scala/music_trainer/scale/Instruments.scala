package scale

import scala.scalajs.js.Math

abstract class Instrument(val name: String) {
    def attack() : Double;
    def dampen(sampleRate: Double, frequency: Double, volume: Double) : Double;
    def wave(i: Int, sampleRate: Double, frequency: Double) : Double;
}

case object Piano extends Instrument("piano") {
    @Override
    def attack() : Double = 0.002;

    @Override
    def dampen(sampleRate: Double, frequency: Double, volume: Double) : Double = {
        Math.pow(0.5*Math.log((frequency*volume)/sampleRate),2)
    }

    @Override
    def wave(i: Int, sampleRate: Double, frequency: Double) : Double = {
        // Pure dark magic
        val base = (y: Int, x: Double) => Math.sin(2 * Math.PI * ((y.toDouble / sampleRate) * frequency) + x)
        val modulate = (y: Int, x: Double) => Math.sin(4 * Math.PI * ((y.toDouble / sampleRate) * frequency) + x)
        modulate(i, 
            Math.pow(base(i, 0), 2) +
                    (0.75 * base(i, 0.25)) +
                    (0.1 * base(i, 0.5)) 
        )
    }
}

case object Organ extends Instrument("organ") {
    @Override
    def attack() : Double = 0.3;

    @Override
    def dampen(sampleRate: Double, frequency: Double, volume: Double) : Double = {
       1+(frequency * 0.01)
    }

    @Override
    def wave(i: Int, sampleRate: Double, frequency: Double) : Double = {
        // Pure dark magic
        val base = (y: Int, x: Double) => Math.sin(2 * Math.PI * ((y.toDouble / sampleRate) * frequency) + x)
        val modulate = (y: Int, x: Double) => Math.sin(4 * Math.PI * ((y.toDouble / sampleRate) * frequency) + x)
        modulate(i, 
                base(i, 0) +
				0.5*base(i, 0.25) +
				0.25*base(i, 0.5)
        )
    }
}