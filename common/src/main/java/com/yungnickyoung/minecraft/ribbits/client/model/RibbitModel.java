package com.yungnickyoung.minecraft.ribbits.client.model;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.data.RibbitData;
import com.yungnickyoung.minecraft.ribbits.data.RibbitInstrument;
import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import com.yungnickyoung.minecraft.ribbits.module.DataTicketModule;
import com.yungnickyoung.minecraft.ribbits.module.RibbitInstrumentModule;
import com.yungnickyoung.minecraft.ribbits.module.RibbitProfessionModule;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RibbitModel extends GeoModel<RibbitEntity> {
    private static final Identifier TEXTURE = RibbitsCommon.id("textures/entity/ribbit.png");
    private static final Identifier ANIMATIONS = RibbitsCommon.id("ribbit");
    private static final Map<Object, Set<String>> PROFESSION_BONES = Map.of(
            RibbitProfessionModule.GARDENER, Set.of("watering_can", "gardener_hat"),
            RibbitProfessionModule.SORCERER, Set.of("sourcerer_hat"),
            RibbitProfessionModule.FISHERMAN, Set.of("accessories", "fishing_rod", "fishing_rod_2", "fishing_rod_3"),
            RibbitProfessionModule.MERCHANT, Set.of("leaf")
    );

    private static final Map<RibbitInstrument, String> INSTRUMENT_BONES = Map.of(
            RibbitInstrumentModule.BASS, "bass",
            RibbitInstrumentModule.BONGO, "bongo",
            RibbitInstrumentModule.FLUTE, "flute",
            RibbitInstrumentModule.GUITAR, "guitar"
    );

    private static final Map<String, String> UMBRELLA_BONES = Map.of(
            "1", "umbrella",
            "2", "umbrella2",
            "3", "umbrella3"
    );

    private static final Map<String, String> FISHERMAN_UMBRELLA_BONES = Map.of(
            "1", "fisherman_umbrella",
            "2", "fisherman_umbrella2",
            "3", "fisherman_umbrella3"
    );

    private static final Set<String> ALL_DYNAMIC_BONES = Set.of(
            "gardener_hat", "sourcerer_hat", "leaf", "watering_can",
            "accessories", "fishing_rod", "fishing_rod_2", "fishing_rod_3",
            "guitar", "flute", "bongo", "bass",
            "umbrella", "umbrella2", "umbrella3",
            "fisherman_umbrella", "fisherman_umbrella2", "fisherman_umbrella3",
            "pride"
    );

    private static final Set<String> TOGGLED_BONES = Stream.concat(
            Stream.of("body_default", "body_merchant"), ALL_DYNAMIC_BONES.stream()).collect(Collectors.toUnmodifiableSet());

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return RibbitsCommon.id("ribbit_master");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(RibbitEntity animatable) {
        return ANIMATIONS;
    }

    /**
     * The bones that should be visible for this render state (GeckoLib 5.5: applied per render pass through bone
     * snapshots by {@link com.yungnickyoung.minecraft.ribbits.client.render.RibbitRenderer}, instead of toggling the
     * shared model's bones in setCustomAnimations).
     */
    public static Set<String> visibleBones(GeoRenderState state) {
        RibbitData data = state.getGeckolibData(DataTicketModule.DT_RIBBIT_DATA);
        boolean playingInstrument = Boolean.TRUE.equals(state.getGeckolibData(DataTicketModule.DT_PLAYING_INSTRUMENT));
        boolean umbrellaFalling = Boolean.TRUE.equals(state.getGeckolibData(DataTicketModule.DT_UMBRELLA_FALLING));
        boolean inRain = Boolean.TRUE.equals(state.getGeckolibData(DataTicketModule.DT_IN_RAIN));
        boolean isPride = Boolean.TRUE.equals(state.getGeckolibData(DataTicketModule.DT_IS_PRIDE_RIBBIT));

        Set<String> desired = new HashSet<>(8);
        if (data == null) {
            desired.add("body_default");
            return desired;
        }

        if (data.getProfession().equals(RibbitProfessionModule.MERCHANT)) {
            desired.add("body_merchant");
        } else {
            desired.add("body_default");
        }

        if (playingInstrument && data.getInstrument() != RibbitInstrumentModule.NONE) {
            String inst = INSTRUMENT_BONES.get(data.getInstrument());
            if (inst != null) desired.add(inst);
        } else {
            desired.addAll(PROFESSION_BONES.getOrDefault(data.getProfession(), Set.of()));

            if (isPride) {
                desired.add("pride");

            } else if (umbrellaFalling || inRain) {
                String suffix = data.getUmbrellaType().modelLocationSuffix();
                Map<String, String> currentUmbrellaMap =
                        RibbitProfessionModule.FISHERMAN.equals(data.getProfession())
                                ? FISHERMAN_UMBRELLA_BONES
                                : UMBRELLA_BONES;

                currentUmbrellaMap.forEach((k, v) -> {
                    if (suffix.contains(k)) desired.add(v);
                });
            }
        }
        return desired;
    }

    /** Every bone whose visibility {@link #visibleBones} decides. */
    public static Set<String> toggledBones() {
        return TOGGLED_BONES;
    }
}
