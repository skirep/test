package com.somnis.fusta.characters

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

/**
 * Teca - The magical puppet guide
 * A wooden marionette that guides Nil through the surreal worlds
 */
class Teca(position: Vector2) : Character(position, "Teca") {
    private val shapeRenderer = ShapeRenderer()
    private val size = 28f
    
    override fun render(batch: SpriteBatch) {
        // End the batch to use ShapeRenderer
        batch.end()
        
        // Draw Teca as a simple shape (placeholder)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(Color.TAN) // Lighter wood color
        shapeRenderer.circle(position.x + size/2, position.y + size/2, size/2)
        shapeRenderer.end()
        
        // Resume batch
        batch.begin()
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}
