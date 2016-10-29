package com.drakelinglabs.digitaljumper.client.component

import com.artemis.Component

class Shooting() : Component() {
    var cooldown: Float = 0f
    var interval: Float = 1f
    var bulletSpeed: Float = 1f
    var bulletLife: Float = 1f
}
