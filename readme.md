# Music trainer

Open index.html after compilation to see results (you have to use `fullOptJS` first).

### Fast compilation

Use it for fast code checking.

```
sbt
fastOptJS
```

### Compilation + optimalization

```
sbt
fullOptJS
```

#### Watch

By adding a prefix `~` to `fastOptJS` or `fullOptJS` your code will be tracked for changes and compiled automatically.