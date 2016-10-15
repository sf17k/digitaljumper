package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.drakelinglabs.digitaljumper.client.go.Asteroid
import com.drakelinglabs.digitaljumper.client.go.Bullet
import com.drakelinglabs.digitaljumper.client.go.Go
import com.drakelinglabs.digitaljumper.client.go.Ship
import java.util.*

object GameWorld {

    val WORLD_SIZE = 300f
    val ASTEROID_SPEED = 30f

    val gos = Array<Go>()
    val rng = Random()

    fun restart() {
        gos.clear()

        val ship = Ship()

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
        checkCollisions(delta)
        gos.removeAll { it.isRemoved() }
    }

    fun checkCollisions(delta: Float) {
        for (i in 0..gos.size - 1) {
            for (j in i + 1..gos.size - 1) {
                val a = gos[i]
                val b = gos[j]
                val rad = a.getRadius() + b.getRadius()
                if (a.pos.dst2(b.pos) > rad * rad)
                    continue // no collision

                if (a is Ship && b is Asteroid)
                    hitShipAsteroid(a, b)
                else if (b is Ship && a is Asteroid)
                    hitShipAsteroid(b, a)
                else if (a is Bullet && b is Asteroid)
                    hitBulletAsteroid(a, b)
                else if (b is Bullet && a is Asteroid)
                    hitBulletAsteroid(b, a)
            }
        }
    }

    fun hitShipAsteroid(s: Ship, a: Asteroid) {
        s.remove()
        breakAsteroid(a)
    }

    fun hitBulletAsteroid(b: Bullet, a: Asteroid) {
        b.remove()
        breakAsteroid(a)
    }

    fun breakAsteroid(a: Asteroid) {
        if (a.size > 1) {
            val splitAngle = MathUtils.PI2 * rng.nextFloat()
            val splitVec = Vector2(ASTEROID_SPEED, 0f).rotateRad(splitAngle)

            val a1 = Asteroid(a.size - 1)
            a1.copyFrom(a)
            a1.vel.add(splitVec)

            val a2 = Asteroid(a.size - 1)
            a2.copyFrom(a)
            a2.vel.sub(splitVec)
        }
        a.remove()
    }

}
