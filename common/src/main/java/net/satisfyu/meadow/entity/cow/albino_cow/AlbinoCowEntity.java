package net.satisfyu.meadow.entity.cow.albino_cow;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.satisfyu.meadow.registry.EntityRegistry;
import net.satisfyu.meadow.registry.ObjectRegistry;


public class AlbinoCowEntity extends CowEntity {
    public AlbinoCowEntity(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public CowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityRegistry.ALBINO_COW.get().create(serverWorld);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isDay() && this.world.isSkyVisible(this.getBlockPos()) && !this.isBaby()) {
            this.damage(DamageSource.ON_FIRE, 1.0f);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl;
        if (((bl = itemStack.isOf(Items.BUCKET)) || itemStack.isOf(ObjectRegistry.WOODEN_BUCKET.get())) && !this.isBaby()) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, bl ? Items.MILK_BUCKET.getDefaultStack() : ObjectRegistry.WOODEN_MILK_BUCKET.get().getDefaultStack());
            player.setStackInHand(hand, itemStack2);
            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }
}


