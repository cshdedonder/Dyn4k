import dyn4k.*
import dyn4k.delegation.singleAssign
import dyn4k.geometry.PCircle
import org.dyn4j.dynamics.World
import org.dyn4j.geometry.MassType

object CircleTest : DynApplet() {

    private var world: World by singleAssign()

    override fun settings() = size(800, 800)

    override fun setup() {
        world = world {
            body {
                fixture(PCircle(50.0)) {
                    density = 194.82
                    restitution = 0.05
                }
                massType = MassType.NORMAL
                setLinearVelocity(20.0, 60.0)
                translate(100.0, 100.0)
            }
        }
    }

    override fun draw() {
        super.draw()
        background(255)
        world.update(frameRateLastNanos * 1E-09)
        world.dynBodies.forEach { it.draw() }
    }
}

fun main() {
    CircleTest.run()
}
