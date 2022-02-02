package froztigaming.fantasyorigins.init;

import froztigaming.fantasyorigins.entities.items.SpearEntity;
import froztigaming.fantasyorigins.entities.items.TritonTridentEntity;
import froztigaming.fantasyorigins.items.*;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Objects;

import static froztigaming.fantasyorigins.FantasyOrigins.MODID;

public class ItemInit {

    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    //Items
    public static final NetherPortalBottle NETHER_PORTAL_BOTTLE = new NetherPortalBottle(new Item.Settings().group(ItemGroup.TRANSPORTATION).maxDamage(1));
    public static final EndPortalBottle END_PORTAL_BOTTLE = new EndPortalBottle(new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final TravelerStone TRAVELER_STONE = new TravelerStone(new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1).maxDamage(20));
    public static final Item GOBLIN_SPAWN_EGG = new SpawnEggItem(EntityInit.GOBLIN, 2721385, 8943433, new Item.Settings().group(ItemGroup.MISC));
    public static TritonTridentItem TRITON_TRIDENT = new TritonTridentItem(new Item.Settings().maxDamage(550).group(ItemGroup.COMBAT), EntityInit.TRITON_TRIDENT);
    public static SpearItem SPEAR = new SpearItem(new Item.Settings().maxDamage(150).group(ItemGroup.COMBAT), EntityInit.SPEAR);
    public static ElvenBowItem ELVEN_BOW = new ElvenBowItem(new Item.Settings().maxDamage(768).group(ItemGroup.COMBAT));

    public static void registerItems()
    {
        //Items
        Registry.register(Registry.ITEM, new Identifier(MODID, "traveler_stone"), TRAVELER_STONE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "nether_portal_bottle"), NETHER_PORTAL_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "end_portal_bottle"), END_PORTAL_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "goblin_spawn_egg"), GOBLIN_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MODID, "elven_bow"), ELVEN_BOW);
        Registry.register(Registry.ITEM, new Identifier(MODID, "triton_trident"), TRITON_TRIDENT);
        DispenserBlock.registerBehavior(TRITON_TRIDENT, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                TritonTridentEntity tridentEntity = Objects.requireNonNull(TRITON_TRIDENT.getEntityType().create(world));
                tridentEntity.setPos(position.getX(), position.getY(), position.getZ());
                stack.decrement(1);
                return tridentEntity;
            }
        });
        Registry.register(Registry.ITEM, new Identifier(MODID, "spear"), SPEAR);
        DispenserBlock.registerBehavior(SPEAR, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                SpearEntity spearEntity = Objects.requireNonNull(SPEAR.getEntityType().create(world));
                spearEntity.setPos(position.getX(), position.getY(), position.getZ());
                stack.decrement(1);
                return spearEntity;
            }
        });
    }
}
