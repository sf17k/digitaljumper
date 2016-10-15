package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.Assets

class Ship() : Go() {

    val TURN_SPEED = 3.8f
    val ACCEL = 500f

    override fun getSprite() = Assets.ship

    override fun tick(delta: Float) {
        // accelerate
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            vel.add(Vector2(ACCEL * delta, 0f).rotateRad(angle))
        }
        // turn
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            angle += TURN_SPEED * delta
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            angle -= TURN_SPEED * delta
        }

        pos.add(vel.cpy().scl(delta))
    }

}
