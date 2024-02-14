package net.glasslauncher.mods.api.gcapi.api;

import blue.endless.jankson.JsonObject;
import net.glasslauncher.mods.api.gcapi.impl.EventStorage;

public interface PreConfigSavedListener {

    /**
     * New values are already applied, this is so you can revert invalid values or do some config post-processing.
     * Called before config is saved to file.
     * You can modify newValues to modify what is saved to file.
     * This entrypoint is called once for just your mod. Do not use to modify other mod's configs.
     * @param oldValues the values that were in the config file BEFORE the new values were applied. !!May be incomplete or empty!!
     * @param newValues the values that are about to be saved to the config file.
     * @see net.glasslauncher.mods.api.gcapi.impl.EventStorage.EventSource
     */
    void onPreConfigSaved(int source, JsonObject oldValues, JsonObject newValues);
}
