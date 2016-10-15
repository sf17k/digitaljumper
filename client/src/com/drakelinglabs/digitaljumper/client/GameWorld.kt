package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.drakelinglabs.digitaljumper.client.go.Asteroid
import com.drakelinglabs.digitaljumper.client.go.Bullet
import com.drakelinglabs.digitaljumper.client.go.Go
import com.drakelinglabs.digitaljumper.client.go.Ship
import java.util.*

object GameWorld {

    val WORLD_W = 480
    val WORLD_H = 480
    val ASTEROID_SPEED = 30f

    var dead = false

    val gos = Array<Go>()
    val rng = Random()

    fun restart() {
        gos.clear()

        val ship = Ship()
        ship.pos.set(WORLD_W / 2f, WORLD_H / 2f)
        ship.angle = MathUtils.PI / 2f

        for (i in 0..5) {
            val a = Asteroid(3)
            a.angle = MathUtils.PI2 * rng.nextFloat()
            a.pos.set(
                    WORLD_W * 0.6f * (rng.nextFloat() - 0.5f),
                    WORLD_H * (rng.nextFloat() - 0.5f))
            a.vel.set(ASTEROID_SPEED, 0f).rotateRad(MathUtils.PI2 * rng.nextFloat())
        }

    }

    fun tick(delta: Float) {
        if (dead && Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            dead = false
            restart()
        }
        gos.map { it.tick(delta) }
        gos.map { wrap(it.pos) }
        checkCollisions(delta)
        gos.removeAll { it.isRemoved() }
    }

    fun wrap(v: Vector2) {
        if (v.x < 0)
            v.x += WORLD_W
        if (v.x >= WORLD_W)
            v.x -= WORLD_W
        if (v.y < 0)
            v.y += WORLD_H
        if (v.y >= WORLD_H)
            v.y -= WORLD_H
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
        dead = true
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
