# [LRN-20260510-001] Production Stability Requirements
**Fact**: Mixin configurations MUST contain a `refmap` field even if not used in dev, otherwise production environments will crash.
**Source**: Zenith Error Memory / Session Debugging.
**Application**: Applied in `combat-cooldown-adjuster.mixins.json`.

# [LRN-20260510-002] Future-Proofing Dependencies
**Fact**: To avoid updating for every Minecraft patch, use version ranges like `>=26.1.2` in `fabric.mod.json`.
**Source**: User Request for maintenance reduction.
**Application**: Applied in `fabric.mod.json`.
