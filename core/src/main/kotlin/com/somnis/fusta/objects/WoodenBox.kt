package com.somnis.fusta.objects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

/**
 * A wooden box that can be pushed around - basic puzzle mechanic
 */
class WoodenBox(
    position: Vector2,
    size: Vector2 = Vector2(40f, 40f)
) : InteractiveObject(position, size, "Wooden Box") {
    
    private val shapeRenderer = ShapeRenderer()
    var isPushed = false
    
    override fun render(batch: SpriteBatch) {
        batch.end()
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(Color.BROWN)
        shapeRenderer.rect(position.x, position.y, size.x, size.y)
        
        // Draw box outline
        shapeRenderer.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(Color.DARK_GRAY)
        shapeRenderer.rect(position.x, position.y, size.x, size.y)
        shapeRenderer.end()
        
        batch.begin()
    }
    
    override fun onInteract(): InteractionResult {
        isPushed = true
        return InteractionResult.None
    }
    
    /**
     * Move the box
     */
    fun move(direction: Vector2, distance: Float) {
        position.add(direction.x * distance, direction.y * distance)
        bounds.setPosition(position.x, position.y)
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}
