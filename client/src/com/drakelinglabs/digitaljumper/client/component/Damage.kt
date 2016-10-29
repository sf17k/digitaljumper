package com.drakelinglabs.digitaljumper.client.component

import com.artemis.Component

// deals and/or takes damage
class Damage() : Component() {
    var dealsBitmask: Int = 0
    var takesBitmask: Int = 0
}
