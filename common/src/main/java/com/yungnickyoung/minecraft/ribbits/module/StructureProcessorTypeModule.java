package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.world.processor.*;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.mojang.serialization.MapCodec;

/** 26.2: processors are registered as their MapCodec (YUNG's API port routes MapCodec fields to STRUCTURE_PROCESSOR). */
@AutoRegister(RibbitsCommon.MOD_ID)
public class StructureProcessorTypeModule {
    @AutoRegister("pillar_processor")
    public static MapCodec<PillarProcessor> PILLAR_PROCESSOR = PillarProcessor.CODEC;

    @AutoRegister("podzol_processor")
    public static MapCodec<PodzolProcessor> PODZOL_PROCESSOR = PodzolProcessor.CODEC;

    @AutoRegister("warped_nylium_processor")
    public static MapCodec<WarpedNyliumProcessor> WARPED_NYLIUM_PROCESSOR = WarpedNyliumProcessor.CODEC;

    @AutoRegister("block_replace_processor")
    public static MapCodec<BlockReplaceProcessor> BLOCK_REPLACE_PROCESSOR = BlockReplaceProcessor.CODEC;

    @AutoRegister("lapis_block_processor")
    public static MapCodec<LapisBlockProcessor> LAPIS_BLOCK_PROCESSOR = LapisBlockProcessor.CODEC;

    @AutoRegister("brewing_stand_processor")
    public static MapCodec<BrewingStandProcessor> BREWING_STAND_PROCESSOR = BrewingStandProcessor.CODEC;
}
