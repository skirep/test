package com.somnis.fusta.levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.somnis.fusta.SomnisDeFustaGame
import com.somnis.fusta.characters.Nil
import com.somnis.fusta.characters.Teca
import com.somnis.fusta.dialogue.Dialogue
import com.somnis.fusta.dialogue.DialogueSystem
import com.somnis.fusta.inventory.DreamFragment
import com.somnis.fusta.inventory.Inventory
import com.somnis.fusta.objects.DreamFragmentObject
import com.somnis.fusta.objects.InteractionResult
import com.somnis.fusta.objects.InteractiveObject
import com.somnis.fusta.objects.WoodenBox

/**
 * First level: The Enchanted Forest
 * A mystical wooden forest where Nil begins his journey
 */
class EnchantedForestLevel(game: SomnisDeFustaGame) : Level(game) {
    override val name = "Bosc Encantat"
    override val description = "Un bosc màgic fet de fusta on comença el viatge de Nil"
    
    // Characters
    private lateinit var nil: Nil
    private lateinit var teca: Teca
    
    // Systems
    val dialogueSystem = DialogueSystem(game.font)
    private val inventory = Inventory()
    
    // Objects
    private val interactiveObjects = mutableListOf<InteractiveObject>()
    
    // Touch control state
    private var lastTouchPos = Vector2()
    private var isTouching = false
    
    override fun start() {
        Gdx.app.log("EnchantedForest", "Level starting")
        
        // Initialize characters
        nil = Nil(Vector2(100f, 200f))
        teca = Teca(Vector2(150f, 200f))
        
        // Add interactive objects
        val box = WoodenBox(Vector2(300f, 200f))
        interactiveObjects.add(box)
        
        // Add a dream fragment to collect
        val fragment = DreamFragment(
            "forest_fragment_1",
            "Fragment del Bosc",
            "Un fragment brillant del somni del bosc encantat",
            "Bosc Encantat"
        )
        val fragmentObject = DreamFragmentObject(Vector2(500f, 200f), fragment)
        interactiveObjects.add(fragmentObject)
        
        // Start introductory dialogue with Teca
        startIntroDialogue()
    }
    
    private fun startIntroDialogue() {
        val intro = listOf(
            Dialogue("Teca", "Hola Nil! Sóc Teca, la teva guia en aquest viatge."),
            Dialogue("Teca", "Has perdut la capacitat de somiar, però junts podem recuperar-la."),
            Dialogue("Nil", "Com ho farem?"),
            Dialogue("Teca", "Hem de trobar els fragments dels teus somnis perduts."),
            Dialogue("Teca", "Mira! Hi ha un fragment brillant allà. Toca la pantalla per moure't!")
        )
        dialogueSystem.startDialogue(intro)
    }
    
    override fun update(delta: Float) {
        // Handle touch input for movement (only when not in dialogue)
        if (!dialogueSystem.isActive()) {
            handleTouchInput()
        }
        
        // Update characters
        nil.update(delta)
        teca.update(delta)
        
        // Update objects
        interactiveObjects.forEach { it.update(delta) }
        
        // Check for interactions with objects
        checkInteractions()
    }
    
    private fun handleTouchInput() {
        if (Gdx.input.isTouched) {
            val touchX = Gdx.input.x.toFloat()
            val touchY = Gdx.graphics.height - Gdx.input.y.toFloat() // Flip Y coordinate
            
            if (!isTouching) {
                lastTouchPos.set(touchX, touchY)
                isTouching = true
            }
            
            // Calculate direction from Nil to touch position
            val direction = Vector2(touchX - nil.position.x, touchY - nil.position.y)
            if (direction.len() > 10f) { // Dead zone
                direction.nor()
                nil.move(direction)
            } else {
                nil.stop()
            }
        } else {
            nil.stop()
            isTouching = false
        }
    }
    
    private fun checkInteractions() {
        val nilBounds = Rectangle(nil.position.x, nil.position.y, 32f, 32f)
        
        interactiveObjects.forEach { obj ->
            if (obj.isInteractable && obj.overlaps(nilBounds)) {
                val result = obj.onInteract()
                handleInteractionResult(result)
            }
        }
    }
    
    private fun handleInteractionResult(result: InteractionResult) {
        when (result) {
            is InteractionResult.Dialogue -> {
                dialogueSystem.startDialogue(result.dialogues)
            }
            is InteractionResult.CollectFragment -> {
                inventory.addDreamFragment(result.fragment)
                val collectDialogue = listOf(
                    Dialogue("Teca", "Molt bé! Has trobat un fragment del somni!"),
                    Dialogue("Teca", "Cada fragment et tornarà part de la teva imaginació.")
                )
                dialogueSystem.startDialogue(collectDialogue)
            }
            is InteractionResult.CollectItem -> {
                inventory.addItem(result.itemName)
            }
            else -> {}
        }
    }
    
    override fun render(batch: SpriteBatch) {
        // Draw background (forest floor)
        batch.begin()
        batch.color = Color(0.3f, 0.5f, 0.2f, 1f) // Forest green
        batch.end()
        
        // Draw objects
        batch.begin()
        interactiveObjects.forEach { it.render(batch) }
        batch.end()
        
        // Draw characters
        batch.begin()
        nil.render(batch)
        teca.render(batch)
        batch.end()
        
        // Draw UI
        batch.begin()
        
        // Draw level name
        game.font.color = Color.WHITE
        game.font.draw(batch, name, 10f, Gdx.graphics.height - 10f)
        
        // Draw fragment count
        game.font.draw(
            batch,
            "Fragments: ${inventory.getDreamFragmentCount()}",
            10f,
            Gdx.graphics.height - 30f
        )
        
        // Draw dialogue system
        dialogueSystem.render(batch)
        
        batch.end()
    }
    
    override fun onComplete() {
        Gdx.app.log("EnchantedForest", "Level completed!")
    }
    
    override fun dispose() {
        nil.dispose()
        teca.dispose()
        dialogueSystem.dispose()
        interactiveObjects.forEach { it.dispose() }
    }
}
