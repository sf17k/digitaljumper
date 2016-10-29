package com.drakelinglabs.digitaljumper.client.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.component.Angle
import com.drakelinglabs.digitaljumper.client.component.ShipControls
import com.drakelinglabs.digitaljumper.client.component.SimplePhysics

class ShipControlsSys : IteratingSystem(Aspect.all(
        ShipControls::class.java,
        Angle::class.java,
        SimplePhysics::class.java
)) {

    lateinit var mShipControls: ComponentMapper<ShipControls>
    lateinit var mAngle: ComponentMapper<Angle>
    lateinit var mSimplePhysics: ComponentMapper<SimplePhysics>

    override fun process(e: Int) {
        val controls = mShipControls.get(e)
        val angle = mAngle.get(e)
        val phys = mSimplePhysics.get(e)

        // accelerate
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            phys.v.add(Vector2(controls.accel * world.delta, 0f).rotateRad(angle.a))
        }
        // turn
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            angle.a += controls.turnSpeed * world.delta
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            angle.a -= controls.turnSpeed * world.delta
        }
    }

}
