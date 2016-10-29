package com.drakelinglabs.digitaljumper.client.screen

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.drakelinglabs.digitaljumper.client.system.*
import net.mostlyoriginal.api.event.common.EventSystem

class PlayScreen : WorldScreen() {

    private val renderSys = RenderSys()

    override fun createWorld(): World {
        return World(WorldConfigurationBuilder().with(
                // supporting
                EventSystem(),
                AssetsSys(),
                // movement
                PhysicsSys(),
                ShipControlsSys(),
                //new DirectionalControlsSys(),
                CollisionSys(),
                // logic
                LifetimeSys(),
                DamageSys(),
                MultiplyingSys(),
                ShootingSys(),
                GameSys(),
                // rendering
                renderSys
        ).build())
    }

    override fun resize(width: Int, height: Int) {
        renderSys.resize(width, height)
    }

}
