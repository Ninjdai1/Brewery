package net.satisfy.brewery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.satisfy.brewery.Brewery;
import net.satisfy.brewery.block.entity.BeerKegFlowerPotBlockEntity;
import net.satisfy.brewery.block.entity.BrewstationBlockEntity;
import net.satisfy.brewery.block.entity.SiloBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Brewery.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<BrewstationBlockEntity>> BREWINGSTATION_BLOCK_ENTITY = create("brewingstation", () -> BlockEntityType.Builder.of(BrewstationBlockEntity::new, ObjectRegistry.WOODEN_BREWINGSTATION.get(),  ObjectRegistry.COPPER_BREWINGSTATION.get(),  ObjectRegistry.NETHERITE_BREWINGSTATION.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BeerKegFlowerPotBlockEntity>> BEER_MUG_FLOWER_POT_BLOCK_ENTITY = create("beer_mug", () -> BlockEntityType.Builder.of(BeerKegFlowerPotBlockEntity::new, ObjectRegistry.BEER_MUG.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SiloBlockEntity>> SILO = create("silo", () -> BlockEntityType.Builder.of(SiloBlockEntity::new, ObjectRegistry.SILO_WOOD.get(), ObjectRegistry.SILO_COPPER.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(path, type);
    }

    public static void init(){
        Brewery.LOGGER.debug("Registering Mod BlockEntities for " + Brewery.MOD_ID);
        BLOCK_ENTITY_TYPES.register();
    }

}
