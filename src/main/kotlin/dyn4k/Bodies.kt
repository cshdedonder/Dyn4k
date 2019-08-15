package dyn4k

import dyn4k.inject.inject
import org.dyn4j.dynamics.Body
import org.dyn4j.dynamics.World

class DynBody : Body() {

    private val parent: DynApplet by inject()
    val shapes: List<DynAppletChild>
        get() = fixtures.mapNotNull { it.shape as? DynAppletChild }

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