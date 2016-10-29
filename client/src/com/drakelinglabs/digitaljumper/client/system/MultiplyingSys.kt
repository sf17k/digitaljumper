package com.drakelinglabs.digitaljumper.client.system

import com.artemis.ComponentMapper
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.component.Multiplying
import com.drakelinglabs.digitaljumper.client.component.Position
import com.drakelinglabs.digitaljumper.client.component.SimplePhysics
import com.drakelinglabs.digitaljumper.client.event.DamageEvent
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem

class MultiplyingSys : PassiveSystem() {

    lateinit var bus: EventSystem
    lateinit var game: GameSys
    lateinit var mMultiplying: ComponentMapper<Multiplying>
    lateinit var mPosition: ComponentMapper<Position>
    lateinit var mSimplePhysics: ComponentMapper<SimplePhysics>

    @Subscribe
    fun onDamage(ev: DamageEvent) {
        val mult = mMultiplying.get(ev.e)
        if (mult == null)
            return
        if (mult.size <= 1)
            return
        val pos = mPosition.get(ev.e)
        val phys = mSimplePhysics.get(ev.e)
        if (pos == null)
            return
        val v = if (phys != null) phys.v else Vector2()
        val throwAngle = MathUtils.PI2 * game.rng.nextFloat()
        for (i in 0..mult.numChildren - 1) {
            game.createAsteroid(
                    pos.p,
                    v.cpy().add(Vector2(GameSys.ASTEROID_INITIAL_SPEED, 0f).rotateRad(throwAngle + MathUtils.PI2 * i / mult.numChildren)),
                    MathUtils.PI2 * game.rng.nextFloat(), GameSys.ASTEROID_INITIAL_VROT,
                    mult.size - 1)
        }

        // prevent multiplying again on further damage
        mMultiplying.remove(ev.e)
    }

}
