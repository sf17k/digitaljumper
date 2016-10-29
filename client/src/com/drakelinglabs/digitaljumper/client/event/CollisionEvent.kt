package com.drakelinglabs.digitaljumper.client.event

import net.mostlyoriginal.api.event.common.Event

// a and b are entities
class CollisionEvent(val a: Int, val b: Int) : Event {}
