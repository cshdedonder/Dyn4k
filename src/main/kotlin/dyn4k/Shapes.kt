package dyn4k

import org.dyn4j.geometry.*

data class PColor(val red: Int, val green: Int, val blue: Int, val alpha: Int = 255) {
    companion object {
        val black = PColor(0, 0, 0)
        val white = PColor(255, 255, 255)
    }
}

data class RenderConfig(
        val fill: PColor = PColor.white,
        val stroke: PColor = PColor.black,
        val width: Float = 2f//,
//        val grid: Boolean = true
) {
    companion object {
        val default: RenderConfig = RenderConfig()
    }
}

interface Child {
    val parent: DynApplet
}

interface Drawable {
    fun draw()

    companion object {
        fun base(parent: DynApplet, config: RenderConfig) {
            with(parent) {
                fill = config.fill
                stroke = config.stroke
                weight = config.width
            }
        }
    }
}

class ImplChild : Child {
    override val parent: DynApplet by inject()
}

class PCircle(radius: Double, private val config: RenderConfig = RenderConfig.default) : Circle(radius), Drawable, Child by ImplChild() {

    override fun draw() {
        Drawable.base(parent, config)
        with(center) {
            parent.circle(x, y, radius)
        }
    }
}

class PCapsule(width: Double, height: Double, private val config: RenderConfig = RenderConfig.default) : Capsule(width, height), Drawable, Child by ImplChild() {

    override fun draw() {
        Drawable.base(parent, config)
        // TODO implement
    }
}

class PEllipse(width: Double, height: Double, private val config: RenderConfig = RenderConfig.default) : Ellipse(width, height), Drawable, Child by ImplChild() {

    override fun draw() {
        Drawable.base(parent, config)
        // TODO implement
    }
}

class PHalfEllipse(width: Double, height: Double, private val config: RenderConfig = RenderConfig.default) : HalfEllipse(width, height), Drawable, Child by ImplChild() {

    override fun draw() {
        Drawable.base(parent, config)
        // TODO implement
    }
}

class PPolygon(vararg vertices: Vector2, private val config: RenderConfig = RenderConfig.default) : Polygon(*vertices), Drawable, Child by ImplChild() {

    override fun draw() {
        Drawable.base(parent, config)
        // TODO implement
    }
}

class PSegment(p1: Vector2, p2: Vector2, private val config: RenderConfig = RenderConfig.default) : Segment(p1, p2), Drawable, Child by ImplChild() {

    override fun draw() {
        Drawable.base(parent, config)
        // TODO implement
    }
}

class PSlice(radius: Double, theta: Double, private val config: RenderConfig = RenderConfig.default) : Slice(radius, theta), Drawable, Child by ImplChild() {

    override fun draw() {
        Drawable.base(parent, config)
        // TODO implement
    }
}
