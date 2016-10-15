package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

object Main {

    @JvmStatic fun main(args: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()
        config.setTitle("Digital Jumper")
        config.setWindowedMode(GameWorld.WORLD_W, GameWorld.WORLD_H)
        config.setResizable(false)
        Lwjgl3Application(GameApp, config)
    }

}
