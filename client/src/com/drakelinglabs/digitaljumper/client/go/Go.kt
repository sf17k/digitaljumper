package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.GameWorld

abstract class Go {

    var pos = Vector2()
    var vel = Vector2()
    var angle = 0.0f

    init {
        GameWorld.gos.add(this)
    }

    fun destroy() {
        GameWorld.gos.removeValue(this, true)
    }

    abstract fun getSprite(): Sprite
    abstract fun tick(delta: Float)

}
