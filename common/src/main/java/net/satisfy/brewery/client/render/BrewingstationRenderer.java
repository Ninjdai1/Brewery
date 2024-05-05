package net.satisfy.brewery.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cristelknight.doapi.client.ClientUtil;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.satisfy.brewery.block.brewingstation.BrewingstationBlock;
import net.satisfy.brewery.block.entity.BrewstationBlockEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;

public class BrewingstationRenderer implements BlockEntityRenderer<BrewstationBlockEntity> {

    public BrewingstationRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(BrewstationBlockEntity entity, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!entity.hasLevel() || !(entity.getBlockState().getBlock() instanceof BrewingstationBlock)) return;

        List<ItemStack> ingredients = entity.getIngredient();
        if (ingredients.isEmpty()) return;

        matrixStack.pushPose(); // Ensure this is the first thing done after conditions check
        setupInitialTransform(matrixStack, entity);

        Random random = new Random(entity.getBlockPos().hashCode());
        float angleOffset = 360f / ingredients.size();

        for (int index = 0; index < ingredients.size(); index++) {
            ItemStack stack = ingredients.get(index);
            matrixStack.pushPose();
            Vector3f position = calculateItemPosition(index, angleOffset, ingredients.size());
            applyItemTransform(matrixStack, position, angleOffset * index);
            renderItems(matrixStack, bufferSource, entity, stack, random);
            matrixStack.popPose(); // Match every push with a pop
        }

        matrixStack.popPose(); // Make sure to pop the initial push
    }

    private void setupInitialTransform(PoseStack matrixStack, BrewstationBlockEntity entity) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        matrixStack.translate(1.0f, 0.3F, 1.0f);
    }

    private Vector3f calculateItemPosition(int index, float angleOffset, int itemCount) {
        if (itemCount == 1) return new Vector3f(0, 0.3f, 0);
        double angleRad = Math.toRadians(angleOffset * index);
        return new Vector3f((float) (0.125 * Math.cos(angleRad)), 0.3f, (float) (0.125 * Math.sin(angleRad)));
    }

    private void applyItemTransform(PoseStack matrixStack, Vector3f position, float angle) {
        Quaternionf rotation = new Quaternionf().rotateY(angle + 35).rotateX(65);
        matrixStack.translate(position.x, position.y, position.z);
        matrixStack.mulPose(rotation);
    }

    private void renderItems(PoseStack matrixStack, MultiBufferSource bufferSource, BrewstationBlockEntity entity, ItemStack stack, Random random) {
        for (int i = 0; i <= stack.getCount() / 8; i++) {
            matrixStack.pushPose();
            Vector3f offset = offsetRandomly(random, 1 / 16f);
            matrixStack.translate(offset.x, offset.y, offset.z);
            ClientUtil.renderItem(stack, matrixStack, bufferSource, entity);
            matrixStack.popPose();
        }
    }

    private Vector3f offsetRandomly(Random random, float radius) {
        return new Vector3f((random.nextFloat() - 0.5f) * 2 * radius, (random.nextFloat() - 0.5f) * 2 * radius, (random.nextFloat() - 0.5f) * 2 * radius);
    }
}
