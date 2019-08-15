package dyn4k

import dyn4k.delegation.inject
import dyn4k.geometry.DynAppletChild
import org.dyn4j.dynamics.Body
import org.dyn4j.dynamics.BodyFixture
import org.dyn4j.dynamics.World
import org.dyn4j.geometry.Convex
import org.dyn4j.geometry.MassType

class DynBody : Body() {

    private val parent: DynApplet by inject()
    private val shapes: List<DynAppletChild>
        get() = fixtures.mapNotNull { it.shape as? DynAppletChild }
    var massType: MassType = MassType.INFINITE
        set(value) {
            field = value
            setMass(value)
        }

    fun draw() {
        with(parent) {
            pushMatrix()
            translate(transform.translationX, transform.translationY)
            rotate(transform.rotation)
            shapes.forEach { it.draw() }
            popMatrix()
        }
    }
}

val World.dynBodies: List<DynBody>
    get() = bodies.mapNotNull { it as? DynBody }

fun world(op: World.() -> Unit): World = World().also(op)

fun World.body(op: DynBody.() -> Unit): Body =
        DynBody().also {
            it.op()
            addBody(it)
        }

inline fun DynBody.fixture(shape: DynAppletChild, op: BodyFixture.() -> Unit): BodyFixture =
        BodyFixture(shape as Convex).also {
            it.op()
            addFixture(it)
        }