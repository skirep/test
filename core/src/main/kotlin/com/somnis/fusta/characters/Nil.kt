package com.somnis.fusta.characters

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

/**
 * Nil - The protagonist
 * A boy who has lost the ability to dream and must recover his dream fragments
 */
class Nil(position: Vector2) : Character(position, "Nil") {
    private val shapeRenderer = ShapeRenderer()
    private val size = 32f
    
    override fun render(batch: SpriteBatch) {
        // End the batch to use ShapeRenderer
        batch.end()
        
        // Draw Nil as a simple shape (placeholder)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(Color.BROWN) // Wood color theme
        shapeRenderer.rect(position.x, position.y, size, size)
        shapeRenderer.end()
        
        // Resume batch
        batch.begin()
    }
    
    /**
     * Move in a direction
     */
    fun move(direction: Vector2) {
        velocity.set(direction).scl(speed)
    }
    
    /**
     * Stop movement
     */
    fun stop() {
        velocity.set(0f, 0f)
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}
