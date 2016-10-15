package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.Assets

class Ship() : Go() {

    val TURN_SPEED = 3.8f
    val ACCEL = 500f
    val BULLET_SPEED = 400f
    val BULLET_LIFE = 0.8f
    val SHOOT_INTERVAL = 0.22f // sec

    var shootTimer = SHOOT_INTERVAL

    override fun getSprite() = Assets.ship
    override fun getRadius() = 16f

    override fun tick(delta: Float) {
        // timers
        shootTimer -= delta

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

        // shoot
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && shootTimer < 0) {
            shootTimer = SHOOT_INTERVAL
            val bvel = vel.cpy().add(Vector2(BULLET_SPEED, 0f).rotateRad(angle))
            val b = Bullet(BULLET_LIFE)
            b.pos = pos.cpy()
            b.vel = bvel
            b.angle = angle
        }
    }

}
