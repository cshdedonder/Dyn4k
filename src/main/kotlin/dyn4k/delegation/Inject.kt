package dyn4k.delegation

import kotlin.reflect.KProperty
import kotlin.reflect.full.createInstance

/**
 * This class acts as a scope to be used with Dependency Injection. It contains a list of objects [scope] and they can
 * be called by using [get]. When multiple objects of the same type are present, retrieval is non-deterministic.
 */
class InjectionScope {
    @PublishedApi
    internal val scope: MutableList<Any> = mutableListOf()

    companion object {
        /**
         * The global scope used when no scope if provided.
         */
        val default = InjectionScope()
    }

    /**
     * Add [any] to this scope.
     */
    fun register(any: Any) {
        scope += any
    }

    /**
     * Retrieve an object if type [T] from this scope. If no such object can be found, this method will attempt
     * to create one. Throws an exception when this is not possible.
     * @see [kotlin.reflect.KClass.createInstance]
     */
    inline fun <reified T : Any> get(): T = scope.find { it is T } as? T ?: T::class.createInstance().also(::register)
}

/**
 * @see [inject]
 */
class Inject(val scope: InjectionScope) {

    @PublishedApi
    internal var any: Any? = null

    inline operator fun <reified T : Any> getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (this.any == null) scope.get<T>().also { any = it } else any as T
    }
}

/**
 * Returns an [Inject] object which is used with delegated properties. Optionally a [InjectionScope] can be provided.
 * Uses the global scope otherwise.
 * @see [InjectionScope]
 */
fun inject(scope: InjectionScope = InjectionScope.default): Inject = Inject(scope)