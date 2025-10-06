package com.somnis.fusta.inventory

import com.badlogic.gdx.Gdx

/**
 * Manages the player's inventory of dream fragments and items
 */
class Inventory {
    private val dreamFragments = mutableListOf<DreamFragment>()
    private val items = mutableMapOf<String, Int>() // item name to quantity
    
    /**
     * Add a dream fragment to the inventory
     */
    fun addDreamFragment(fragment: DreamFragment) {
        dreamFragments.add(fragment)
        Gdx.app.log("Inventory", "Collected dream fragment: ${fragment.name}")
    }
    
    /**
     * Add an item to the inventory
     */
    fun addItem(itemName: String, quantity: Int = 1) {
        items[itemName] = items.getOrDefault(itemName, 0) + quantity
        Gdx.app.log("Inventory", "Added $quantity x $itemName")
    }
    
    /**
     * Remove an item from the inventory
     */
    fun removeItem(itemName: String, quantity: Int = 1): Boolean {
        val current = items.getOrDefault(itemName, 0)
        if (current >= quantity) {
            items[itemName] = current - quantity
            if (items[itemName] == 0) {
                items.remove(itemName)
            }
            return true
        }
        return false
    }
    
    /**
     * Check if inventory has an item
     */
    fun hasItem(itemName: String, quantity: Int = 1): Boolean {
        return items.getOrDefault(itemName, 0) >= quantity
    }
    
    /**
     * Get all dream fragments
     */
    fun getDreamFragments(): List<DreamFragment> = dreamFragments.toList()
    
    /**
     * Get all items
     */
    fun getItems(): Map<String, Int> = items.toMap()
    
    /**
     * Get number of dream fragments collected
     */
    fun getDreamFragmentCount(): Int = dreamFragments.size
}
