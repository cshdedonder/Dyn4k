package dyn4k

import dyn4k.inject.InjectionScope
import processing.core.PApplet

open class DynApplet(scope: InjectionScope = InjectionScope.default) : PApplet() {

    init {
        @Suppress("LeakingThis")
        scope.register(this)
    }

    var fill: PColor? = null
        set(value) {
            if (value != null) {
                with(value) {
                    fill(red, green, blue, alpha)
                }
            } else {
                noFill()
            }
            field = value
        }

    var stroke: PColor? = null
        set(value) {
            if (value != null) {
                with(value) {
                    stroke(red, green, blue, alpha)
                }
            } else {
                noStroke()
            }
            field = value
        }
    var weight: Float = 0f
        set(value) {
            strokeWeight(value)
            field = value
        }

    fun circle(x: Double, y: Double, radius: Double) {
        circle(x.toFloat(), y.toFloat(), radius.toFloat())
    }

    fun fill(red: Int, green: Int, blue: Int) {
        fill(red.toFloat(), green.toFloat(), blue.toFloat())
    }

    fun stroke(red: Int, green: Int, blue: Int) {
        stroke(red.toFloat(), green.toFloat(), blue.toFloat())
    }

    fun fill(red: Int, green: Int, blue: Int, alpha: Int) {
        fill(red.toFloat(), green.toFloat(), blue.toFloat(), alpha.toFloat())
    }

    fun stroke(red: Int, green: Int, blue: Int, alpha: Int) {
        stroke(red.toFloat(), green.toFloat(), blue.toFloat(), alpha.toFloat())
    }

    fun run() = runSketch()
}