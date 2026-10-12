# Ribbits 4.1.4 — unofficial port to Minecraft 26.2 (NeoForge 26.2.0.88)

This branch (`port-26.2-dum`) ports [Ribbits](https://github.com/yungnickyoung/Ribbits) by YUNGNICKYOUNG, Joosh,
Flechazo, HellionGames and Refresh Studios to Minecraft 26.2 for the Misty Dale modpack. It is **not** an official
release and is not supported by the original authors — please report problems with this port here, not upstream.
When an official 26.2 version appears, use that one.

License: GNU LGPL v3 (unchanged, see `LICENSE.txt`). Only the NeoForge side was ported; the Fabric module is untouched.
Requires the 26.2 port of YUNG's API (6.1.3-dum1), GeckoLib 5.5, Architectury and Cloth Config.

## What changed
- 26.2 API renames (`ResourceLocation` → `Identifier`, `read/writeIdentifier`, `getOverworldClockTime`,
  `isWithinEntityInteractionRange`, `EntityType` constants → `EntityTypes`).
- Structure processors: `StructureProcessor` is an interface in 26.2 (`codec()` instead of `getType()`, `process(...)`
  with the template argument); the six Ribbits processors follow, same logic.
- GeckoLib 5.5 (`com.geckolib`): bone visibility per render pass via `adjustModelBonesForRender` and
  `BoneSnapshot.skipRender`, same choice of bones as before.
- Rendering: `RenderTypes.entityCutout` (the old no-cull type); `ItemBlockRenderTypes` no longer exists (layer comes
  from sprite transparency); spawn eggs via `Properties.spawnEgg`; `FireBlock.setFlammable` through an `@Invoker`.
- Vegetation features in the 26.2 format.
- The GeoIP request to ip-api.com (used upstream to pick a config preset for players in mainland China) is disabled.

Built with plain `javac` against the 26.2 client (no Gradle update), Common + NeoForge sources merged.
