package com.drakelinglabs.digitaljumper.client.component

import com.artemis.Component

// breaks into smaller pieces until minimum size of 1
class Multiplying() : Component() {
    var size: Int = 3
    var numChildren: Int = 2
    var speed: Float = 10f // speed of separating children
}
