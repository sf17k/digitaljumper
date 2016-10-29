package com.drakelinglabs.digitaljumper.client

import com.artemis.Component
import com.badlogic.gdx.math.Vector2

object Utils {

    // assumes all Components contain only simple data and Vector2's
    // warning: some hairy reflection
    // don't be surprised if it breaks in complicated ways
    fun copyComponent(src: Component): Component {
        val cls = src.javaClass
        val dst = cls.getConstructor().newInstance()
        cls.declaredFields.forEach {
            // fields are actually private
            // guessing because Kotlin hides them behind getters/setters
            // so we have to use those
            val cname = it.name.capitalize()
            val ftype = it.type
            val getter = cls.getMethod("get" + cname)
            val setter = cls.getMethod("set" + cname, ftype)
            if (ftype == Vector2::class.java) {
                // deep copy Vector2's
                (getter.invoke(dst) as Vector2).set(getter.invoke(src) as Vector2)
            } else {
                // shallow copy everything else
                setter.invoke(dst, getter.invoke(src))
            }
        }
        return dst
    }

}
