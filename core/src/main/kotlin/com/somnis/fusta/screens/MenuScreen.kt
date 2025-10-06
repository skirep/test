package com.somnis.fusta.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Rectangle
import com.somnis.fusta.SomnisDeFustaGame

/**
 * Main menu screen
 */
class MenuScreen(private val game: SomnisDeFustaGame) : Screen {
    private val startButton = Rectangle(
        Gdx.graphics.width / 2f - 100f,
        Gdx.graphics.height / 2f - 25f,
        200f,
        50f
    )
    
    override fun show() {
        Gdx.app.log("MenuScreen", "Menu screen shown")
    }
    
    override fun render(delta: Float) {
        // Clear screen with warm wood color
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        
        game.batch.begin()
        
        // Draw title
        game.font.color = Color.GOLD
        game.font.data.setScale(2f)
        val titleText = "Somnis de Fusta"
        val titleLayout = game.font.draw(
            game.batch,
            titleText,
            Gdx.graphics.width / 2f - 150f,
            Gdx.graphics.height / 2f + 100f
        )
        
        // Draw subtitle
        game.font.data.setScale(1f)
        game.font.color = Color.WHITE
        game.font.draw(
            game.batch,
            "Tap to Start",
            Gdx.graphics.width / 2f - 60f,
            Gdx.graphics.height / 2f
        )
        
        game.batch.end()
        
        // Check for touch input
        if (Gdx.input.justTouched()) {
            game.screen = GameScreen(game)
            dispose()
        }
    }
    
    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun dispose() {}
}
