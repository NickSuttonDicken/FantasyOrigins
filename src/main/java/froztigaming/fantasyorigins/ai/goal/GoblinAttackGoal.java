package froztigaming.fantasyorigins.ai.goal;

import froztigaming.fantasyorigins.entities.mobs.GoblinEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class GoblinAttackGoal extends MeleeAttackGoal {
    private final GoblinEntity goblin;
    private  int ticks;

    public GoblinAttackGoal(GoblinEntity goblin, double speed, boolean pauseWhenMobIdle) {
        super(goblin, speed, pauseWhenMobIdle);
        this.goblin = goblin;
    }

    @Override
    public void start() {
        super.start();
        this.ticks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.goblin.setAttacking(false);
    }

    @Override
    public void tick() {
        super.tick();
        ++this.ticks;
        if (this.ticks >= 4 && this.getCooldown() < this.getMaxCooldown() / 2) {
            this.goblin.setAttacking(true);
        } else {
            this.goblin.setAttacking(false);
        }
    }
}
