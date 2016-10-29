package com.drakelinglabs.digitaljumper.client.system

import com.artemis.*
import com.artemis.utils.IntBag
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.component.*
import net.mostlyoriginal.api.event.common.EventSystem
import java.util.*

class GameSys : BaseEntitySystem(Aspect.all(
        RuleKeepOneAlive::class.java
)) {

    companion object {
        val WORLD_W = 720
        val WORLD_H = 480
        val ASTEROID_INITIAL_SPEED = 30f
        val ASTEROID_INITIAL_VROT = 1.0f
    }

    // are we in a state of having lost?
    var isGameOver = false

    val rng = Random()


    lateinit var basicObjectArchetype: Archetype
    lateinit var shipArchetype: Archetype
    lateinit var asteroidArchetype: Archetype
    lateinit var bulletArchetype: Archetype


    lateinit var bus: EventSystem
    lateinit var mPosition: ComponentMapper<Position>
    lateinit var mAngle: ComponentMapper<Angle>
    lateinit var mSimplePhysics: ComponentMapper<SimplePhysics>
    lateinit var mCollision: ComponentMapper<Collision>
    lateinit var mShipControls: ComponentMapper<ShipControls>
    lateinit var mShooting: ComponentMapper<Shooting>
    lateinit var mDamage: ComponentMapper<Damage>
    lateinit var mMultiplying: ComponentMapper<Multiplying>
    lateinit var mLifetime: ComponentMapper<Lifetime>
    lateinit var mSpriteRender: ComponentMapper<SpriteRender>
    lateinit var mRuleKeepOneAlive: ComponentMapper<RuleKeepOneAlive>

    override fun initialize() {
        basicObjectArchetype = ArchetypeBuilder()
                .add(Position::class.java)
                .add(Angle::class.java)
                .add(SimplePhysics::class.java)
                .add(Collision::class.java)
                .add(Damage::class.java)
                .add(SpriteRender::class.java)
                .build(world)

        shipArchetype = ArchetypeBuilder(basicObjectArchetype)
                .add(ShipControls::class.java)
                .add(Shooting::class.java)
                .add(RuleKeepOneAlive::class.java)
                .build(world)

        asteroidArchetype = ArchetypeBuilder(basicObjectArchetype)
                .add(Multiplying::class.java)
                .build(world)

        bulletArchetype = ArchetypeBuilder(basicObjectArchetype)
                .add(Lifetime::class.java)
                .build(world)

        restartGame()
    }

    fun deleteAllEntities() {
        val sub = world.aspectSubscriptionManager.get(Aspect.all())
        val entities: IntBag = sub.entities
        val ids: IntArray = entities.data
        for (i in 0..entities.size() - 1) {
            world.delete(ids[i])
        }
    }

    fun restartGame() {
        deleteAllEntities()

        isGameOver = false

        val ship = createEntity(shipArchetype,
                Vector2(WORLD_W / 2f, WORLD_H / 2f),
                Vector2(0f, 0f),
                MathUtils.PI / 2f, 0f,
                16f, "ship")
        mDamage.get(ship).dealsBitmask = 0x00
        mDamage.get(ship).takesBitmask = 0x01
        mShipControls.get(ship).accel = 500f
        mShipControls.get(ship).turnSpeed = 3.8f
        val shooting = mShooting.get(ship)
        shooting.bulletSpeed = 400f
        shooting.bulletLife = 0.8f
        shooting.interval = 0.22f
        shooting.cooldown = shooting.interval

        for (i in 0..5) {
            createAsteroid(
                    Vector2(WORLD_W * 0.6f * (rng.nextFloat() - 0.5f),
                            WORLD_H * (rng.nextFloat() - 0.5f)),
                    Vector2(ASTEROID_INITIAL_SPEED, 0f).rotateRad(MathUtils.PI2 * rng.nextFloat()),
                    MathUtils.PI2 * rng.nextFloat(), ASTEROID_INITIAL_VROT,
                    3)
        }
    }

    fun createBullet(pos: Vector2, vel: Vector2, angle: Float, lifetime: Float): Int {
        val e = createEntity(bulletArchetype, pos, vel, angle, 0f, 8f, "bullet")
        mDamage.get(e).takesBitmask = 0x01
        mDamage.get(e).dealsBitmask = 0x02
        mLifetime.get(e).t = lifetime
        return e
    }

    fun createAsteroid(pos: Vector2, vel: Vector2, angle: Float, vrot: Float, size: Int): Int {
        val e = createEntity(asteroidArchetype, pos, vel, angle, vrot, size * 16f / 3f, "asteroid")
        mDamage.get(e).takesBitmask = 0x02
        mDamage.get(e).dealsBitmask = 0x01
        mMultiplying.get(e).size = size
        mMultiplying.get(e).numChildren = 2
        mSpriteRender.get(e).scale = size / 3f
        return e
    }

    fun createEntity(archetype: Archetype,
                     pos: Vector2, vel: Vector2,
                     angle: Float, vrot: Float,
                     radius: Float,
                     sprite: String): Int {
        val e = world.create(archetype)
        mPosition.get(e).p.set(pos)
        mAngle.get(e).a = angle
        val sp = mSimplePhysics.get(e)
        sp.v.set(vel)
        sp.vrot = vrot
        mCollision.get(e).radius = radius
        mSpriteRender.get(e).sprite = sprite
        return e
    }

    override fun processSystem() {
        // do gamemode stuff
        if (isGameOver)
            processGameOver()
        else
            processPlaying()
    }

    fun processPlaying() {
        // check if game has ended
        isGameOver = true

        val actives: IntBag = subscription.entities
        val ids: IntArray = actives.data
        for (i in 0..actives.size() - 1) {
            if (mRuleKeepOneAlive.has(ids[i]))
                isGameOver = false;
        }

        if (isGameOver)
            onGameOver()
    }

    fun onGameOver() {
        //bus.dispatch(GameOverEvent())
    }

    fun processGameOver() {
        // allow player to restart the game
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            restartGame()
        }
    }

}
