package net.satisfy.brewery.registry;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.client.render.feature.CustomArmorManager;
import de.cristelknight.doapi.client.render.feature.CustomArmorSet;
import de.cristelknight.doapi.client.render.feature.FullCustomArmor;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.satisfy.brewery.client.model.*;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.satisfy.brewery.item.IBrewfestArmorSet;
import net.satisfy.brewery.util.BreweryIdentifier;

import java.util.List;
import java.util.Map;

public class ArmorRegistry {
    public static void registerArmorModelLayers(){
        EntityModelLayerRegistry.register(BrewfestHatModel.LAYER_LOCATION, BrewfestHatModel::getTexturedModelData);
        EntityModelLayerRegistry.register(LederhosenInner.LAYER_LOCATION, LederhosenInner::createBodyLayer);
        EntityModelLayerRegistry.register(LederhosenOuter.LAYER_LOCATION, LederhosenOuter::createBodyLayer);
        EntityModelLayerRegistry.register(DirndlInner.LAYER_LOCATION, DirndlInner::createBodyLayer);
        EntityModelLayerRegistry.register(DirndlOuter.LAYER_LOCATION, DirndlOuter::createBodyLayer);

    }

    public static <T extends LivingEntity> void registerArmorModels(CustomArmorManager<T> armors, EntityModelSet modelLoader) {
        armors.addArmor(new CustomArmorSet<T>(ObjectRegistry.BREWFEST_HAT.get(), ObjectRegistry.BREWFEST_REGALIA.get(), ObjectRegistry.BREWFEST_BOOTS.get(), ObjectRegistry.BREWFEST_TROUSERS.get())
                .setTexture(new BreweryIdentifier("lederhosen"))
                .setOuterModel(new LederhosenOuter<>(modelLoader.bakeLayer(LederhosenOuter.LAYER_LOCATION)))
                .setInnerModel(new LederhosenInner<>(modelLoader.bakeLayer(LederhosenInner.LAYER_LOCATION))));
        armors.addArmor(new CustomArmorSet<T>(ObjectRegistry.BREWFEST_HAT_RED.get(), ObjectRegistry.BREWFEST_BLOUSE.get(), ObjectRegistry.BREWFEST_DRESS.get(), ObjectRegistry.BREWFEST_SHOES.get())
                .setTexture(new BreweryIdentifier("dirndl"))
                .setOuterModel(new LederhosenOuter<>(modelLoader.bakeLayer(LederhosenOuter.LAYER_LOCATION)))
                .setInnerModel(new LederhosenInner<>(modelLoader.bakeLayer(LederhosenInner.LAYER_LOCATION))));
    }

    public static  <T extends LivingEntity> void registerHatModels(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.BREWFEST_HAT.get(), new BrewfestHatModel<>(modelLoader.bakeLayer(BrewfestHatModel.LAYER_LOCATION)));
        models.put(ObjectRegistry.BREWFEST_HAT_RED.get(), new BrewfestHatModel<>(modelLoader.bakeLayer(BrewfestHatModel.LAYER_LOCATION)));
    }

    public static void appendTooltip(List<Component> tooltip) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean helmet = IBrewfestArmorSet.hasBrewfestHelmet(player);
        boolean breastplate = IBrewfestArmorSet.hasBrewfestBreastplate(player);
        boolean leggings = IBrewfestArmorSet.hasBrewfestLeggings(player);
        boolean boots = IBrewfestArmorSet.hasBrewfestBoots(player);
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.brewfest.brewfestdrop").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.brewfest.brewfest_set").withStyle(ChatFormatting.DARK_GREEN, ChatFormatting.BOLD));
        tooltip.add(helmet ? Component.translatable("tooltip.brewfest.brewfesthelmet").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.brewfest.brewfesthelmet").withStyle(ChatFormatting.GRAY));
        tooltip.add(breastplate ? Component.translatable("tooltip.brewfest.brewfestbreastplate").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.brewfest.brewfestbreastplate").withStyle(ChatFormatting.GRAY));
        tooltip.add(leggings ? Component.translatable("tooltip.brewfest.brewfestleggings").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.brewfest.brewfestleggings").withStyle(ChatFormatting.GRAY));
        tooltip.add(boots ? Component.translatable("tooltip.brewfest.brewfestboots").withStyle(ChatFormatting.GREEN) : Component.translatable("tooltip.brewfest.brewfestboots").withStyle(ChatFormatting.GRAY));
    }
}
