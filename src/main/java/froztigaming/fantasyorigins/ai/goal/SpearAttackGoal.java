package froztigaming.fantasyorigins.ai.goal;

import froztigaming.fantasyorigins.entities.mobs.GoblinEntity;
import froztigaming.fantasyorigins.init.ItemInit;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class SpearAttackGoal  extends ProjectileAttackGoal {
    private final GoblinEntity goblin;

    public SpearAttackGoal(RangedAttackMob rangedAttackMob, double d, int i, float f) {
        super(rangedAttackMob, d, i, f);
        this.goblin = ((GoblinEntity) rangedAttackMob);
    }

    @Override
    public boolean canStart() {
        return super.canStart() && (goblin.getMainHandStack().isOf(ItemInit.SPEAR) || goblin.getMainHandStack().isOf(ItemInit.TRITON_TRIDENT) || goblin.getMainHandStack().isOf(Items.TRIDENT));
    }

    @Override
    public void start() {
        super.start();
        this.goblin.setAttacking(true);
        this.goblin.setCurrentHand(Hand.MAIN_HAND);
    }

    @Override
    public void stop() {
        super.stop();
        this.goblin.clearActiveItem();
        this.goblin.setAttacking(false);
    }
}
