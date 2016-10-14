package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.Assets

class Asteroid(var size: Int) : Go() {

    val ROT_SPEED = 1.0f

    override fun getSprite(): Sprite {
        val s = Assets.asteroid
        s.setScale(size.toFloat() / 3f)
        return s
    }

    override fun tick(delta: Float) {
        pos.add(vel.cpy().scl(delta))
        angle += ROT_SPEED * delta
    }

}
