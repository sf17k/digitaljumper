package com.drakelinglabs.digitaljumper.client.system

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.utils.Bag
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.Utils
import com.drakelinglabs.digitaljumper.client.component.*
import com.drakelinglabs.digitaljumper.client.event.DamageEvent
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem

class MultiplyingSys : PassiveSystem() {

    lateinit var bus: EventSystem
    lateinit var game: GameSys
    lateinit var mMultiplying: ComponentMapper<Multiplying>
    lateinit var mSimplePhysics: ComponentMapper<SimplePhysics>
    lateinit var mAngle: ComponentMapper<Angle>
    lateinit var mScale: ComponentMapper<Scale>

    @Subscribe
    fun onDamage(ev: DamageEvent) {
        val mult = mMultiplying.get(ev.e)
        if (mult == null)
            return
        if (mult.size <= 1)
            return
        val phys = mSimplePhysics.get(ev.e)

        val v = if (phys != null) phys.v else Vector2()
        val splitAngle = MathUtils.PI2 * game.rng.nextFloat()

        // get components to copy to children
        val coms = Bag<Component>()
        world.componentManager.getComponentsFor(ev.e, coms)

        for (i in 0..mult.numChildren - 1) {
            // create entity
            val c = world.create()
            val ce = world.edit(c)

            // copy components to it
            coms.asIterable().forEach { ce.add(Utils.copyComponent(it)) }

            // adjust a few things
            val cphys = mSimplePhysics.get(c)
            if (cphys != null) {
                cphys.v.set(v)
                cphys.v.add(Vector2(mult.speed, 0f).rotateRad(splitAngle + MathUtils.PI2 * i / mult.numChildren))
            }

            val cangle = mAngle.get(c)
            if (cangle != null) {
                cangle.a = MathUtils.PI2 * game.rng.nextFloat()
            }

            val cmult = mMultiplying.get(c)
            cmult.size -= 1

            mScale.create(c).s *= cmult.size.toFloat() / (cmult.size + 1f)
        }

        // prevent multiplying again on further damage
        mMultiplying.remove(ev.e)
    }

}
