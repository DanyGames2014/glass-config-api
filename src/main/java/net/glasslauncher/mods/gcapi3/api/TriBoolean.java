package net.glasslauncher.mods.gcapi3.api;

/**
 * Used in ValueOnVanillaServer.
 * @see ValueOnVanillaServer
 */
public enum TriBoolean {
    TRUE(true),
    FALSE(false),
    DEFAULT(null);

    public final Boolean value;

    TriBoolean(Boolean value) {
        this.value = value;
    }
}
