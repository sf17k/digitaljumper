package com.drakelinglabs.digitaljumper.client.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.GdxRuntimeException
import net.mostlyoriginal.api.system.core.PassiveSystem

class AssetsSys : PassiveSystem() {

    private val sprites = hashMapOf<String, Sprite>()

    private val missingSprite = loadSprite("missing")

    private fun loadSprite(name: String): Sprite {
        var sprite: Sprite = missingSprite
        try {
            val fh = Gdx.files.internal("../data/img/" + name + ".png")
            sprite = Sprite(Texture(fh))
        } catch(e: GdxRuntimeException) {
            // TODO: warning
        }
        return sprite
    }

    fun sprite(name: String): Sprite {
        return sprites.getOrPut(name, { loadSprite(name) })
    }

}
