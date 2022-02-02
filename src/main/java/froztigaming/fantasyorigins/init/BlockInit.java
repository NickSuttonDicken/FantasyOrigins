package froztigaming.fantasyorigins.init;

import froztigaming.fantasyorigins.blocks.containers.DwarvenBlastFurnace;
import froztigaming.fantasyorigins.blocks.containers.HalflingOven;
import froztigaming.fantasyorigins.entities.blocks.DwarvenBlastFurnaceEntity;
import froztigaming.fantasyorigins.entities.blocks.HalflingOvenEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.ToIntFunction;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;
public class BlockInit {

    //Blocks
    public static final DwarvenBlastFurnace DWARVEN_BLAST_FURNACE = new DwarvenBlastFurnace(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4.5f).luminance(createLightLevelFromBlockState(13)));
    public static final HalflingOven HALFLING_OVEN = new HalflingOven(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.5f).luminance(createLightLevelFromBlockState(13)));
    //Block Entities
    public static BlockEntityType<DwarvenBlastFurnaceEntity> DWARVEN_BLAST_FURNACE_ENTITY;
    public static BlockEntityType<HalflingOvenEntity> HALFLING_OVEN_ENTITY;

    public static void registerBlocks()
    {
        Registry.register(Registry.BLOCK, new Identifier(MODID, "dwarven_blast_furnace"), DWARVEN_BLAST_FURNACE);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "halfling_oven"), HALFLING_OVEN);
    }

    public static void registerBlockItems()
    {
        Registry.register(Registry.ITEM, new Identifier(MODID, "dwarven_blast_furnace"), new BlockItem(DWARVEN_BLAST_FURNACE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "halfling_oven"), new BlockItem(HALFLING_OVEN, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
    }

    public static void registerBlockEntities()
    {
        DWARVEN_BLAST_FURNACE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "dwarven_blast_furnace_entity"), FabricBlockEntityTypeBuilder.create(DwarvenBlastFurnaceEntity::new, DWARVEN_BLAST_FURNACE).build(null));
        HALFLING_OVEN_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "halfling_oven_entity"), FabricBlockEntityTypeBuilder.create(HalflingOvenEntity::new, HALFLING_OVEN).build(null));
    }

    private static ToIntFunction<BlockState> createLightLevelFromBlockState(int litLevel) {
        return (blockState) -> (Boolean)blockState.get(Properties.LIT) ? litLevel : 0;
    }
}
