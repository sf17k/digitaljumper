package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.drakelinglabs.digitaljumper.client.go.Asteroid
import com.drakelinglabs.digitaljumper.client.go.Bullet
import com.drakelinglabs.digitaljumper.client.go.Go
import com.drakelinglabs.digitaljumper.client.go.Ship
import java.util.*

object GameWorld {

    val gos = Array<Go>()
    val rng = Random()

    fun restart() {
        gos.clear()

        val ship = Ship()

        val WORLD_SIZE = 300f
        val ASTEROID_SPEED = 30f
        for (i in 0..5) {
            val a = Asteroid(3)
            a.angle = MathUtils.PI2 * rng.nextFloat()
            a.pos.set(
                    WORLD_SIZE * (rng.nextFloat() - 0.5f),
                    WORLD_SIZE * (rng.nextFloat() - 0.5f))
            a.vel.set(ASTEROID_SPEED, 0f).rotateRad(MathUtils.PI2 * rng.nextFloat())
        }

    }

    fun tick(delta: Float) {
        gos.map { it.tick(delta) }
    }

}
