## Requirements

- Java 21
- Chrome installed locally

## Configuration

Website URLs are defined per environment in `src/test/resources/environments/`, default environment is `prod`:

| File               | Property   | Default value        |
|--------------------|------------|----------------------|
| `prod.properties`  | `web.host` | *(update as needed)* |
| `stage.properties` | `web.host` | *(update as needed)* |
| `test.properties`  | `web.host` | *(update as needed)* |

Update `web.host` in the relevant file before running against a different target.

## Run From Command Line

Run the full suite in parallel:

```bash
./gradlew clean runAcceptPortalParallel
```

Run the full suite sequentially:

```bash
./gradlew clean runAcceptPortal
```

Run with specific tags:

```bash
./gradlew clean runAcceptPortalParallel -PrunTags=@smoke
```

Exclude tags:

```bash
./gradlew clean runAcceptPortalParallel -PrunTags=@all -PignoreTags=@slow
```

Run in parallel with a custom thread count:

```bash
./gradlew clean runAcceptPortalParallel -PparallelForks=4
```

Run against a specific environment:

```bash
./gradlew clean runAcceptPortalParallel -Penvironment=stage
```

Run with a headless browser (defaults to headed):

```bash
./gradlew clean runAcceptPortalParallel -PgebEnv=chromeHeadless
```