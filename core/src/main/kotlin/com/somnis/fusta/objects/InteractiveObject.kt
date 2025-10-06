package com.somnis.fusta.objects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * Base class for interactive objects in the game world
 */
abstract class InteractiveObject(
    val position: Vector2,
    val size: Vector2,
    val name: String
) {
    var isInteractable = true
    protected val bounds = Rectangle(position.x, position.y, size.x, size.y)
    
    /**
     * Update object state
     */
    open fun update(delta: Float) {}
    
    /**
     * Render the object
     */
    abstract fun render(batch: SpriteBatch)
    
    /**
     * Called when the player interacts with this object
     */
    abstract fun onInteract(): InteractionResult
    
    /**
     * Check if a point is within the object's bounds
     */
    fun contains(x: Float, y: Float): Boolean {
        return bounds.contains(x, y)
    }
    
    /**
     * Check if this object overlaps with another rectangle
     */
    fun overlaps(other: Rectangle): Boolean {
        return bounds.overlaps(other)
    }
    
    open fun dispose() {}
}

/**
 * Result of an interaction
 */
sealed class InteractionResult {
    object None : InteractionResult()
    data class Dialogue(val dialogues: List<com.somnis.fusta.dialogue.Dialogue>) : InteractionResult()
    data class CollectItem(val itemName: String) : InteractionResult()
    data class CollectFragment(val fragment: com.somnis.fusta.inventory.DreamFragment) : InteractionResult()
    data class Puzzle(val puzzleId: String) : InteractionResult()
}
