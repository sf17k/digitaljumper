package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.Assets

class Bullet(var life: Float) : Go() {

    override fun getSprite() = Assets.bullet

    override fun tick(delta: Float) {
        life -= delta

        if (life < 0) {
            destroy()
        }

        pos.add(vel.cpy().scl(delta))
    }

}
