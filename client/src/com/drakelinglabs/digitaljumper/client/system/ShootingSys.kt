package com.drakelinglabs.digitaljumper.client.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.component.Angle
import com.drakelinglabs.digitaljumper.client.component.Position
import com.drakelinglabs.digitaljumper.client.component.Shooting
import com.drakelinglabs.digitaljumper.client.component.SimplePhysics

class ShootingSys : IteratingSystem(Aspect.all(
        Shooting::class.java,
        Position::class.java,
        Angle::class.java
)) {

    lateinit var game: GameSys
    lateinit var mShooting: ComponentMapper<Shooting>
    lateinit var mPosition: ComponentMapper<Position>
    lateinit var mAngle: ComponentMapper<Angle>
    lateinit var mSimplePhysics: ComponentMapper<SimplePhysics>

    override fun process(e: Int) {
        val shooting = mShooting.get(e)
        shooting.cooldown -= world.delta

        // shoot
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && shooting.cooldown < 0f) {
            shooting.cooldown += shooting.interval

            val p = mPosition.get(e).p
            val a = mAngle.get(e).a
            val phys = mSimplePhysics.get(e)
            val v = if (phys != null) phys.v.cpy() else Vector2()
            v.add(Vector2(shooting.bulletSpeed, 0f).rotateRad(a))

            game.createBullet(p, v, a, shooting.bulletLife)
        }

        if (shooting.cooldown < 0f)
            shooting.cooldown = 0f
    }

}
