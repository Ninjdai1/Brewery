package net.satisfy.brewery.client;

import de.cristelknight.doapi.client.render.block.storage.api.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.api.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.brewery.client.render.block.BeverageRenderer;
import net.satisfy.brewery.registry.StorageTypeRegistry;

public class ClientStorageTypes {

    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init(){
        registerStorageType(StorageTypeRegistry.WINE_BOTTLE, new BeverageRenderer());
    }

}
