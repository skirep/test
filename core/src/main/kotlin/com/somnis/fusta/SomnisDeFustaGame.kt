package com.somnis.fusta

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.somnis.fusta.screens.MenuScreen

/**
 * Main game class for Somnis de Fusta
 * A narrative adventure game about a boy named Nil who lost the ability to dream
 */
class SomnisDeFustaGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont
    lateinit var assetManager: GameAssetManager
    
    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont() // Default font
        assetManager = GameAssetManager()
        
        Gdx.app.log("SomnisDeFusta", "Game created successfully")
        setScreen(MenuScreen(this))
    }
    
    override fun dispose() {
        batch.dispose()
        font.dispose()
        assetManager.dispose()
    }
}
