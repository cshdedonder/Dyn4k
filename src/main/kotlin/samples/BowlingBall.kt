package samples

import org.dyn4j.dynamics.BodyFixture
import org.dyn4j.geometry.Geometry
import org.dyn4j.geometry.MassType
import samples.framework.SimulationBody
import samples.framework.SimulationFrame
import java.awt.Graphics2D

class BowlingBall : SimulationFrame("Bowling Ball", 128.0) {

    override fun initializeWorld() {
        with(SimulationBody()) {
            addFixture(Geometry.createRectangle(15.0, 0.2))
            setMass(MassType.INFINITE)
            world.addBody(this)
        }

        with(SimulationBody()) {
            val fixture = BodyFixture(Geometry.createCircle(0.109))
            with(fixture) {
                density = 194.82
                restitution = 0.5
            }
            addFixture(fixture)
            setMass(MassType.NORMAL)
            setLinearVelocity(2.0, 3.0)
            translate(-3.0, 3.0)
            world.addBody(this)
        }
    }

    override fun render(g: Graphics2D, elapsedTime: Double) {
        g.translate(-200, 0)
        super.render(g, elapsedTime)
    }
}

fun main() {
    BowlingBall().run()
}