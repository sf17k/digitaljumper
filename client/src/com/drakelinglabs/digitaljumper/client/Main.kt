package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.drakelinglabs.digitaljumper.client.system.GameSys

object Main {

    @JvmStatic fun main(args: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()
        config.setTitle("Digital Jumper")
        config.setWindowedMode(GameSys.WORLD_W, GameSys.WORLD_H)
        config.setResizable(false)
        Lwjgl3Application(GameApp, config)
    }

}
