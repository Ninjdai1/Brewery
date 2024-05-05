package net.satisfy.brewery.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.brewery.client.model.BeerElementalModel;
import net.satisfy.brewery.entity.BeerElementalEntity;

import static net.satisfy.brewery.Brewery.MOD_ID;


public class
BeerElementalRenderer extends MobRenderer<BeerElementalEntity, BeerElementalModel<BeerElementalEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/beer_elemental.png");

    public BeerElementalRenderer(EntityRendererProvider.Context context) {
        super(context, new BeerElementalModel<>(context.bakeLayer(BeerElementalModel.BEER_ELEMENTAL_MODEL_LAYER)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(BeerElementalEntity entity) {
        return TEXTURE;
    }
}
