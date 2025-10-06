package com.somnis.fusta.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.somnis.fusta.SomnisDeFustaGame
import com.somnis.fusta.GameState
import com.somnis.fusta.levels.EnchantedForestLevel

/**
 * Main game screen where gameplay happens
 */
class GameScreen(private val game: SomnisDeFustaGame) : Screen {
    private var currentLevel: EnchantedForestLevel? = null
    private var gameState = GameState.PLAYING
    
    override fun show() {
        Gdx.app.log("GameScreen", "Starting game")
        currentLevel = EnchantedForestLevel(game)
        currentLevel?.start()
    }
    
    override fun render(delta: Float) {
        // Clear screen with soft sky blue
        Gdx.gl.glClearColor(0.6f, 0.8f, 0.9f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        
        // Update and render current level
        currentLevel?.let { level ->
            level.update(delta)
            
            game.batch.begin()
            level.render(game.batch)
            game.batch.end()
            
            // Handle input for dialogue
            if (level.dialogueSystem.isActive() && Gdx.input.justTouched()) {
                level.dialogueSystem.next()
            }
        }
    }
    
    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    
    override fun dispose() {
        currentLevel?.dispose()
    }
}
