package com.drakelinglabs.digitaljumper.client

import com.badlogic.gdx.Game
import com.drakelinglabs.digitaljumper.client.screen.PlayScreen

object GameApp : Game() {

    override fun create() {
        restart()
    }

    fun restart() {
        GameWorld.restart()

        val s = PlayScreen()
        s.restart()
        setScreen(s)
    }

}
