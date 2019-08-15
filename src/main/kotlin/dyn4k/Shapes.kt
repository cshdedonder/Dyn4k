package dyn4k

import dyn4k.inject.inject
import org.dyn4j.geometry.Capsule
import org.dyn4j.geometry.Circle

data class PColor(val red: Int, val green: Int, val blue: Int, val alpha: Int = 255) {
    companion object {
        val black = PColor(0, 0, 0)
        val white = PColor(255, 255, 255)
    }
}

data class RenderConfig(
        val fill: PColor = PColor.white,
        val stroke: PColor = PColor.black,
        val width: Float = 2f
) {
    companion object {
        val default: RenderConfig = RenderConfig()
    }
}

interface DynAppletChild {
    val parent: DynApplet
    val config: RenderConfig

    fun draw()
}

class BaseDynAppletChild(override val config: RenderConfig) : DynAppletChild {

    override val parent: DynApplet by inject()

    override fun draw() {
        with(parent) {
            stroke = config.stroke
            fill = config.fill
            strokeWeight(config.width)
        }
    }
}

class PCircle private constructor(radius: Double, private val child: DynAppletChild)
    : Circle(radius), DynAppletChild by child {

    constructor(radius: Double, config: RenderConfig = RenderConfig.default) : this(radius, BaseDynAppletChild(config))

    override fun draw() {
        child.draw()
        parent.circle(center.x, center.y, radius)
    }
}

class PCapsule private constructor(width: Double, height: Double, private val child: DynAppletChild)
    : Capsule(width, height), DynAppletChild by child {

    constructor(width: Double, height: Double, config: RenderConfig = RenderConfig.default) : this(width, height, BaseDynAppletChild(config))

    override fun draw() {
        child.draw()
        //TODO implement
    }
}

//
//class PEllipse(width: Double, height: Double, private val config: RenderConfig = RenderConfig.default)
//    : Ellipse(width, height), Drawable, Child by ImplChild() {
//
//    override fun draw() {
//        Drawable.prepare(parent, config)
//        // TODO implement
//    }
//}
//
//class PHalfEllipse(width: Double, height: Double, private val config: RenderConfig = RenderConfig.default)
//    : HalfEllipse(width, height), Drawable, Child by ImplChild() {
//
//    override fun draw() {
//        Drawable.prepare(parent, config)
//        // TODO implement
//    }
//}
//
//class PPolygon(vararg vertices: Vector2, private val config: RenderConfig = RenderConfig.default)
//    : Polygon(*vertices), Drawable, Child by ImplChild() {
//
//    override fun draw() {
//        Drawable.prepare(parent, config)
//        with(parent) {
//            beginShape()
//            vertices.forEach { (x, y) -> vertex(x, y) }
//            endShape(CLOSE)
//        }
//    }
//}
//
//class PSegment(p1: Vector2, p2: Vector2, private val config: RenderConfig = RenderConfig.default)
//    : Segment(p1, p2), Drawable, Child by ImplChild() {
//
//    override fun draw() {
//        Drawable.prepare(parent, config)
//        // TODO implement
//    }
//}
//
//class PSlice(radius: Double, theta: Double, private val config: RenderConfig = RenderConfig.default)
//    : Slice(radius, theta), Drawable, Child by ImplChild() {
//
//    override fun draw() {
//        Drawable.prepare(parent, config)
//        // TODO implement
//    }
//}
