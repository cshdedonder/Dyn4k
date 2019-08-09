import dyn4k.DynApplet
import dyn4k.PCircle
import org.dyn4j.dynamics.Body

object CircleTest : DynApplet() {

    val body = Body()

    override fun settings() = size(400, 400)

    override fun setup() {
        body.addFixture(PCircle(50.0))
        body.translate(100.0, 100.0)
    }

    override fun draw() {
        body.fixtures.forEach { (it.shape as PCircle).draw() }
    }
}

fun main() {
    CircleTest.run()
}