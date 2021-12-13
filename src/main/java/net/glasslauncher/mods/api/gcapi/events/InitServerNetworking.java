package net.glasslauncher.mods.api.gcapi.events;

import net.glasslauncher.mods.api.gcapi.impl.GlassConfigAPI;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.NBTIO;
import net.modificationstation.stationapi.api.event.registry.MessageListenerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.network.ModdedPacketHandler;
import net.modificationstation.stationapi.api.packet.Message;
import net.modificationstation.stationapi.api.packet.PacketHelper;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.server.event.network.PlayerLoginEvent;
import net.modificationstation.stationapi.api.util.Null;

import java.io.ByteArrayOutputStream;
import java.util.WeakHashMap;

public class InitServerNetworking {

    public static final WeakHashMap<PlayerBase, Boolean> GCAPI_PLAYERS = new WeakHashMap<>();

    @Entrypoint.ModID
    private final ModID modID = Null.get();

    @EventListener
    private void registerNetworkShit(MessageListenerRegistryEvent event) {
        event.registry.register(Identifier.of(modID, "ping"), (playerBase, message) -> {
            GCAPI_PLAYERS.put(playerBase, true);
            Message configSync = new Message(Identifier.of(modID, "config_sync"));
            CompoundTag compoundTag = new CompoundTag();
            GlassConfigAPI.exportConfigsForServer(compoundTag);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            NBTIO.writeGzipped(compoundTag, byteArrayOutputStream);
            configSync.bytes = byteArrayOutputStream.toByteArray();
            PacketHelper.sendTo(playerBase, configSync);
        });
    }

    @EventListener
    private void doPlayerShit(PlayerLoginEvent event) {
        if (((ModdedPacketHandler) event.player.packetHandler).isModded()) {
            PacketHelper.sendTo(event.player, new Message(Identifier.of(modID, "ping")));
        }
    }
}
