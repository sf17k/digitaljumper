package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.Assets

class Ship() : Go() {

    val TURN_SPEED = 0.1f
    val ACCEL = 0.1f

    override fun getSprite() = Assets.ship

    override fun tick(delta: Float) {
        pos.add(vel.cpy().scl(delta))
        //angle += TURN_SPEED * delta
    }

}
