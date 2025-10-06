# Exemples de Codi - Somnis de Fusta

Aquest document conté exemples pràctics per ajudar-te a ampliar el joc.

## Exemple 1: Crear un Nou Nivell (Vaixell Pirata)

```kotlin
package com.somnis.fusta.levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.somnis.fusta.SomnisDeFustaGame
import com.somnis.fusta.characters.Nil
import com.somnis.fusta.characters.Teca
import com.somnis.fusta.dialogue.Dialogue
import com.somnis.fusta.dialogue.DialogueSystem
import com.somnis.fusta.inventory.DreamFragment
import com.somnis.fusta.inventory.Inventory
import com.somnis.fusta.objects.DreamFragmentObject
import com.somnis.fusta.objects.InteractiveObject

class PirateShipLevel(game: SomnisDeFustaGame) : Level(game) {
    override val name = "Vaixell Pirata"
    override val description = "Un vaixell de fusta navegant per mars de somnis"
    
    private lateinit var nil: Nil
    private lateinit var teca: Teca
    val dialogueSystem = DialogueSystem(game.font)
    private val inventory = Inventory()
    private val interactiveObjects = mutableListOf<InteractiveObject>()
    
    override fun start() {
        Gdx.app.log("PirateShip", "Level starting")
        
        // Inicialitza personatges
        nil = Nil(Vector2(150f, 250f))
        teca = Teca(Vector2(200f, 250f))
        
        // Afegeix fragments
        val fragment = DreamFragment(
            "ship_fragment_1",
            "Timó del Vaixell",
            "El timó de fusta d'un vaixell pirata dels somnis",
            "Vaixell Pirata"
        )
        interactiveObjects.add(DreamFragmentObject(Vector2(600f, 250f), fragment))
        
        // Diàleg inicial
        val intro = listOf(
            Dialogue("Teca", "Benvingut al Vaixell Pirata, Nil!"),
            Dialogue("Teca", "Aquest era un dels teus somnis d'aventures.")
        )
        dialogueSystem.startDialogue(intro)
    }
    
    override fun update(delta: Float) {
        if (!dialogueSystem.isActive()) {
            handleInput()
        }
        
        nil.update(delta)
        teca.update(delta)
        interactiveObjects.forEach { it.update(delta) }
        checkInteractions()
    }
    
    private fun handleInput() {
        if (Gdx.input.isTouched) {
            val touchX = Gdx.input.x.toFloat()
            val touchY = Gdx.graphics.height - Gdx.input.y.toFloat()
            val direction = Vector2(touchX - nil.position.x, touchY - nil.position.y)
            if (direction.len() > 10f) {
                direction.nor()
                nil.move(direction)
            } else {
                nil.stop()
            }
        } else {
            nil.stop()
        }
    }
    
    private fun checkInteractions() {
        // Implementa detecció de col·lisions
    }
    
    override fun render(batch: SpriteBatch) {
        batch.begin()
        
        // Fons (mar)
        batch.color = Color(0.2f, 0.3f, 0.5f, 1f)
        
        // Renderitza objectes i personatges
        interactiveObjects.forEach { it.render(batch) }
        nil.render(batch)
        teca.render(batch)
        
        // UI
        game.font.color = Color.WHITE
        game.font.draw(batch, name, 10f, Gdx.graphics.height - 10f)
        dialogueSystem.render(batch)
        
        batch.end()
    }
    
    override fun onComplete() {
        Gdx.app.log("PirateShip", "Level completed!")
    }
    
    override fun dispose() {
        nil.dispose()
        teca.dispose()
        dialogueSystem.dispose()
        interactiveObjects.forEach { it.dispose() }
    }
}
```

## Exemple 2: Crear un Puzle de Clau i Porta

```kotlin
package com.somnis.fusta.objects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.somnis.fusta.dialogue.Dialogue

// Porta tancada
class LockedDoor(position: Vector2) : InteractiveObject(
    position, 
    Vector2(50f, 80f), 
    "Porta Tancada"
) {
    private val shapeRenderer = ShapeRenderer()
    var isLocked = true
    
    override fun render(batch: SpriteBatch) {
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        
        // Color segons estat
        if (isLocked) {
            shapeRenderer.setColor(Color.DARK_GRAY)
        } else {
            shapeRenderer.setColor(Color.BROWN)
        }
        
        shapeRenderer.rect(position.x, position.y, size.x, size.y)
        shapeRenderer.end()
        batch.begin()
    }
    
    override fun onInteract(): InteractionResult {
        return if (isLocked) {
            InteractionResult.Dialogue(listOf(
                Dialogue("Nil", "La porta està tancada. Necessito una clau.")
            ))
        } else {
            InteractionResult.None
        }
    }
    
    fun unlock() {
        isLocked = false
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}

// Clau
class Key(position: Vector2) : InteractiveObject(
    position,
    Vector2(20f, 20f),
    "Clau"
) {
    private val shapeRenderer = ShapeRenderer()
    private var collected = false
    
    override fun render(batch: SpriteBatch) {
        if (collected) return
        
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(Color.YELLOW)
        shapeRenderer.circle(position.x + size.x/2, position.y + size.y/2, size.x/2)
        shapeRenderer.end()
        batch.begin()
    }
    
    override fun onInteract(): InteractionResult {
        collected = true
        isInteractable = false
        return InteractionResult.CollectItem("Clau")
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}

// Ús en un nivell:
// val door = LockedDoor(Vector2(400f, 200f))
// val key = Key(Vector2(100f, 200f))
// interactiveObjects.add(door)
// interactiveObjects.add(key)
//
// Quan el jugador tingui la clau:
// if (inventory.hasItem("Clau")) {
//     door.unlock()
//     inventory.removeItem("Clau")
// }
```

## Exemple 3: Seqüència de Diàlegs amb Decisions

```kotlin
// En un nivell o objecte:
private fun startStoryDialogue() {
    val story = listOf(
        Dialogue("Teca", "Nil, hem de prendre una decisió important."),
        Dialogue("Teca", "Podem anar pel bosc o per la muntanya."),
        Dialogue("Nil", "Què recomanes, Teca?"),
        Dialogue("Teca", "El bosc és més segur, però la muntanya té més fragments."),
        Dialogue("Nil", "Anirem per la muntanya!")
    )
    dialogueSystem.startDialogue(story)
}
```

## Exemple 4: Animació Simple

```kotlin
class AnimatedCharacter(position: Vector2) : Character(position, "Animated") {
    private val shapeRenderer = ShapeRenderer()
    private var animationTime = 0f
    private var currentFrame = 0
    
    override fun update(delta: Float) {
        super.update(delta)
        
        // Animació simple (canvi de frame)
        animationTime += delta
        if (animationTime >= 0.5f) { // Canvia cada 0.5 segons
            currentFrame = (currentFrame + 1) % 2
            animationTime = 0f
        }
    }
    
    override fun render(batch: SpriteBatch) {
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        
        // Alterna entre dos colors
        if (currentFrame == 0) {
            shapeRenderer.setColor(Color.BROWN)
        } else {
            shapeRenderer.setColor(Color.TAN)
        }
        
        shapeRenderer.rect(position.x, position.y, 32f, 32f)
        shapeRenderer.end()
        batch.begin()
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}
```

## Exemple 5: Sistema de Pistes

```kotlin
class HintSystem(private val font: BitmapFont) {
    private var currentHint: String? = null
    private var hintTime = 0f
    private val hintDuration = 5f // Mostra la pista durant 5 segons
    
    fun showHint(hint: String) {
        currentHint = hint
        hintTime = 0f
    }
    
    fun update(delta: Float) {
        if (currentHint != null) {
            hintTime += delta
            if (hintTime >= hintDuration) {
                currentHint = null
            }
        }
    }
    
    fun render(batch: SpriteBatch) {
        currentHint?.let { hint ->
            val x = Gdx.graphics.width / 2f - 100f
            val y = Gdx.graphics.height - 60f
            
            font.color = Color.YELLOW
            font.draw(batch, "Pista: $hint", x, y)
        }
    }
}

// Ús:
// val hintSystem = HintSystem(game.font)
// hintSystem.showHint("Busca la clau al calaix")
```

## Exemple 6: Sistema de Guardar/Carregar (Simple)

```kotlin
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences

class GameSaveSystem {
    private val prefs: Preferences = Gdx.app.getPreferences("SomnisDeFusta")
    
    fun saveProgress(level: Int, fragmentsCollected: Int) {
        prefs.putInteger("currentLevel", level)
        prefs.putInteger("fragments", fragmentsCollected)
        prefs.flush()
        Gdx.app.log("SaveSystem", "Progress saved")
    }
    
    fun loadProgress(): SaveData {
        val level = prefs.getInteger("currentLevel", 1)
        val fragments = prefs.getInteger("fragments", 0)
        return SaveData(level, fragments)
    }
    
    fun hasData(): Boolean {
        return prefs.contains("currentLevel")
    }
}

data class SaveData(
    val currentLevel: Int,
    val fragmentsCollected: Int
)

// Ús:
// val saveSystem = GameSaveSystem()
// saveSystem.saveProgress(2, 5)
// val data = saveSystem.loadProgress()
```

## Exemple 7: Efecte de Partícules Simple

```kotlin
class SimpleParticle(
    var position: Vector2,
    var velocity: Vector2,
    val color: Color,
    var lifetime: Float
) {
    fun update(delta: Float): Boolean {
        position.add(velocity.x * delta, velocity.y * delta)
        lifetime -= delta
        return lifetime > 0
    }
}

class ParticleSystem {
    private val particles = mutableListOf<SimpleParticle>()
    private val shapeRenderer = ShapeRenderer()
    
    fun emit(position: Vector2, count: Int, color: Color) {
        repeat(count) {
            val angle = Math.random() * Math.PI * 2
            val speed = 50f + Math.random().toFloat() * 50f
            val velocity = Vector2(
                Math.cos(angle).toFloat() * speed,
                Math.sin(angle).toFloat() * speed
            )
            particles.add(SimpleParticle(
                position.cpy(),
                velocity,
                color,
                1f + Math.random().toFloat()
            ))
        }
    }
    
    fun update(delta: Float) {
        particles.removeAll { !it.update(delta) }
    }
    
    fun render(batch: SpriteBatch) {
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        particles.forEach { particle ->
            shapeRenderer.setColor(particle.color)
            shapeRenderer.circle(particle.position.x, particle.position.y, 3f)
        }
        shapeRenderer.end()
        batch.begin()
    }
    
    fun dispose() {
        shapeRenderer.dispose()
    }
}

// Ús:
// val particles = ParticleSystem()
// particles.emit(Vector2(100f, 100f), 20, Color.GOLD) // Explosió daurada
```

## Exemple 8: NPC amb Camí Predefinit

```kotlin
class PatrollingNPC(
    startPosition: Vector2,
    private val waypoints: List<Vector2>
) : Character(startPosition, "Patrol NPC") {
    private var currentWaypointIndex = 0
    private val shapeRenderer = ShapeRenderer()
    
    override fun update(delta: Float) {
        if (waypoints.isEmpty()) return
        
        val targetWaypoint = waypoints[currentWaypointIndex]
        val direction = Vector2(
            targetWaypoint.x - position.x,
            targetWaypoint.y - position.y
        )
        
        if (direction.len() < 5f) {
            // Arribar al waypoint, anar al següent
            currentWaypointIndex = (currentWaypointIndex + 1) % waypoints.size
        } else {
            direction.nor()
            velocity.set(direction).scl(speed)
        }
        
        super.update(delta)
    }
    
    override fun render(batch: SpriteBatch) {
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(Color.PURPLE)
        shapeRenderer.circle(position.x + 16f, position.y + 16f, 16f)
        shapeRenderer.end()
        batch.begin()
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}

// Ús:
// val waypoints = listOf(
//     Vector2(100f, 100f),
//     Vector2(300f, 100f),
//     Vector2(300f, 300f),
//     Vector2(100f, 300f)
// )
// val npc = PatrollingNPC(Vector2(100f, 100f), waypoints)
```

## Exemple 9: Music i Sons

```kotlin
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound

class AudioManager {
    private var backgroundMusic: Music? = null
    private val sounds = mutableMapOf<String, Sound>()
    
    fun loadMusic(path: String) {
        backgroundMusic?.dispose()
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(path))
        backgroundMusic?.isLooping = true
    }
    
    fun playMusic() {
        backgroundMusic?.play()
    }
    
    fun stopMusic() {
        backgroundMusic?.stop()
    }
    
    fun loadSound(name: String, path: String) {
        sounds[name] = Gdx.audio.newSound(Gdx.files.internal(path))
    }
    
    fun playSound(name: String) {
        sounds[name]?.play(1.0f)
    }
    
    fun dispose() {
        backgroundMusic?.dispose()
        sounds.values.forEach { it.dispose() }
    }
}

// Ús:
// val audioManager = AudioManager()
// audioManager.loadMusic("music/background.mp3")
// audioManager.playMusic()
// audioManager.loadSound("collect", "sounds/collect.wav")
// audioManager.playSound("collect")
```

## Notes Finals

- Tots aquests exemples són compatibles amb l'arquitectura actual del joc
- Pots combinar múltiples exemples per crear funcionalitats més complexes
- Recorda sempre cridar `dispose()` per alliberar recursos
- Utilitza `delta` per a moviments i animacions suaus
- Prova els canvis en un dispositiu real per veure el rendiment
