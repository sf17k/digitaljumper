package com.drakelinglabs.digitaljumper.client

import com.artemis.Component
import com.badlogic.gdx.math.Vector2
import kotlin.reflect.*

object Utils {

    // Assumes all Components contain only simple data and Vector2's.
    // FIXME: Lags game for a second first time it's called.
    fun copyComponent(src: Component): Component {
        val cls = src.javaClass.kotlin // is this right?
        val dst = cls.primaryConstructor!!.call()
        cls.memberProperties.forEach {
            if (it is KMutableProperty<*>) {
                if (it.returnType.equals(Vector2::class.defaultType)) {
                    // deep copy Vector2's
                    (it.get(dst) as Vector2).set(it.get(src) as Vector2)
                } else {
                    // shallow copy everything else
                    it.setter.call(dst, it.get(src))
                }
            }
        }
        return dst
    }

}
