# Tabella delle responsabilità

### Deliverable

| Attività                      | Frarlo | Bork | Ferra | Brozio |
| ----------------------------- |:------:|:----:|:-----:|:------:|
|Casi d'uso|                    |        |      |X      |        |
|Diagramma classi               |X       |      |       |        |
|Diagramma di sequenza          |        |      |X      |        |
|Doxygen                        |        |X     |       |        |

### Moduli Software

Le interfacce non necessitano di implementazione dei metodi

Le classi con prefisso _Base_ sono implementazioni di base di interfacce e, 
in quanto contengono funzionalità condivise da tutte le classi che le implementano, 
sono modificate da tutti in base alle necessità

| Classi                        | Frarlo | Bork | Ferra | Brozio |
| ----------------------------- |:------:|:----:|:-----:|:------:|
|GiocoPalla                     |X       |      |       |        |
|EntityManager                  |        |X     |       |        |
|BallEntity                     |        |X     |       |        |
|PoolEntity                     |X       |      |       |        |
|PhysicsThread                  |        |X     |       |        |
|OptionsGui                     |        |      |       |X       |
|PauseMenuGui                   |        |      |X      |        |
|GameGui                        |        |      |X      |        |
|ValueManager                   |        |      |       |X       |
|Value                          |        |      |       |X       |
|ColorValue                     |X       |      |       |        |
|ColorSlider                    |X       |      |       |        |
|PauseMenuButton                |X       |      |       |        |
|PauseButton                    |X       |      |       |        |
|InputManager                   |        |      |X      |        |
|InputSubscription              |        |      |X      |        |
|InputGyroscope                 |        |      |       |X       |
|NoInput                        |X       |      |       |        |

> [Torna al README](../README.md)
