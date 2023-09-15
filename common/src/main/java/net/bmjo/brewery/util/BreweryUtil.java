package net.bmjo.brewery.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BreweryUtil {
    private static final String BLOCK_POS_KEY = "block_pos";

    public static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
        Objects.requireNonNull(pos, "BlockPos cannot be null");
        return tracking(world, new ChunkPos(pos));
    }

    public static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
        Objects.requireNonNull(world, "The world cannot be null");
        Objects.requireNonNull(pos, "The chunk pos cannot be null");

        return world.getChunkSource().chunkMap.getPlayers(pos, false);
    }

    public static int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    public static <T extends BlockEntity> void renderItem(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, T blockEntity) {
        Level level = blockEntity.getLevel();
        if (level != null) {
            Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(level, blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, 1);
        }
    }

    public static void putBlockPos(CompoundTag compoundTag, Collection<BlockPos> blockPoses) {
        if (blockPoses == null || blockPoses.isEmpty()) return;
        int[] positions = new int[blockPoses.size() * 3];
        int pos = 0;
        for (BlockPos blockPos : blockPoses) {
            positions[pos * 3] = blockPos.getX();
            positions[pos * 3 + 1] = blockPos.getY();
            positions[pos * 3 + 2] = blockPos.getZ();
            pos++;
        }
        compoundTag.putIntArray(BLOCK_POS_KEY, positions);
    }


    public static Set<BlockPos> readBlockPos(CompoundTag compoundTag) {
        int[] positions = compoundTag.getIntArray(BLOCK_POS_KEY);
        Set<BlockPos> blockSet = new HashSet<>();
        for (int pos = 0; pos < positions.length / 3; pos++) {
            blockSet.add(new BlockPos(positions[pos * 3], positions[pos * 3 + 1], positions[pos * 3 + 2]));
        }
        return blockSet;
    }
}
