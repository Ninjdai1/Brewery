package net.bmjo.brewery.client;

import com.mojang.math.Vector3f;
import net.bmjo.brewery.Brewery;
import net.bmjo.brewery.entity.RopeKnotEntity;
import net.bmjo.brewery.networking.BreweryNetworking;
import net.bmjo.brewery.util.rope.IncompleteRopeConnection;
import net.bmjo.brewery.util.rope.RopeConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class RopeHelper {
    @Nullable
    public static Entity createEntity(Minecraft client, EntityType<?> type, UUID uuid, int id, Vec3 pos) {
        if (client.level == null) {
            Brewery.LOGGER.error("Tried to spawn entity in a null world!");
            return null;
        }

        Entity entity = type.create(client.level);
        if (entity == null) {
            Brewery.LOGGER.error("Failed to create instance of entity with type {}.", type);
            return null;
        }
        entity.syncPacketPositionCodec(pos.x, pos.y, pos.z);
        entity.moveTo(pos.x, pos.y, pos.z);
        entity.setId(id);
        entity.setUUID(uuid);
        entity.setDeltaMovement(Vec3.ZERO);
        return entity;
    }

    public static void createLink(Minecraft client, int fromId, int toIds) {
        createLinks(client, fromId, new int[]{toIds});
    }

    public static void createLinks(Minecraft client, int fromId, int[] toIds) {
        if (client.level == null) return;
        Entity from = client.level.getEntity(fromId);
        if (from instanceof RopeKnotEntity fromKnot) {
            for (int toId : toIds) {
                Entity to = client.level.getEntity(toId);
                if (to == null) {
                    BreweryNetworking.incompleteLinks.add(new IncompleteRopeConnection(fromKnot, toId));
                } else {
                    RopeConnection.create(fromKnot, to);
                }
            }
        }
    }

    public static Vec3 getChainOffset(Vec3 start, Vec3 end) {
        Vec3 vec = end.subtract(start);
        Vector3f offset = new Vector3f((float)vec.x(), 0.0F, (float)vec.z());
        offset.normalize();
        offset.mul(2 / 16F);
        return new Vec3(offset.x(), offset.y(), offset.z());
    }

    private static final double HANGING_AMOUNT = 50.0F;

    public static double drip2(double x, double d, double h) {
        double a = HANGING_AMOUNT; // 7
        double p1 = a * asinh((h / (2D * a)) * (1D / Math.sinh(d / (2D * a))));
        double p2 = -a * Math.cosh((2D * p1 - d) / (2D * a));
        return p2 + a * Math.cosh((((2D * x) + (2D * p1)) - d) / (2D * a));
    }

    private static double asinh(double x) {
        return Math.log(x + Math.sqrt(x * x + 1.0));
    }
}
