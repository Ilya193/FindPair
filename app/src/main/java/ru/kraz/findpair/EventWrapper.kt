package ru.kraz.findpair

interface EventWrapper<T> {
    fun getContentOrNot(callback: (T) -> Unit)

    class Single<T>(private var data: T) : EventWrapper<T> {
        private var protectionEnabled = false

        override fun getContentOrNot(callback: (T) -> Unit) {
            if (!protectionEnabled) {
                protectionEnabled = true
                callback(data)
            }
        }
    }
}