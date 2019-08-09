package samples.framework

import org.dyn4j.dynamics.Body
import org.dyn4j.dynamics.BodyFixture
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D

open class SimulationBody(var color: Color = getRandomColor()) : Body() {

    fun render(g: Graphics2D, scale: Double) {
        render(g, scale, color)
    }

    fun render(g: Graphics2D, scale: Double, color: Color) {
        val pr = 4
        val ot = g.transform
        val lt = AffineTransform()

        with(lt) {
            translate(transform.translationX * scale, transform.translationY * scale)
            rotate(transform.rotation)
        }

        g.transform(lt)

        fixtures.forEach { renderFixture(g, scale, it, color) }

        val ce = Ellipse2D.Double(
                localCenter.x * scale - pr * 0.5,
                localCenter.y * scale - pr * 0.5,
                pr.toDouble(),
                pr.toDouble()
        )
        with(g) {
            setColor(Color.WHITE)
            fill(ce)
            setColor(Color.DARK_GRAY)
            draw(ce)

            transform = ot
        }
    }

    protected fun renderFixture(g: Graphics2D, scale: Double, fixture: BodyFixture, col: Color) {
        val convex = fixture.shape
        var color = col
        if (isAsleep) {
            color = col.brighter()
        }
        render(g, convex, scale, color)
    }
}