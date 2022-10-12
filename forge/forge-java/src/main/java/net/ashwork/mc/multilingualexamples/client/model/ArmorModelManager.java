package net.ashwork.mc.multilingualexamples.client.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials;
import net.ashwork.mc.multilingualexamples.util.TriConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

//TODO: Document
public class ArmorModelManager {

    private final Table<EntityType<?>, ArmorMaterial, ModelHandler> entityArmorModels;
    private final Table<String, ArmorMaterial, ModelHandler> playerArmorModels;
    private final List<Consumer<BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>>>> definitions;

    public ArmorModelManager(IEventBus modBus) {
        this.entityArmorModels = HashBasedTable.create();
        this.playerArmorModels = HashBasedTable.create();
        this.definitions = new ArrayList<>();

        modBus.addListener(this::onRegisterLayerDefinitions);
        modBus.addListener(this::onAddLayers);

        this.init();
    }

    public void init() {
        Supplier<LayerDefinition> basicCollageLayerDef = () -> LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 0F), 64, 32);
        this.registerArmorModel(ExampleArmorMaterials.COLLAGE, basicCollageLayerDef, CollageModel::new, CollageModel::copyAndSet,
                EntityType.DROWNED, EntityType.GIANT, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.SKELETON,
                EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN);
        this.registerArmorModel(ExampleArmorMaterials.COLLAGE,
                () -> LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 1F, 0F, -1F, -1F), 64, 32),
                CollageModel::new, CollageModel::copyAndSet, EntityType.ARMOR_STAND);
        this.registerPlayerArmorModel(ExampleArmorMaterials.COLLAGE, basicCollageLayerDef, CollageModel::new, CollageModel::copyAndSet,
                "default", "slim");
    }

    private <T extends Model> void registerArmorModel(ArmorMaterial material, Supplier<LayerDefinition> definition, Function<ModelPart, T> modelFactory, TriConsumer<T, HumanoidModel<?>, EquipmentSlot> setup, EntityType<?>... types) {
        var split = material.getName().split(":");
        var texture = split[0] + ":textures/models/armor/" + split[1] + ".png";
        for (EntityType<?> type : types) {
            var mll = new ModelLayerLocation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(type)), material.getName());
            this.definitions.add(method -> method.accept(mll, definition));
            this.entityArmorModels.put(type, material, new SingleModelHandler<>(entityModelSet -> modelFactory.apply(entityModelSet.bakeLayer(mll)), setup, texture));
        }
    }

    private <T extends Model> void registerPlayerArmorModel(ArmorMaterial material, Supplier<LayerDefinition> definition, Function<ModelPart, T> modelFactory, TriConsumer<T, HumanoidModel<?>, EquipmentSlot> setup, String... types) {
        var split = material.getName().split(":");
        var texture = split[0] + ":textures/models/armor/" + split[1] + ".png";
        for (String str : types) {
            var mll = new ModelLayerLocation(new ResourceLocation("player_" + str), material.getName());
            this.definitions.add(method -> method.accept(mll, definition));
            this.playerArmorModels.put(str, material, new SingleModelHandler<>(entityModelSet -> modelFactory.apply(entityModelSet.bakeLayer(mll)), setup, texture));
        }
    }

    private void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        this.definitions.forEach(method -> method.accept(event::registerLayerDefinition));
    }

    private void onAddLayers(EntityRenderersEvent.AddLayers event) {
        this.playerArmorModels.values().forEach(handler -> handler.constructModel(event.getEntityModels()));
        this.entityArmorModels.values().forEach(handler -> handler.constructModel(event.getEntityModels()));
    }

    private ModelHandler getHandler(ArmorMaterial material, Entity entity) {
        ModelHandler handler;
        if (entity instanceof AbstractClientPlayer player) handler = this.playerArmorModels.get(player.getModelName(), material);
        else handler = this.entityArmorModels.get(entity.getType(), material);

        if (handler == null) handler = this.playerArmorModels.get("default", material);

        return Objects.requireNonNull(handler);
    }

    public Model getArmorModel(ArmorMaterial material, LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
        return this.getHandler(material, entity).getAndSetup(entity, stack, slot, original);
    }

    public String getTexture(ArmorMaterial material, ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return this.getHandler(material, entity).getTexture(stack, entity, slot, type);
    }

    public interface ModelHandler {

        void constructModel(EntityModelSet modelSet);

        Model getAndSetup(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original);

        String getTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type);
    }

    public static class SingleModelHandler<T extends Model> implements ModelHandler {

        private final Function<EntityModelSet, T> modelFactory;
        private final TriConsumer<T, HumanoidModel<?>, EquipmentSlot> setup;
        private final String texture;
        private T model;

        public SingleModelHandler(Function<EntityModelSet, T> modelFactory, TriConsumer<T, HumanoidModel<?>, EquipmentSlot> setup, String texture) {
            this.modelFactory = modelFactory;
            this.setup = setup;
            this.texture = texture;
        }

        @Override
        public void constructModel(EntityModelSet modelSet) {
            this.model = modelFactory.apply(modelSet);
        }

        @Override
        public Model getAndSetup(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
            setup.accept(this.model, original, slot);
            return this.model;
        }

        @Override
        public String getTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return this.texture;
        }
    }
}
