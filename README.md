# Somnis de Fusta

**Un joc narratiu i d'aventura per a Android**

## Descripció

Somnis de Fusta és un joc 2D narratiu i d'aventura desenvolupat amb Kotlin i LibGDX per a dispositius Android. El jugador controla Nil, un nen que ha perdut la capacitat de somiar i ha de viatjar per mons surrealistes fets de fusta per recuperar fragments dels seus somnis.

## Història

El protagonista és un nen anomenat **Nil** que ha perdut la capacitat de somiar. Ha de viatjar per mons surrealistes fets de fusta per recuperar fragments dels seus somnis. Cada nivell representa un somni trencat (ex: vaixell pirata, casa de nines, bosc encantat). El seu guia és una marioneta màgica anomenada **Teca**.

## Característiques

### Funcionalitats Implementades

✅ **Sistema de Nivells**
- Arquitectura modular per a nivells
- Transicions suaus entre escenes
- Primer nivell implementat: Bosc Encantat

✅ **Sistema de Diàlegs**
- Interaccions entre personatges
- Sistema de text amb múltiples línies
- Diàlegs introdutoris amb Teca

✅ **Sistema d'Inventari**
- Recollida de fragments de somnis
- Gestió d'objectes
- Comptador de fragments

✅ **Mecàniques de Puzles**
- Caixes de fusta que es poden moure
- Objectes interactius
- Sistema base per a combinar objectes

✅ **Controls Tàctils**
- Moviment del personatge mitjançant toc de pantalla
- Toc per avançar diàlegs
- Interacció amb objectes per proximitat

✅ **Gestió d'Estats**
- Menú principal
- Pantalla de joc
- Sistema de pausa (base implementada)
- Final del joc (estructura preparada)

✅ **Estètica Visual**
- Colors càlids inspirats en fusta
- Formes geomètriques simples (placeholder per a gràfics finals)
- Animacions bàsiques (fragments flotants)

## Arquitectura del Projecte

```
SomnisDeFusta/
├── android/                    # Mòdul Android
│   ├── src/main/
│   │   ├── kotlin/
│   │   │   └── com/somnis/fusta/
│   │   │       └── AndroidLauncher.kt
│   │   ├── AndroidManifest.xml
│   │   └── res/
│   └── build.gradle.kts
│
├── core/                       # Mòdul del joc (lògica principal)
│   ├── src/main/kotlin/com/somnis/fusta/
│   │   ├── SomnisDeFustaGame.kt        # Classe principal del joc
│   │   ├── GameAssetManager.kt         # Gestió d'assets
│   │   ├── GameState.kt                # Estats del joc
│   │   │
│   │   ├── characters/                 # Personatges
│   │   │   ├── Character.kt
│   │   │   ├── Nil.kt                  # Protagonista
│   │   │   └── Teca.kt                 # Guia màgica
│   │   │
│   │   ├── dialogue/                   # Sistema de diàlegs
│   │   │   ├── Dialogue.kt
│   │   │   └── DialogueSystem.kt
│   │   │
│   │   ├── inventory/                  # Sistema d'inventari
│   │   │   ├── DreamFragment.kt
│   │   │   └── Inventory.kt
│   │   │
│   │   ├── objects/                    # Objectes interactius
│   │   │   ├── InteractiveObject.kt
│   │   │   ├── WoodenBox.kt
│   │   │   └── DreamFragmentObject.kt
│   │   │
│   │   ├── levels/                     # Nivells del joc
│   │   │   ├── Level.kt
│   │   │   └── EnchantedForestLevel.kt
│   │   │
│   │   └── screens/                    # Pantalles del joc
│   │       ├── MenuScreen.kt
│   │       └── GameScreen.kt
│   │
│   └── build.gradle.kts
│
├── build.gradle.kts              # Configuració principal
├── settings.gradle.kts           # Configuració de mòduls
└── gradle.properties            # Propietats de Gradle
```

## Tecnologies Utilitzades

- **Kotlin** - Llenguatge de programació principal
- **LibGDX** - Framework per a jocs 2D multiplataforma
- **Android SDK** - Desenvolupament per a Android
- **Gradle** - Sistema de construcció

## Requisits

- Android Studio Arctic Fox o superior
- JDK 11 o superior
- Android SDK (API 24+)
- Gradle 7.0+

## Compilació i Execució

### Amb Android Studio

1. Clona el repositori:
```bash
git clone https://github.com/skirep/test.git
cd test
```

2. Obre el projecte amb Android Studio

3. Sincronitza les dependències de Gradle

4. Executa el mòdul `android` en un emulador o dispositiu físic

### Amb Línia de Comandes

```bash
# Compilar el projecte
./gradlew build

# Instal·lar en un dispositiu Android connectat
./gradlew android:installDebug

# Executar en un emulador
./gradlew android:run
```

### Amb GitHub Actions

El projecte inclou un workflow de GitHub Actions que genera automàticament l'APK:

- **Activació automàtica**: S'executa en cada push a les branques `main` o `develop`, i en pull requests
- **Activació manual**: Pots executar-lo manualment des de la pestanya "Actions" del repositori
- **Artefactes**: Els APKs generats (debug i release) es poden descarregar des de la pàgina del workflow
- **Retenció**: Els artefactes es guarden durant 30 dies

Per executar manualment el workflow:
1. Ves a la pestanya "Actions" del repositori a GitHub
2. Selecciona "Build Android APK" a la llista de workflows
3. Clica "Run workflow" i selecciona la branca
4. Un cop completat, descarrega l'APK des de la secció "Artifacts"

## Com Jugar

1. **Menú Principal**: Toca la pantalla per començar
2. **Moviment**: Toca qualsevol lloc de la pantalla per moure Nil cap aquella direcció
3. **Diàlegs**: Toca la pantalla per avançar en els diàlegs
4. **Objectius**: Recull els fragments de somnis (esferes daurades brillants)
5. **Puzles**: Interactua amb caixes i altres objectes per resoldre puzles

## Estructura de Classes Principals

### SomnisDeFustaGame
Classe principal que gestiona el cicle de vida del joc i les transicions entre pantalles.

### Character (base), Nil, Teca
Sistema de personatges amb moviment, renderització i lògica específica.

### DialogueSystem
Gestiona la visualització de diàlegs amb suport per a múltiples línies i personatges.

### Inventory
Gestiona els fragments de somnis i objectes recollits pel jugador.

### InteractiveObject (base), WoodenBox, DreamFragmentObject
Sistema d'objectes interactius amb mecàniques de puzles.

### Level (base), EnchantedForestLevel
Sistema modular de nivells. Cada nivell és independent i pot tenir els seus propis objectes, diàlegs i mecàniques.

## Nivells Implementats

### 1. Bosc Encantat (EnchantedForestLevel)
El primer nivell on Nil coneix Teca i comença la seva aventura. Inclou:
- Diàleg introductori amb Teca
- Un fragment de somni per recollir
- Una caixa de fusta com a introducció a les mecàniques de puzles
- Controls tàctils bàsics

## Pròxims Passos

### Funcionalitats Pendents
- [ ] Afegir textures i sprites reals (actualment s'utilitzen formes geomètriques)
- [ ] Implementar música ambiental i efectes de so
- [ ] Afegir més nivells (Vaixell Pirata, Casa de Nines)
- [ ] Ampliar les mecàniques de puzles (combinació d'objectes, seqüències)
- [ ] Sistema de guarda i càrrega de partides
- [ ] Animacions avançades per als personatges
- [ ] Millores en la UI (botons, menús, inventari visual)
- [ ] Sistema de pausa funcional
- [ ] Pantalla de final del joc

### Millores Tècniques
- [ ] Asset loading asíncron
- [ ] Sistema de localització per a múltiples idiomes
- [ ] Optimització de rendiment
- [ ] Tests unitaris i d'integració
- [ ] Gestió de diferents resolucions de pantalla

## Contribució

Aquest és un projecte educatiu. Si vols contribuir:

1. Fes un fork del repositori
2. Crea una branca per a la teva funcionalitat (`git checkout -b feature/nova-funcionalitat`)
3. Commit els canvis (`git commit -am 'Afegeix nova funcionalitat'`)
4. Push a la branca (`git push origin feature/nova-funcionalitat`)
5. Crea un Pull Request

## Llicència

Aquest projecte està sota llicència MIT. Consulta el fitxer LICENSE per a més detalls.

## Autors

- Desenvolupament inicial: [skirep]

## Agraïments

- LibGDX per proporcionar un excel·lent framework de desenvolupament de jocs
- La comunitat de desenvolupadors de jocs en Kotlin

---

**Nota**: Aquest projecte està en desenvolupament actiu. Els gràfics actuals són placeholders i seran substituïts per artwork definitiu en el futur.
