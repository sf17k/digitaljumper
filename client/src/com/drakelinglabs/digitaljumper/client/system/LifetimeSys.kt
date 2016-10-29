package com.drakelinglabs.digitaljumper.client.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.drakelinglabs.digitaljumper.client.component.Lifetime

class LifetimeSys : IteratingSystem(Aspect.all(
        Lifetime::class.java
)) {

    lateinit var mLifetime: ComponentMapper<Lifetime>

    override fun process(e: Int) {
        val life = mLifetime.get(e)
        life.t -= world.delta
        if (life.t < 0f)
            world.delete(e)
    }

}
