package com.drakelinglabs.digitaljumper.client.system

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.utils.IntBag
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.drakelinglabs.digitaljumper.client.component.Angle
import com.drakelinglabs.digitaljumper.client.component.Position
import com.drakelinglabs.digitaljumper.client.component.SpriteRender

class RenderSys : BaseEntitySystem(Aspect.all(
        SpriteRender::class.java,
        Position::class.java
)) {

    lateinit var assets: AssetsSys
    lateinit var mSpriteRender: ComponentMapper<SpriteRender>
    lateinit var mPosition: ComponentMapper<Position>
    lateinit var mAngle: ComponentMapper<Angle>

    override fun processSystem() {
        preRender()
        val actives: IntBag = subscription.entities
        val ids: IntArray = actives.data
        for (i in 0..actives.size() - 1) {
            renderEntity(ids[i])
        }
        postRender()
    }


    val batch = SpriteBatch()
    val camera = OrthographicCamera()
    val viewport = ScreenViewport(camera)

    fun preRender() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.position.set(GameSys.WORLD_W / 2f, GameSys.WORLD_H / 2f, 0f)
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
    }

    fun postRender() {
        batch.end()
    }

    fun renderEntity(e: Int) {
        val info = mSpriteRender.get(e)
        val pos = mPosition.get(e)
        val angle = mAngle.get(e) // optional

        val sprite = assets.sprite(info.sprite)
        val spriteAngle: Float = if (angle != null) angle.a else 0f

        if (sprite != null) {
            sprite.setScale(info.scale)
            drawSpriteCentered(sprite, pos.p, spriteAngle)
        }
    }

    fun drawSpriteCentered(s: Sprite, pos: Vector2, angle: Float) {
        val w = s.width.toFloat()
        val h = s.height.toFloat()
        val angleDeg = angle * MathUtils.radiansToDegrees
        batch.draw(s, pos.x - w / 2, pos.y - w / 2, w / 2, h / 2, w, h, s.scaleX, s.scaleY, angleDeg)
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

}
