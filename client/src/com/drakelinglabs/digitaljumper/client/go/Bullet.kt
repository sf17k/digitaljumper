package com.drakelinglabs.digitaljumper.client.go

import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.Assets

class Bullet() : Go() {

    override fun getSprite() = Assets.bullet

    override fun tick(delta: Float) {
        pos.add(vel.cpy().scl(delta))
    }

}
