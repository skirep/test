package com.somnis.fusta.levels

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.somnis.fusta.SomnisDeFustaGame

/**
 * Base class for all game levels
 */
abstract class Level(protected val game: SomnisDeFustaGame) {
    abstract val name: String
    abstract val description: String
    
    /**
     * Called when the level starts
     */
    abstract fun start()
    
    /**
     * Update level logic
     */
    abstract fun update(delta: Float)
    
    /**
     * Render the level
     */
    abstract fun render(batch: SpriteBatch)
    
    /**
     * Called when the level is completed
     */
    abstract fun onComplete()
    
    /**
     * Clean up resources
     */
    abstract fun dispose()
}
