package com.yungnickyoung.minecraft.ribbits.mixin.mixins.accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** 26.2: FireBlock.setFlammable is private and NeoForge no longer widens it. */
@Mixin(FireBlock.class)
public interface FireBlockAccessor {
    @Invoker("setFlammable")
    void ribbits$setFlammable(Block block, int igniteOdds, int burnOdds);
}
