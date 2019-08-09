package dyn4k.inject

import kotlin.reflect.KProperty

class InjectionScope {
    val scope: MutableList<Any> = mutableListOf()

    companion object {
        val default = InjectionScope()
    }

    fun register(any: Any) {
        scope += any
    }

    inline fun <reified T : Any> get(): T? = scope.find { it is T } as? T
}

class Inject(val scope: InjectionScope) {

    var any: Any? = null

    inline operator fun <reified T : Any> getValue(thisRef: Any?, property: KProperty<*>): T {
        return (if (any == null) scope.get<T>()?.also { any = it } else any as? T)
                ?: throw IllegalStateException("Injection is not initialised")
    }
}

fun inject(scope: InjectionScope = InjectionScope.default): Inject = Inject(scope)