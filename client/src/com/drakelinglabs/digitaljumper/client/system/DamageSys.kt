package com.drakelinglabs.digitaljumper.client.system

import com.artemis.ComponentMapper
import com.drakelinglabs.digitaljumper.client.component.Damage
import com.drakelinglabs.digitaljumper.client.event.CollisionEvent
import com.drakelinglabs.digitaljumper.client.event.DamageEvent
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem

class DamageSys : PassiveSystem() {

    lateinit var bus: EventSystem
    lateinit var mDamage: ComponentMapper<Damage>

    @Subscribe
    fun onCollision(ev: CollisionEvent) {
        val dmga = mDamage.get(ev.a)
        if (dmga == null)
            return
        val dmgb = mDamage.get(ev.b)
        if (dmgb == null)
            return
        if (dmga.takesBitmask and dmgb.dealsBitmask != 0) {
            // hurt a
            bus.dispatch(DamageEvent(ev.a))
            world.delete(ev.a)
        }
        if (dmgb.takesBitmask and dmga.dealsBitmask != 0) {
            // hurt b
            bus.dispatch(DamageEvent(ev.b))
            world.delete(ev.b)
        }
    }

}
