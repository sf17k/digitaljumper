package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

object Main {

    @JvmStatic fun main(args: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()
        config.setTitle("Digital Jumper")
        config.setWindowedMode(800, 480)
        Lwjgl3Application(GameApp, config)
    }

}
