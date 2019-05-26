# Tabella delle responsabilità

### Deliverable

| Attività                      | Frarlo | Bork | Ferra | Brozio |
| ----------------------------- |:------:|:----:|:-----:|:------:|
|Casi d'uso|                    |        |      |X      |        |
|Diagramma classi               |X       |      |       |        |
|Diagramma di sequenza          |        |      |X      |        |
|Doxygen                        |        |X     |       |        |

### Moduli Software

#### Implementazione

Le interfacce non necessitano di implementazione dei metodi

Le classi con prefisso _Base_ sono implementazioni di base di interfacce e, 
in quanto contengono funzionalità condivise da tutte le classi che le implementano, 
sono modificate da tutti in base alle necessità

| Classe                        | Frarlo | Bork | Ferra | Brozio |
| ----------------------------- |:------:|:----:|:-----:|:------:|
|GiocoPalla                     |X       |      |       |        |
|EntityManager                  |        |X     |       |        |
|BallEntity                     |        |X     |       |        |
|PoolEntity                     |X       |      |       |        |
|PoolList                       |        |X     |       |        |
|PhysicsThread                  |        |X     |       |        |
|GameGui                        |        |      |X      |        |
|PauseGui                       |X       |      |       |        |
|PauseMenuGui                   |        |      |X      |        |
|OptionsGui                     |        |      |       |X       |
|ColorSlider                    |X       |      |       |        |
|PauseMenuButton                |X       |      |       |        |
|PauseButton                    |        |      |X      |        |
|ValueManager                   |        |      |       |X       |
|Value                          |        |      |       |X       |
|ColorValue                     |X       |      |       |        |
|InputManager                   |        |      |X      |        |
|InputSubscription              |        |      |X      |        |
|InputGyroscope                 |        |      |       |X       |
|NoInput                        |        |      |       |X       |
|FastMath                       |X       |      |       |        |
|ScaledResolution               |X       |      |       |        |

#### Commenti

| Classe                        | Frarlo | Bork | Ferra | Brozio |
| ----------------------------- |:------:|:----:|:-----:|:------:|
|GiocoPalla                     |        |      |       |        |
|Entity                         |        |      |       |        |
|EntityManager                  |        |X     |       |        |
|BallEntity                     |        |X     |       |        |
|PoolEntity                     |        |      |       |        |
|PoolList                       |        |X     |       |        |
|PhysicsThread                  |        |X     |       |        |
|Gui                            |        |      |       |        |
|BaseGui                        |        |      |       |        |
|GameGui                        |        |      |       |        |
|PauseGui                       |        |      |       |        |
|PauseMenuGui                   |        |      |       |        |
|OptionsGui                     |        |      |       |        |
|Component                      |        |      |       |        |
|BaseGuiComponent               |        |      |       |        |
|ColorSlider                    |        |      |       |        |
|PauseMenuButton                |        |      |       |        |
|PauseButton                    |        |      |       |        |
|ValueManager                   |        |      |       |X       |
|Value                          |        |      |       |X       |
|ColorValue                     |        |      |       |X       |
|InputManager                   |        |      |       |X       |
|InputSubscription              |        |      |       |X       |
|InputData                      |        |      |       |        |
|InputGyroscope                 |        |      |       |X       |
|NoInput                        |        |      |       |X       |
|FastMath                       |        |      |       |X       |
|ScaledResolution               |        |      |       |X       |


> [Torna al README](../README.md)
