package music_trainer.visualization

import org.scalajs.dom
import org.scalajs.dom.Window
import org.scalajs.dom.html
import scala.math
import scala.util.Random
import scala.reflect.ClassTag
import org.scalajs.dom.document
import scala.scalajs.js.timers.setTimeout
import scala.collection.mutable
import scala.scalajs.js

case class Vec[T](val x: T, val y: T) {}

// Position is relative to canvas size ([0.5,0.5] is it's middle)
class Circle(var position: Vec[Double], 
             var radius: Double, 
             val fill: String, 
             var sizeMultiplier: Double = 1, 
             var movement: Vec[Double] = new Vec(0,0)) {
  def draw(context: dom.CanvasRenderingContext2D, width: Int, height: Int) {
    context.beginPath();
    context.arc(position.x*width, position.y*height, radius, 0, 2 * Math.PI, false);
    context.fillStyle = fill;
    context.fill();
  }
  def update() {
    radius = radius*sizeMultiplier
    position = new Vec(position.x + movement.x, position.y + movement.y)
  }
}

trait randomInt {
  protected def randomInt(min: Int, max: Int): Int = {
    math.floor(Random.nextDouble() * (max - min + 1)).toInt + min
  }
}

object Circle extends randomInt {
  val fancyColors = Array(
    // WHITE
    "#ffffff",
    "#fdfdfd",
    "#fdfeff"
    // BLUE
    // "#2e7da5",
    // "#399cce"
    // GREEN
    // "#8ddc9b",
    // "#38de1a",
    // RED
    // "#d63a3a",
    // YELLOW
    // "#ffeb00"
  )
  def random(position: Vec[Double], maxRadius: Double): Circle = {
    val movementLength = randomInt(1, 5).toDouble/200
    val angle = math.toRadians(randomInt(0,360))
    val movement = new Vec(math.cos(angle)*movementLength, math.sin(angle)*movementLength)
    val sizeMultiplier = randomInt(12,15).toDouble/16
    val color = randomFill()
    new Circle(position, randomInt(10, maxRadius.toInt).toDouble, color, sizeMultiplier, movement)
  }
  def randomFill() = fancyColors(randomInt(0, fancyColors.size-1))
}