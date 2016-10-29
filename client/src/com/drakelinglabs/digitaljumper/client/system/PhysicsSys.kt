package com.drakelinglabs.digitaljumper.client.system

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.drakelinglabs.digitaljumper.client.component.Angle
import com.drakelinglabs.digitaljumper.client.component.Position
import com.drakelinglabs.digitaljumper.client.component.SimplePhysics

class PhysicsSys : IteratingSystem(Aspect.all(
        Position::class.java,
        SimplePhysics::class.java
)) {

    lateinit var mPosition: ComponentMapper<Position>
    lateinit var mSimplePhysics: ComponentMapper<SimplePhysics>
    lateinit var mAngle: ComponentMapper<Angle>

    override fun process(e: Int) {
        val phys = mSimplePhysics.get(e)
        var pos = mPosition.get(e).p
        pos.add(phys.v.cpy().scl(world.delta))
        wrap(pos)
        val angle = mAngle.get(e) // optional
        if (angle != null)
            angle.a += phys.vrot * world.delta
    }

    fun wrap(v: Vector2) {
        if (v.x < 0)
            v.x += GameSys.WORLD_W
        if (v.x >= GameSys.WORLD_W)
            v.x -= GameSys.WORLD_W
        if (v.y < 0)
            v.y += GameSys.WORLD_H
        if (v.y >= GameSys.WORLD_H)
            v.y -= GameSys.WORLD_H
    }

}
