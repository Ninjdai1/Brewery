package net.satisfy.brewery.forge.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.satisfy.brewery.Brewery;
import net.satisfy.brewery.client.BreweryClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.satisfy.brewery.client.model.BeerElementalModel;
import net.satisfy.brewery.client.model.RopeKnotEntityModel;
import net.satisfy.brewery.client.render.block.HangingRopeRenderer;
import net.satisfy.brewery.client.render.block.RopeCollisionEntityRenderer;
import net.satisfy.brewery.client.render.block.RopeKnotRenderer;
import net.satisfy.brewery.client.render.entity.BeerElementalAttackRenderer;
import net.satisfy.brewery.client.render.entity.BeerElementalRenderer;
import net.satisfy.brewery.registry.EntityRegistry;
import net.satisfy.brewery.registry.ModelRegistry;

@Mod.EventBusSubscriber(modid = Brewery.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BreweryClientForge {

    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        BreweryClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BreweryClient.onInitializeClient();
    }

    public static void entityRendererSetup() {
        EntityModelLayerRegistry.register(ModelRegistry.BEER_ELEMENTAL, BeerElementalModel::createBodyLayer);
        EntityRendererRegistry.register(EntityRegistry.BEER_ELEMENTAL, BeerElementalRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.BEER_ELEMENTAL_ATTACK, BeerElementalAttackRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.ROPE_COLLISION, RopeCollisionEntityRenderer::new);

        EntityModelLayerRegistry.register(ModelRegistry.ROPE_KNOT, RopeKnotEntityModel::createBodyLayer);
        EntityRendererRegistry.register(EntityRegistry.ROPE_KNOT, RopeKnotRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.HANGING_ROPE, HangingRopeRenderer::new);

    }
}
