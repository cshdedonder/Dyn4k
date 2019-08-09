package samples

import org.dyn4j.dynamics.World
import org.dyn4j.geometry.Geometry
import org.dyn4j.geometry.MassType
import samples.framework.SimulationBody
import samples.framework.SimulationFrame
import java.awt.Graphics2D

class Billiards : SimulationFrame("Billiards", 300.0) {

    override fun initializeWorld() {
        world.gravity = World.ZERO_GRAVITY

        with(SimulationBody()) {
            addFixture(Geometry.createRectangle(0.2, 10.0))
            translate(2.0, 0.0)
            setMass(MassType.INFINITE)
            world.addBody(this)
        }
        with(SimulationBody()) {
            addFixture(Geometry.createCircle(0.028575), 217.97925, 0.08, 0.9)
            translate(-1.0, 0.0)
            setLinearVelocity(2.0, 0.0)
            setMass(MassType.NORMAL)
            world.addBody(this)
        }
        with(SimulationBody()) {
            addFixture(Geometry.createCircle(0.028575), 217.97925, 0.08, 0.9)
            translate(1.0, 0.0)
            setMass(MassType.NORMAL)
            world.addBody(this)
        }
    }

    override fun render(g: Graphics2D, elapsedTime: Double) {
        g.translate(-200, 0)
        super.render(g, elapsedTime)
    }
}

fun main() {
    Billiards().run()
}