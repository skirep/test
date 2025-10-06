package com.somnis.fusta.characters

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

/**
 * Base class for all characters in the game
 */
abstract class Character(
    var position: Vector2,
    val name: String
) {
    var velocity = Vector2()
    var speed = 100f // pixels per second
    
    /**
     * Update character logic
     */
    open fun update(delta: Float) {
        position.add(velocity.x * delta, velocity.y * delta)
    }
    
    /**
     * Render the character
     */
    abstract fun render(batch: SpriteBatch)
    
    /**
     * Dispose of resources
     */
    open fun dispose() {}
}
