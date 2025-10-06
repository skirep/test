package com.somnis.fusta

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Disposable

/**
 * Manages game assets (textures, sounds, fonts)
 * Provides centralized asset loading and disposal
 */
class GameAssetManager : Disposable {
    private val textures = mutableMapOf<String, Texture>()
    
    /**
     * Load a texture from assets
     */
    fun loadTexture(key: String, path: String) {
        try {
            textures[key] = Texture(Gdx.files.internal(path))
            Gdx.app.log("AssetManager", "Loaded texture: $key")
        } catch (e: Exception) {
            Gdx.app.error("AssetManager", "Failed to load texture: $key", e)
        }
    }
    
    /**
     * Get a loaded texture
     */
    fun getTexture(key: String): Texture? {
        return textures[key]
    }
    
    override fun dispose() {
        textures.values.forEach { it.dispose() }
        textures.clear()
    }
}
