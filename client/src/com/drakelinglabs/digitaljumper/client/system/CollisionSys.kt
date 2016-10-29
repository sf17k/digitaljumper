package com.drakelinglabs.digitaljumper.client.system

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.utils.IntBag
import com.drakelinglabs.digitaljumper.client.component.Collision
import com.drakelinglabs.digitaljumper.client.component.Position
import com.drakelinglabs.digitaljumper.client.event.CollisionEvent
import net.mostlyoriginal.api.event.common.EventSystem

class CollisionSys : BaseEntitySystem(Aspect.all(
        Position::class.java,
        Collision::class.java
)) {

    lateinit var bus: EventSystem
    lateinit var mPosition: ComponentMapper<Position>
    lateinit var mCollision: ComponentMapper<Collision>

    override fun processSystem() {
        val actives: IntBag = subscription.entities
        val ids: IntArray = actives.data
        for (i in 0..actives.size() - 1) {
            for (j in i + 1..actives.size() - 1) {
                val a = ids[i]
                val b = ids[j]
                val p1 = mPosition.get(a).p
                val p2 = mPosition.get(b).p
                val rad = mCollision.get(a).radius + mCollision.get(b).radius
                if (p1.dst2(p2) <= rad * rad) {
                    // collision
                    bus.dispatch(CollisionEvent(a, b))
                }
            }
        }
    }

}
