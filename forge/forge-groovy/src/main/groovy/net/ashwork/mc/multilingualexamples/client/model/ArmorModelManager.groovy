/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client.model

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import groovy.transform.CompileStatic
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.Model
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.ForgeRegistries
/**
 * A manager used for handling armor models on any given entity.
 */
@CompileStatic
class ArmorModelManager {

    private final Table<EntityType<? extends Entity>, ArmorMaterial, ModelHandler> entityArmorModels
    private final Table<String, ArmorMaterial, ModelHandler> playerArmorModels

    /**
     * Default constructor.
     *
     * @param modBus the mod's event bus
     */
    ArmorModelManager(IEventBus modBus) {
        this.entityArmorModels = HashBasedTable.create()
        this.playerArmorModels = HashBasedTable.create()

        modBus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.RegisterLayerDefinitions, this::onRegisterLayerDefinitions)
        modBus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.AddLayers, this::onAddLayers)
    }

    /**
     * Registers a single handler for an armor model for use with the
     * specified entity types.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param modelFactory a factory used to construct the model from its part
     * @param setup a method to copy the original model parts and set visibility
     * @param types the entity types to apply the armor to
     * @param <T> the type of the armor model
     */
    private <T extends Model> void registerArmorModel(ArmorMaterial material, Closure<?> register, Closure<T> modelFactory, Closure<?> setup, EntityType<? extends Entity>... types) {
        this.registerArmorModel(material, register, createSingleModel(material, modelFactory, setup), types)
    }

    /**
     * Registers an armor model handler for use with the specified entity
     * types.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param handler the armor model handler
     * @param types the entity types to apply the armor to
     */
    private void registerArmorModel(ArmorMaterial material, Closure<?> register, Closure<ModelHandler> handler, EntityType<? extends Entity>... types) {
        for (EntityType<? extends Entity> type : types) {
            var mll = new ModelLayerLocation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(type)), material.getName())
            register(mll)
            this.entityArmorModels.put(type, material, handler(mll))
        }
    }

    /**
     * Registers a single handler for an armor model for use with the
     * specified player models.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param modelFactory a factory used to construct the model from its part
     * @param setup a method to copy the original model parts and set visibility
     * @param types the player models to apply the armor to
     * @param <T> the type of the armor model
     */
    private <T extends Model> void registerPlayerArmorModel(ArmorMaterial material, Closure<?> register, Closure<T> modelFactory, Closure<?> setup, String... types) {
        this.registerPlayerArmorModel(material, register, createSingleModel(material, modelFactory, setup), types)
    }

    /**
     * Registers an armor model handler for use with the specified player
     * models.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param handler the armor model handler
     * @param types the player models to apply the armor to
     */
    private void registerPlayerArmorModel(ArmorMaterial material, Closure<?> register, Closure<ModelHandler> handler, String... types) {
        for (String type : types) {
            var mll = new ModelLayerLocation(new ResourceLocation("player_$type"), material.getName())
            register(mll)
            this.playerArmorModels.put(type, material, handler(mll))
        }
    }

    /**
     * Constructs a handler for armor models with only one model. Assumes the
     * texture data to be in one file.
     *
     * @param material the material of the armor model
     * @param modelFactory a factory used to construct the model from its part
     * @param setup a method to copy the original model parts and set visibility
     * @return the handler
     * @param <T> the type of the armor model
     */
    private static <T extends Model> Closure<ModelHandler> createSingleModel(ArmorMaterial material, Closure<T> modelFactory, Closure<?> setup) {
        var split = material.getName().split(':')
        var texture = "${split[0]}:textures/models/armor/${split[1]}.png"
        return { ModelLayerLocation mll -> new SingleModelHandler<>({ EntityModelSet entityModelSet -> modelFactory(entityModelSet.bakeLayer(mll)) }, setup, texture) }
    }

    /**
     * An event used to register layer definitions.
     *
     * @param event an event instance
     */
    private void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        /*
        This registers the definitions that allow us to create the models for
        each entity. We could clear the map as currently the method is only
        called once. However, it is likely that this will be data driven
        in the future (and already is in some loaders/mods), so it is best to
        let this remain such that it can be called multiple times.
         */

        /*
        There are four categories of armor models to which the following
        entity types are coagulated into. You should test your armor model
        on at least one from each group to make sure everything is properly
        sized.

        If you only have one, non-changing model, you can typically get away
        with testing only three groups, merging the two humanoids into one
        group.

        - Outer Armor: Uses humanoid armor model with an inflation of 1

        Armor Stand (Outer Armor)*
        Husk (Outer Armor)
        Giant (Outer Armor)
        Player: Steve (Outer Armor)
        Player: Alex (Outer Armor)
        Skeleton (Outer Armor)
        Stray (Outer Armor)
        Wither Skeleton (Outer Armor)
        Zombie (Outer Armor)

        - Inner Armor: Uses humanoid armor model with an inflation of 0.5

        Armor Stand (Inner Armor)*
        Drowned (Inner Armor)
        Drowned (Outer Armor)
        Giant (Inner Armor)
        Husk (Inner Armor)
        Piglin (Inner Armor)
        Piglin Brute (Inner Armor)
        Player: Steve (Inner Armor)
        Player: Alex (Inner Armor)
        Skeleton (Inner Armor)
        Stray (Inner Armor)
        Wither Skeleton (Inner Armor)
        Zombie (Inner Armor)
        Zombified Piglin (Inner Armor)

        - Piglin Armor: Uses humanoid armor model with an inflation of 1.02

        Piglin (Outer Armor)
        Piglin Brute (Outer Armor)
        Zombified Piglin (Outer Armor)

        - Villager Armor: Uses zombie villager model with the humanoid armor inflations

        Zombie Villager (Inner Armor)
        Zombie Villager (Outer Armor)

        * The armor stand uses its own model; however, it only changes the part
        transformations, which is copied to our own parts, so these can be
        treated as normal models.
         */

        /*
        As this is called after registration, we can refer to the raw entity
        type here without issue.
         */

        // Collage model
        Closure<?> basicCollageLayerDef = { ModelLayerLocation mll ->
            event.registerLayerDefinition(mll, () -> LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 0F), 64, 32))
        }
        Closure<CollageModel> collageFactory = { ModelPart part -> new CollageModel(part) }
        Closure<?> collageCopyAndSet = { CollageModel model, HumanoidModel<? extends LivingEntity> original, EquipmentSlot slot -> model.copyAndSet(original, slot) }
        this.registerArmorModel(ExampleArmorMaterials.COLLAGE, basicCollageLayerDef, collageFactory, collageCopyAndSet,
                EntityType.DROWNED, EntityType.GIANT, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.SKELETON,
                EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN)
        this.registerArmorModel(ExampleArmorMaterials.COLLAGE,
                { ModelLayerLocation mll -> event.registerLayerDefinition(mll, () -> LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 1F, 0F, -1F, -1F), 64, 32)) },
                collageFactory, collageCopyAndSet, EntityType.ARMOR_STAND)
        this.registerPlayerArmorModel(ExampleArmorMaterials.COLLAGE, basicCollageLayerDef, collageFactory, collageCopyAndSet,
                'default', 'slim')
    }

    /**
     * An event used to add layers to entity renderers.
     *
     * @param event an event instance
     */
    private void onAddLayers(EntityRenderersEvent.AddLayers event) {
        /*
        As the armor models are associated with a layer but are technically
        separate from them, we need to build our own baking and caching system.
        Entity models are currently not data driven, but it is likely they will
        be (and already is in some loaders/mods), so it is best to update them
        with the new models to render according to their rendered definitions.
         */
        this.playerArmorModels.values().forEach() {it.constructModel(event.getEntityModels()) }
        this.entityArmorModels.values().forEach() {it.constructModel(event.getEntityModels()) }
    }

    /**
     * Gets the model handler for the associated entity. If none is available,
     * will default to the default player model, or throw an exception if
     * not present.
     *
     * @param material the material of the armor model
     * @param entity the entity wearing the armor
     * @return the model handler
     * @throws NullPointerException if there is no default player armor model handler
     */
    private ModelHandler getHandler(ArmorMaterial material, Entity entity) {
        ModelHandler handler = entity instanceof AbstractClientPlayer ? this.playerArmorModels.get(((AbstractClientPlayer) entity).getModelName(), material)
                : this.entityArmorModels.get(entity.getType(), material)

        return handler ?: this.playerArmorModels.get('default', material)
    }

    /**
     * Gets the model to apply for the armor model with the correct
     * transformations.
     *
     * @param material the material of the armor model
     * @param entity the entity wearing the armor
     * @param stack the armor currently being worn
     * @param slot the slot the armor is in
     * @param original the original armor model to render
     * @return the model to be rendered
     */
    Model getArmorModel(ArmorMaterial material, LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<? extends LivingEntity> original) {
        return this.getHandler(material, entity).getAndSetup(entity, stack, slot, original)
    }

    /**
     * Gets the texture to apply to the model.
     *
     * @param material the material of the armor model
     * @param stack the armor currently being worn
     * @param entity the entity wearing the armor
     * @param slot the slot the armor is in
     * @param type the subtype of the model, either {@code null} or 'overlay' when dyeable
     * @return the full path and extension of the texture
     */
    String getTexture(ArmorMaterial material, ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return this.getHandler(material, entity).getTexture(stack, entity, slot, type)
    }

    /**
     * A handler for managing models not attached to any entity renderer.
     */
    interface ModelHandler {

        /**
         * Constructs the model anytime the assets are reloaded.
         *
         * @param modelSet the model set containing the model definitions
         */
        void constructModel(EntityModelSet modelSet)

        /**
         * Sets up the parameters for properly rendering the model and returns
         * the model itself.
         *
         * @param entity the entity wearing the armor
         * @param stack the armor currently being worn
         * @param slot the slot the armor is in
         * @param original the original armor model to render
         * @return the model to be rendered
         */
        Model getAndSetup(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<? extends LivingEntity> original)

        /**
         * Gets the texture to apply to the model.
         *
         * @param stack the armor currently being worn
         * @param entity the entity wearing the armor
         * @param slot the slot the armor is in
         * @param type the subtype of the model, either {@code null} or 'overlay' when dyeable
         * @return the full path and extension of the texture
         */
        String getTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
    }

    /**
     * An armor handler for one armor model at a single deformation.
     *
     * @param <T> the type of the armor model
     */
    static class SingleModelHandler<T extends Model> implements ModelHandler {

        private final Closure<T> modelFactory
        private final Closure<?> setup
        private final String texture
        private T model

        /**
         * Default constructor.
         *
         * @param modelFactory a factory used to construct the model from its part
         * @param setup a method to copy the original model parts and set visibility
         * @param texture the texture of the armor model
         */
        SingleModelHandler(Closure<T> modelFactory, Closure<?> setup, String texture) {
            this.modelFactory = modelFactory
            this.setup = setup
            this.texture = texture
        }

        @Override
        void constructModel(EntityModelSet modelSet) {
            this.model = modelFactory(modelSet)
        }

        @Override
        Model getAndSetup(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<? extends LivingEntity> original) {
            setup(this.model, original, slot)
            return this.model
        }

        @Override
        String getTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return this.texture
        }
    }
}
