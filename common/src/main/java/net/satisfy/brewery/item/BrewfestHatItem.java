package net.satisfy.brewery.item;

import de.cristelknight.doapi.common.item.CustomHatItem;
import net.satisfy.brewery.registry.ArmorMaterialRegistry;
import net.satisfy.brewery.registry.ArmorRegistry;
import net.satisfy.brewery.util.BreweryIdentifier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrewfestHatItem extends CustomHatItem {


    public BrewfestHatItem(Properties settings) {
        super(ArmorMaterialRegistry.BREWFEST_LEATHER, Type.HELMET, settings);
    }

    @Override
    public ResourceLocation getTexture() {
        return new BreweryIdentifier("textures/models/armor/brewfest_hat.png");
    }

    @Override
    public Float getOffset() {
        return -1.9f;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if (world != null && world.isClientSide()) {
            ArmorRegistry.appendTooltip(tooltip);
        }
    }
}
