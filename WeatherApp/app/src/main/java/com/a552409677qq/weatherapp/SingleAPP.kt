package com.a552409677qq.weatherapp

import android.app.Application
import org.jetbrains.annotations.NotNull
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by loudj on 2017/6/18.
 * 实现APP的单例
 */

class App : Application() {
    object DelegatesExt {
        fun notNullSingleValue(): ReadWriteProperty<Any?, App> = NotNullSingleValueVar<App>()
    }
    companion object {
//        var instance: App by Delegates.notNull()
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}


private class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("already initialized")
    }
}

