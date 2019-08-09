package dyn4k.inject

import kotlin.reflect.KProperty
import kotlin.reflect.full.createInstance

class InjectionScope {
    private val _scope: MutableList<Any> = mutableListOf()
    val scope: List<Any>
        get() = _scope

    companion object {
        val default = InjectionScope()
    }

    fun register(any: Any) {
        _scope += any
    }

    inline fun <reified T : Any> get(): T = scope.find { it is T } as? T ?: T::class.createInstance().also(::register)
}

class Inject(val scope: InjectionScope) {

    var any: Any? = null

    inline operator fun <reified T : Any> getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (any == null) scope.get<T>().also { any = it } else any as T
    }
}

fun inject(scope: InjectionScope = InjectionScope.default): Inject = Inject(scope)