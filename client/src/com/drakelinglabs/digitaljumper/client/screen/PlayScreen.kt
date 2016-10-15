package com.drakelinglabs.digitaljumper.client.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.drakelinglabs.digitaljumper.client.GameWorld

class PlayScreen : Screen {

    val batch = SpriteBatch()
    val camera = OrthographicCamera()
    val viewport = ScreenViewport(camera)

    fun restart() {
        // ...
    }

    override fun render(delta: Float) {
        GameWorld.tick(delta)

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.position.set(GameWorld.WORLD_W / 2f, GameWorld.WORLD_H / 2f, 0f)
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        GameWorld.gos.map {
            drawSpriteCentered(it.getSprite(), it.pos, it.angle)
        }
        batch.end()
    }

    fun drawSpriteCentered(s: Sprite, pos: Vector2, angle: Float) {
        val w = s.width.toFloat()
        val h = s.height.toFloat()
        val angleDeg = angle * MathUtils.radiansToDegrees
        batch.draw(s, pos.x - w / 2, pos.y - w / 2, w / 2, h / 2, w, h, s.scaleX, s.scaleY, angleDeg)
    }

    override fun resize(width: Int, height: Int) {
        //camera.setToOrtho(false, width.toFloat(), height.toFloat())
        viewport.update(width, height)
    }

    override fun show() {
    }

    override fun hide() {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun dispose() {
    }

}
