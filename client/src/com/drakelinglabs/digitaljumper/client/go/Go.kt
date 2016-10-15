package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.GameWorld

abstract class Go {

    var pos = Vector2()
    var vel = Vector2()
    var angle = 0.0f

    private var toRemove = false

    init {
        GameWorld.gos.add(this)
    }

    fun copyFrom(go: Go) {
        pos.set(go.pos)
        vel.set(go.vel)
        angle = go.angle
    }

    fun remove() {
        toRemove = true
    }

    fun isRemoved() = toRemove

    abstract fun getSprite(): Sprite
    abstract fun getRadius(): Float
    abstract fun tick(delta: Float)

}
