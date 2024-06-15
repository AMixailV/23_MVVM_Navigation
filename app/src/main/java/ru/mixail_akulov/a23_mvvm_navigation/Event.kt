package ru.mixail_akulov.a23_mvvm_navigation

class Event<T>(private val value: T) {
    private var handled: Boolean = false

    fun getValue(): T? {
        if (handled) return null
        handled = true
        return value
    }

}