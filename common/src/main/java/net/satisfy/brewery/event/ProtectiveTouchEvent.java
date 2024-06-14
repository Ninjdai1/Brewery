package net.satisfy.brewery.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.satisfy.brewery.registry.MobEffectRegistry;
import org.jetbrains.annotations.Nullable;

public class ProtectiveTouchEvent implements PlayerEvent.AttackEntity {
    @Override
    public EventResult attack(Player player, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        if (player.hasEffect(MobEffectRegistry.PROTECTIVETOUCH.get())) {
            if (target instanceof LivingEntity livingTarget) {
                MobEffectInstance currentEffect = livingTarget.getEffect(MobEffects.ABSORPTION);
                if (currentEffect == null || currentEffect.getDuration() < 200) {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 1));
                    level.addParticle(ParticleTypes.GLOW_SQUID_INK, target.getX(), target.getY() + target.getBbHeight() / 2.5, target.getZ(), 0, 0, 0);
                    level.addParticle(ParticleTypes.GLOW_SQUID_INK, target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 0, 0, 0);
                    level.addParticle(ParticleTypes.GLOW_SQUID_INK, target.getX(), target.getY() + target.getBbHeight() / 1.5, target.getZ(), 0, 0, 0);

                    return EventResult.interruptFalse();
                }
            }
        }

        return EventResult.pass();
    }
}
