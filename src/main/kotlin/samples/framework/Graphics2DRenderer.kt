package samples.framework

import org.dyn4j.geometry.*
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.Path2D
import kotlin.random.Random

fun render(g: Graphics2D, shape: Shape, scale: Double, color: Color = Color.ORANGE) {
    when (shape) {
        is Circle -> render(g, shape, scale, color)
        is Polygon -> render(g, shape, scale, color)
        is Segment -> render(g, shape, scale, color)
        is Capsule -> render(g, shape, scale, color)
        is Ellipse -> render(g, shape, scale, color)
        is Slice -> render(g, shape, scale, color)
        is HalfEllipse -> render(g, shape, scale, color)
    }
}

fun render(g: Graphics2D, circle: Circle, scale: Double, color: Color) {
    val radius = circle.radius
    val center = circle.center

    val radius2 = 2.0 * radius
    val c = Ellipse2D.Double((center.x - radius) * scale,
            (center.y - radius) * scale,
            radius2 * scale,
            radius2 * scale)

    with(g) {
        setColor(color)
        fill(c)
        setColor(getOutlineColor(color))
        draw(c)
    }

    val l = Line2D.Double(
            center.x * scale,
            center.y * scale,
            (center.x + radius) * scale,
            center.y * scale)
    g.draw(l)
}

fun render(g: Graphics2D, polygon: Polygon, scale: Double, color: Color) {
    val vertices = polygon.vertices
    val l = vertices.size

    val p = Path2D.Double()
    p.moveTo(vertices[0].x * scale, vertices[0].y * scale)
    for (i in 1 until l) {
        p.lineTo(vertices[i].x * scale, vertices[i].y * scale)
    }
    p.closePath()

    with(g) {
        setColor(color)
        fill(p)
        setColor(getOutlineColor(color))
        draw(p)
    }
}

fun getOutlineColor(color: Color): Color {
    with(color.darker()) {
        return Color(red, green, blue, color.alpha)
    }
}

fun getRandomColor(): Color = Color(Random.nextFloat() * 0.5f + 0.5f, Random.nextFloat() * 0.5f + 0.5f, Random.nextFloat() * 0.5f + 0.5f)