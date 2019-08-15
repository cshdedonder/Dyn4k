package dyn4k.delegation

import kotlin.reflect.KProperty

class SingleAssign<T : Any> {

    private lateinit var obj: T

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return obj
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this::obj.isInitialized) {
            throw IllegalStateException("${property.name} in $thisRef has already been assigned!")
        }
        obj = value
    }
}

fun <T : Any> singleAssign(): SingleAssign<T> = SingleAssign()