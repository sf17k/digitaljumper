package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

object Assets {

    // because this class is a Kotlin singleton (i.e. uses the 'object' keyword),
    // fields might only be initialized the first time the class is used.
    // (not sure about this, may need to initialize in constructor?)

    val asteroid = loadImg("asteroid")
    val ship = loadImg("ship")
    val bullet = loadImg("bullet")

    private fun loadImg(name: String): Sprite = Sprite(Texture(Gdx.files.internal("../data/img/" + name + ".png")))

    fun load() {
        // ...
    }

}