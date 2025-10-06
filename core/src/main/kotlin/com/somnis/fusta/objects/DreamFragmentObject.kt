package com.somnis.fusta.objects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.somnis.fusta.inventory.DreamFragment

/**
 * A collectible dream fragment in the game world
 */
class DreamFragmentObject(
    position: Vector2,
    private val fragment: DreamFragment
) : InteractiveObject(position, Vector2(24f, 24f), fragment.name) {
    
    private val shapeRenderer = ShapeRenderer()
    private var collected = false
    private var animationTime = 0f
    
    override fun update(delta: Float) {
        super.update(delta)
        animationTime += delta
    }
    
    override fun render(batch: SpriteBatch) {
        if (collected) return
        
        batch.end()
        
        // Floating animation
        val offset = Math.sin(animationTime.toDouble() * 2.0).toFloat() * 5f
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(Color.GOLD)
        shapeRenderer.circle(
            position.x + size.x / 2,
            position.y + size.y / 2 + offset,
            size.x / 2
        )
        shapeRenderer.end()
        
        // Glow effect
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1f, 1f, 0.5f, 0.5f)
        shapeRenderer.circle(
            position.x + size.x / 2,
            position.y + size.y / 2 + offset,
            size.x / 2 + 3f
        )
        shapeRenderer.end()
        
        batch.begin()
    }
    
    override fun onInteract(): InteractionResult {
        collected = true
        isInteractable = false
        return InteractionResult.CollectFragment(fragment)
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}
