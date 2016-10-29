package com.drakelinglabs.digitaljumper.client.screen

import com.artemis.World
import com.badlogic.gdx.Screen
import com.badlogic.gdx.math.MathUtils

abstract class WorldScreen : Screen {

    companion object {
        val MAX_DELTA = 1 / 60f
    }

    private var world: World? = null

    protected abstract fun createWorld(): World

    override fun show() {
        if (world == null) {
            world = createWorld()
        }
    }

    override fun render(delta: Float) {
        if (world == null) {
            throw RuntimeException("World not initialized.")
        }
        // limit max delta
        world!!.setDelta(MathUtils.clamp(delta, 0f, MAX_DELTA))
        world!!.process()
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
    }

}
