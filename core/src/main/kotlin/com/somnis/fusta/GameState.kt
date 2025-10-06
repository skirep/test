package com.somnis.fusta

/**
 * Represents the different states of the game
 */
enum class GameState {
    MENU,      // Main menu
    PLAYING,   // Active gameplay
    PAUSED,    // Game paused
    DIALOGUE,  // In dialogue interaction
    INVENTORY, // Viewing inventory
    END        // Game ending
}
