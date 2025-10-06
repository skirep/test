# Guia per a Desenvolupadors - Somnis de Fusta

## Introducció

Aquesta guia proporciona informació tècnica per als desenvolupadors que vulguin entendre, modificar o ampliar el joc Somnis de Fusta.

## Arquitectura del Projecte

### Mòduls

El projecte està dividit en dos mòduls principals:

1. **core**: Conté tota la lògica del joc independent de la plataforma
2. **android**: Conté el codi específic d'Android (launcher, configuració)

### Flux d'Execució

```
AndroidLauncher.kt
    ↓
SomnisDeFustaGame.kt (Main Game Class)
    ↓
MenuScreen.kt → GameScreen.kt
                    ↓
                EnchantedForestLevel.kt
```

## Classes Principals

### SomnisDeFustaGame

Classe principal que hereta de `Game` de LibGDX. Gestiona:
- SpriteBatch per a renderització
- BitmapFont per a text
- GameAssetManager per a recursos
- Transicions entre pantalles (screens)

```kotlin
class SomnisDeFustaGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont
    lateinit var assetManager: GameAssetManager
}
```

### Sistema de Pantalles (Screens)

#### MenuScreen
- Mostra el títol del joc
- Espera un toc per començar
- Transiciona a GameScreen

#### GameScreen
- Gestiona el nivell actual
- Actualitza la lògica del joc
- Renderitza tots els elements
- Gestiona l'input de l'usuari

### Sistema de Personatges

#### Character (Base)
Classe abstracta base per a tots els personatges:
- `position: Vector2` - Posició al món
- `velocity: Vector2` - Velocitat de moviment
- `speed: Float` - Velocitat base
- `update(delta: Float)` - Actualitza lògica
- `render(batch: SpriteBatch)` - Renderitza el personatge

#### Nil
El protagonista controlat pel jugador:
- Mètodes `move(direction: Vector2)` i `stop()`
- Renderitzat com a rectangle marró (placeholder)

#### Teca
La guia màgica:
- Renderitzada com a cercle marró clar (placeholder)
- Actualment estàtica, però pot ser animada

### Sistema de Diàlegs

#### Dialogue
Data class que representa una línia de diàleg:
```kotlin
data class Dialogue(
    val speaker: String,
    val text: String
)
```

#### DialogueSystem
Gestiona la visualització de diàlegs:
- `startDialogue(dialogueList: List<Dialogue>)` - Inicia una seqüència
- `next()` - Avança al següent diàleg
- `isActive()` - Comprova si hi ha un diàleg actiu
- `render(batch: SpriteBatch)` - Renderitza la caixa de diàleg

**Exemple d'ús:**
```kotlin
val dialogues = listOf(
    Dialogue("Teca", "Hola Nil!"),
    Dialogue("Nil", "Hola Teca!")
)
dialogueSystem.startDialogue(dialogues)
```

### Sistema d'Inventari

#### DreamFragment
Representa un fragment de somni:
```kotlin
data class DreamFragment(
    val id: String,
    val name: String,
    val description: String,
    val levelOrigin: String
)
```

#### Inventory
Gestiona l'inventari del jugador:
- `addDreamFragment(fragment: DreamFragment)`
- `addItem(itemName: String, quantity: Int)`
- `removeItem(itemName: String, quantity: Int): Boolean`
- `hasItem(itemName: String, quantity: Int): Boolean`

### Sistema d'Objectes Interactius

#### InteractiveObject (Base)
Classe abstracta per a objectes amb els quals el jugador pot interactuar:
- `position: Vector2` - Posició
- `size: Vector2` - Dimensions
- `isInteractable: Boolean` - Si es pot interactuar
- `onInteract(): InteractionResult` - Gestiona la interacció

#### InteractionResult
Sealed class que representa el resultat d'una interacció:
```kotlin
sealed class InteractionResult {
    object None : InteractionResult()
    data class Dialogue(val dialogues: List<...>) : InteractionResult()
    data class CollectItem(val itemName: String) : InteractionResult()
    data class CollectFragment(val fragment: DreamFragment) : InteractionResult()
    data class Puzzle(val puzzleId: String) : InteractionResult()
}
```

#### WoodenBox
Caixa de fusta que es pot moure:
- `move(direction: Vector2, distance: Float)` - Mou la caixa

#### DreamFragmentObject
Fragment de somni col·leccionable:
- Animació flotant
- Efecte de brillantor
- Es recull en tocar-lo

### Sistema de Nivells

#### Level (Base)
Classe abstracta per a tots els nivells:
- `name: String` - Nom del nivell
- `description: String` - Descripció
- `start()` - Inicialitza el nivell
- `update(delta: Float)` - Actualitza lògica
- `render(batch: SpriteBatch)` - Renderitza elements
- `onComplete()` - Gestiona la finalització

#### EnchantedForestLevel
Primer nivell del joc:
- Diàleg introductori
- Un fragment col·leccionable
- Una caixa de fusta
- Controls tàctils

**Estructura interna:**
```kotlin
class EnchantedForestLevel(game: SomnisDeFustaGame) : Level(game) {
    private lateinit var nil: Nil
    private lateinit var teca: Teca
    val dialogueSystem = DialogueSystem(game.font)
    private val inventory = Inventory()
    private val interactiveObjects = mutableListOf<InteractiveObject>()
}
```

## Afegir Nou Contingut

### Crear un Nou Nivell

1. Crea una nova classe que hereti de `Level`:

```kotlin
class MyNewLevel(game: SomnisDeFustaGame) : Level(game) {
    override val name = "Nom del Nivell"
    override val description = "Descripció"
    
    override fun start() {
        // Inicialitza personatges, objectes, diàlegs
    }
    
    override fun update(delta: Float) {
        // Actualitza lògica del nivell
    }
    
    override fun render(batch: SpriteBatch) {
        // Renderitza tots els elements
    }
    
    override fun onComplete() {
        // Gestiona finalització
    }
    
    override fun dispose() {
        // Allibera recursos
    }
}
```

2. Carrega el nivell des de `GameScreen`:

```kotlin
currentLevel = MyNewLevel(game)
currentLevel?.start()
```

### Crear un Nou Personatge

1. Crea una classe que hereti de `Character`:

```kotlin
class MyCharacter(position: Vector2) : Character(position, "Name") {
    private val shapeRenderer = ShapeRenderer()
    
    override fun render(batch: SpriteBatch) {
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        // Dibuixa el personatge
        shapeRenderer.end()
        batch.begin()
    }
    
    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}
```

### Crear un Nou Objecte Interactiu

1. Crea una classe que hereti de `InteractiveObject`:

```kotlin
class MyObject(position: Vector2) : InteractiveObject(
    position, 
    Vector2(32f, 32f), 
    "Object Name"
) {
    override fun render(batch: SpriteBatch) {
        // Renderitza l'objecte
    }
    
    override fun onInteract(): InteractionResult {
        // Gestiona la interacció
        return InteractionResult.Dialogue(listOf(...))
    }
}
```

2. Afegeix l'objecte al nivell:

```kotlin
val myObject = MyObject(Vector2(x, y))
interactiveObjects.add(myObject)
```

## Controls i Input

### Controls Tàctils

El joc utilitza `Gdx.input` per gestionar l'input tàctil:

```kotlin
// Detectar toc
if (Gdx.input.justTouched()) {
    // Acció en tocar
}

// Detectar toc mantingut
if (Gdx.input.isTouched) {
    val touchX = Gdx.input.x.toFloat()
    val touchY = Gdx.graphics.height - Gdx.input.y.toFloat()
    // Processar toc
}
```

**Nota important**: Les coordenades Y de LibGDX estan invertides respecte a les coordenades d'Android. Sempre utilitza:
```kotlin
val touchY = Gdx.graphics.height - Gdx.input.y.toFloat()
```

## Renderització

### Cicle de Renderització

1. Neteja la pantalla amb `glClearColor` i `glClear`
2. Inicia el batch: `batch.begin()`
3. Dibuixa tots els elements
4. Finalitza el batch: `batch.end()`

### Utilitzar ShapeRenderer

Per dibuixar formes geomètriques (placeholder):

```kotlin
batch.end() // Acaba el batch abans
shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
shapeRenderer.setColor(Color.BROWN)
shapeRenderer.rect(x, y, width, height)
shapeRenderer.end()
batch.begin() // Torna a començar el batch
```

## Gestió d'Assets

### GameAssetManager

Carrega i gestiona textures i altres recursos:

```kotlin
// Carregar una textura
assetManager.loadTexture("player", "sprites/player.png")

// Obtenir una textura
val texture = assetManager.getTexture("player")
```

### Afegir Noves Textures

1. Afegeix les imatges a `android/assets/`
2. Carrega-les amb el GameAssetManager
3. Utilitza-les en la renderització

## Debugging

### Logs

Utilitza el sistema de logging de LibGDX:

```kotlin
Gdx.app.log("Tag", "Message")
Gdx.app.error("Tag", "Error message", exception)
```

### Common Issues

**Problema**: Les textures no es carreguen
- **Solució**: Verifica que els fitxers estiguin a `android/assets/`

**Problema**: Els personatges no es mouen
- **Solució**: Comprova que s'estigui cridant `update(delta)` i que la velocitat no sigui zero

**Problema**: Els diàlegs no apareixen
- **Solució**: Verifica que `dialogueSystem.render()` s'estigui cridant dins de `batch.begin()` i `batch.end()`

## Testing

Actualment no hi ha tests automatitzats, però pots provar el joc:

1. En un emulador Android
2. En un dispositiu físic amb Android Studio
3. Amb el profiler d'Android Studio per optimitzar el rendiment

## Millors Pràctiques

1. **Dispose**: Sempre crida `dispose()` en objectes que l'implementin
2. **Delta time**: Utilitza sempre `delta` per a moviments i animacions
3. **Coordenades**: Recorda invertir les Y per a l'input tàctil
4. **Batch**: Minimitza les crides a `begin()` i `end()`
5. **Assets**: Carrega assets una sola vegada, no cada frame
6. **Logging**: Utilitza logs per debug, però elimina'ls en producció

## Recursos Útils

- [LibGDX Wiki](https://libgdx.com/wiki/)
- [LibGDX API Docs](https://libgdx.com/dev/javadoc/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Android Developers](https://developer.android.com/)

## Contribució

Si vols contribuir al projecte:

1. Segueix l'estil de codi existent
2. Comenta el codi en català (com els diàlegs del joc)
3. Prova els canvis abans de fer commit
4. Documenta les noves funcionalitats
