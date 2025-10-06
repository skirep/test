package com.somnis.fusta.dialogue

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * System for displaying and managing dialogues between characters
 */
class DialogueSystem(private val font: BitmapFont) {
    private val dialogues = mutableListOf<Dialogue>()
    private var currentIndex = 0
    private var isActive = false
    private val shapeRenderer = ShapeRenderer()
    
    /**
     * Start a dialogue sequence
     */
    fun startDialogue(dialogueList: List<Dialogue>) {
        dialogues.clear()
        dialogues.addAll(dialogueList)
        currentIndex = 0
        isActive = dialogues.isNotEmpty()
    }
    
    /**
     * Advance to the next dialogue
     */
    fun next() {
        if (isActive) {
            currentIndex++
            if (currentIndex >= dialogues.size) {
                isActive = false
                currentIndex = 0
            }
        }
    }
    
    /**
     * Check if dialogue is currently active
     */
    fun isActive(): Boolean = isActive
    
    /**
     * Get the current dialogue
     */
    fun getCurrentDialogue(): Dialogue? {
        return if (isActive && currentIndex < dialogues.size) {
            dialogues[currentIndex]
        } else null
    }
    
    /**
     * Render the dialogue box
     */
    fun render(batch: SpriteBatch) {
        if (!isActive) return
        
        val currentDialogue = getCurrentDialogue() ?: return
        
        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()
        val boxHeight = 120f
        val boxY = 20f
        
        // Draw dialogue box background
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(0.2f, 0.15f, 0.1f, 0.9f) // Dark wood color
        shapeRenderer.rect(10f, boxY, screenWidth - 20f, boxHeight)
        shapeRenderer.end()
        batch.begin()
        
        // Draw speaker name
        font.color = Color.GOLD
        font.draw(batch, currentDialogue.speaker, 20f, boxY + boxHeight - 10f)
        
        // Draw dialogue text
        font.color = Color.WHITE
        font.draw(batch, currentDialogue.text, 20f, boxY + boxHeight - 40f, screenWidth - 40f, 1, true)
        
        // Draw "Tap to continue" hint
        font.color = Color.LIGHT_GRAY
        font.draw(batch, "Tap to continue...", screenWidth - 150f, boxY + 15f)
    }
    
    fun dispose() {
        shapeRenderer.dispose()
    }
}
