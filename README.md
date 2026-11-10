# Donut Pay FX

A **client-side Fabric** source project for a DonutSMP-style payment celebration mod.

## What it does
- Reads payment messages that match `Player paid you $Amount`
- Tracks every detected incoming payment locally
- Clean top-payer leaderboard
- Right Shift opens the menu
- Preview buttons for 1M / 5M / 10M / 100M / 1B tiers
- Tiered sound/effect trigger system ready for richer HUD rendering

## Tier design
| Amount | Effect |
|---|---|
| 1M | Clean top-right premium notification |
| 5M | Firework burst |
| 10M | Bigger fireworks + dramatic payer reveal |
| 100M | JACKPOT sequence |
| 1B | Multi-stage legendary celebration |

## Build
This project targets the versions listed in `gradle.properties`.

```bash
./gradlew build
```

Windows:

```bat
gradlew.bat build
```

The JAR should be produced in:

`build/libs/`

> If Fabric/Yarn version coordinates have changed, update the version values in
> `gradle.properties` to the current matching versions from Fabric's developer
> documentation.

## Important
This mod is designed to be **client-side and visual/accounting only**. It reads
chat that your client already receives and does not send payments, modify server
balances, or fabricate payment confirmations.

Check DonutSMP's current rules before using any client mod.

## GitHub
1. Create a new GitHub repository.
2. Upload/extract this ZIP.
3. Commit the files.
4. Build with Gradle locally or in GitHub Actions.

## Next upgrade ideas
- Real animated HUD renderer with particle sprites
- Screen shake and chromatic-style overlays
- Persistent JSON history
- Player head/skin lookup
- Custom colors and thresholds
- OBS-friendly streamer mode
