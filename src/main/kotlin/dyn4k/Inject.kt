package dyn4k

import processing.core.PApplet
import kotlin.reflect.KProperty

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

class InjectionScope {
    val scope: MutableList<Any> = mutableListOf()

    companion object {
        val default = InjectionScope()
    }

    fun register(any: Any) {
        scope += any
    }

    inline fun <reified T : Any> get(): T? = scope.find { it is T } as? T
}

class Inject(val scope: InjectionScope) {

    var any: Any? = null

    inline operator fun <reified T : Any> getValue(thisRef: Any?, property: KProperty<*>): T {
        return (if (any == null) scope.get<T>()?.also { any = it } else any as? T)
                ?: throw IllegalStateException("Injection is not initialised")
    }
}

fun inject(scope: InjectionScope = InjectionScope.default) = Inject(scope)