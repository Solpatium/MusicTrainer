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

class Visualization(val canvas: html.Canvas, val times: Iterable[Double]) extends randomInt{
  val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  var animating = false
  var circles = mutable.Buffer[Circle]()
  Visualization.resizeCanvas()

  // Display exploding animation
  def play {
    if( !animating ) {
      var i = 0
      for(time <- times) {
        val circle = circles(i)
        setTimeout(time*1000) {
          destroyCircle(circle)
        }
        i += 1
      }
      animate
    }
  }

  // If canvas changes then it should be redrawn
  canvas.addEventListener("change", (e: dom.Event) => draw)

  // Draw sounds
  init()

  def width() = canvas.width
  def height() = canvas.height

  def animate {
    animating = true
    draw
    if( circles.length == 0 ) {
      animating = false
      init
    } else {
      js.Dynamic.global.window.requestAnimationFrame(()=>animate)
    }
  }

  def draw() {
    context.clearRect(0, 0, width, height);
    circles = circles.filter( (c) => {
      c.draw(context, width, height)
      c.update
      c.radius>=1
    } )
  }

  def init() {
    circles.clear
    for( i <- 0 until times.size ) {
      val position = new Vec(((i+1).toDouble)/(times.size+1), 0.5)
      circles prepend new Circle(position, 75, Circle.randomFill())
    }
    draw()
  }

  private def destroyCircle(circle: Circle) {
    // First grow, then shrink
    circle.sizeMultiplier = 1.05;
    setTimeout(100) {
      // Generate random circle and copy it's features (shrinking)
      val newCircle = Circle.random(circle.position, circle.radius)
      circle.sizeMultiplier = newCircle.sizeMultiplier
      circle.movement = newCircle.movement
    }

    // Add new circles
    for( _ <- 0 to 100 )
      circles prepend Circle.random(circle.position, circle.radius)
  }
}

object Visualization {
  js.Dynamic.global.window.addEventListener("resize", () => resizeCanvas(), true);
  resizeCanvas

  val id = "visualizer"
  def resizeCanvas() {
    val canvas = document.getElementById(id).asInstanceOf[html.Canvas]
    if( canvas == null ) return;

    val canvasWidth = canvas.width
    val parentWidth = canvas.parentElement.getBoundingClientRect().width
    if( canvasWidth != parentWidth) {
      canvas.width = parentWidth.toInt
      val event = document.createEvent("HTMLEvents");
      event.initEvent("change", false, true);
      canvas.dispatchEvent(event);
    }
  }
}
