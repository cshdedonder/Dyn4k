//import dyn4k.PPolygon
import dyn4k.DynApplet
import dyn4k.DynBody
import dyn4k.PCircle
import dyn4k.dynBodies
import org.dyn4j.dynamics.BodyFixture
import org.dyn4j.dynamics.World
import org.dyn4j.geometry.MassType

object CircleTest : DynApplet() {

    val world = World()

    override fun settings() = size(800, 800)

    override fun setup() {
        with(DynBody()) {
            val fixture = BodyFixture(PCircle(50.0))
            with(fixture) {
                density = 194.82
                restitution = 0.5
            }
            addFixture(fixture)
            setMass(MassType.NORMAL)
            setLinearVelocity(20.0, 60.0)
            translate(100.0, 100.0)
            world.addBody(this)
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
//    Derived().message()
}

interface Base {
    fun message()
}

class BaseImpl : Base {
    override fun message() {
        println("Hello from BaseImple!")
    }
}

open class HiddenBaseImpl internal constructor(protected val _b: Base) : Base by _b

class Derived : HiddenBaseImpl(BaseImpl()) {

    override fun message() {
        _b.message()
        println("Hello from Derived!")
    }
}

