# Music trainer

Open index.html after compilation to see results (you have to use `fullOptJS` first).

### Node modules
Install angular dependencies.

```
npm install
```

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

### Start server
```
npm start
```

#### Watch

By adding a prefix `~` to `fastOptJS` or `fullOptJS` your code will be tracked for changes and compiled automatically.
When you are using lite-server (npm start), then site is reloaded automatically after recompilation.
