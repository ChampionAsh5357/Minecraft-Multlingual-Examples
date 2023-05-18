/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client.model

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.ashwork.mc.multilingualexamples.registrar.registerRenderers
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.Model
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack

/**
 * A manager used for handling armor models on any given entity.
 */
class ArmorModelManager {

    private val entityArmorModels: Table<EntityType<*>, ArmorMaterial, ModelHandler> = HashBasedTable.create()
    private val playerArmorModels: Table<String, ArmorMaterial, ModelHandler> = HashBasedTable.create()

    /**
     * Registers a single handler for an armor model for use with the
     * specified entity types.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param modelFactory a factory used to construct the model from its part
     * @param setup a method to copy the original model parts and set visibility
     * @param types the entity types to apply the armor to
     * @param T the type of the armor model
     */
    private inline fun <T: Model> registerArmorModel(material: ArmorMaterial, register: (ModelLayerLocation) -> Unit, noinline modelFactory: (ModelPart) -> T, noinline setup: (T, HumanoidModel<*>, EquipmentSlot) -> Unit, vararg types: EntityType<*>) =
        this.registerArmorModel(material, register, createSingleModel(material, modelFactory, setup), *types)

    /**
     * Registers an armor model handler for use with the specified entity
     * types.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param handler the armor model handler
     * @param types the entity types to apply the armor to
     */
    private inline fun registerArmorModel(material: ArmorMaterial, register: (ModelLayerLocation) -> Unit, handler: (ModelLayerLocation) -> ModelHandler, vararg types: EntityType<*>) {
        types.forEach {
            val mll = ModelLayerLocation(Registry.ENTITY_TYPE.getKey(it), material.name)
            register(mll)
            this.entityArmorModels.put(it, material, handler(mll))
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
     * @param T the type of the armor model
     */
    private inline fun <T: Model> registerPlayerArmorModel(material: ArmorMaterial, register: (ModelLayerLocation) -> Unit, noinline modelFactory: (ModelPart) -> T, noinline setup: (T, HumanoidModel<*>, EquipmentSlot) -> Unit, vararg types: String) =
        this.registerPlayerArmorModel(material, register, createSingleModel(material, modelFactory, setup), *types)

    /**
     * Registers an armor model handler for use with the specified player
     * models.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param handler the armor model handler
     * @param types the player models to apply the armor to
     */
    private inline fun registerPlayerArmorModel(material: ArmorMaterial, register: (ModelLayerLocation) -> Unit, handler: (ModelLayerLocation) -> ModelHandler, vararg types: String) {
        types.forEach {
            val mll = ModelLayerLocation(ResourceLocation("player_$it"), material.name)
            register(mll)
            this.playerArmorModels.put(it, material, handler(mll))
        }
    }

    /**
     * A method used to initialize the custom armor model handlers.
     */
    fun init() {
        // Register the renderer used for the custom armor models for each supported armor item
        val renderer = ArmorRenderer { poseStack, bufferSource, stack, entity, slot, light, context ->
            if (stack.item is ArmorItem) {
                val handler = this.getHandler((stack.item as ArmorItem).material, entity)
                ArmorRenderer.renderPart(poseStack, bufferSource, light, stack, handler.getAndSetup(entity, stack, slot, context), handler.getTexture(stack, entity, slot))
            }
        }
        registerRenderers { ArmorRenderer.register(renderer, it) }

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
        val basicCollageLayerDef: (ModelLayerLocation) -> Unit = {
            EntityModelLayerRegistry.registerModelLayer(it) {
                LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 0F), 64, 32)
            }
        }
        this.registerArmorModel(ExampleArmorMaterials.COLLAGE, basicCollageLayerDef, ::CollageModel, CollageModel::copyAndSet,
            EntityType.DROWNED, EntityType.GIANT, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.SKELETON,
            EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN)
        this.registerArmorModel(ExampleArmorMaterials.COLLAGE,
            {
                EntityModelLayerRegistry.registerModelLayer(it) { LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 1F, 0F, -1F, -1F), 64, 32) }
            }, ::CollageModel, CollageModel::copyAndSet, EntityType.ARMOR_STAND)
        this.registerPlayerArmorModel(ExampleArmorMaterials.COLLAGE, basicCollageLayerDef, ::CollageModel, CollageModel::copyAndSet,
            "default", "slim")
    }

    /**
     * A method used to reload to entity models.
     *
     * @param modelSet the reloaded model set
     */
    fun reloadModels(modelSet: EntityModelSet) {
        /*
        As the armor models are associated with a layer but are technically
        separate from them, we need to build our own baking and caching system.
        Entity models are currently not data driven, but it is likely they will
        be (and already is in some loaders/mods), so it is best to update them
        with the new models to render according to their rendered definitions.
         */
        this.playerArmorModels.values().forEach { it.constructModel(modelSet) }
        this.entityArmorModels.values().forEach { it.constructModel(modelSet) }
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
    private fun getHandler(material: ArmorMaterial, entity: Entity): ModelHandler {
        val handler = if (entity is AbstractClientPlayer) this.playerArmorModels.get(entity.modelName, material)
            else this.entityArmorModels.get(entity.type, material)

        return handler ?: this.playerArmorModels.get("default", material)!!
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
 * @param T the type of the armor model
 */
private inline fun <T: Model> createSingleModel(material: ArmorMaterial, crossinline modelFactory: (ModelPart) -> T, noinline setup: (T, HumanoidModel<*>, EquipmentSlot) -> Unit): (ModelLayerLocation) -> ModelHandler {
    val split = material.name.split(":")
    val texture = ResourceLocation("${split[0]}:textures/models/armor/${split[1]}.png")
    return { mll ->
        SingleModelHandler(texture, setup) {
            modelSet -> modelFactory(modelSet.bakeLayer(mll))
        }
    }
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
    fun constructModel(modelSet: EntityModelSet)

    /**
     * Sets up the parameters for properly rendering the model and returns
     * the model itself.
     *
     * @param entity the entity wearing the armor
     * @param stack the armor currently being worn
     * @param slot the slot the armor is in
     * @param context the context model
     * @return the model to be rendered
     */
    fun getAndSetup(entity: LivingEntity, stack: ItemStack, slot: EquipmentSlot, context: HumanoidModel<*>): Model

    /**
     * Gets the texture to apply to the model.
     *
     * @param stack the armor currently being worn
     * @param entity the entity wearing the armor
     * @param slot the slot the armor is in
     * @return the full path and extension of the texture
     */
    fun getTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot): ResourceLocation
}

/**
 * An armor handler for one armor model at a single deformation.
 *
 * @param T the type of the armor model
 * @param modelFactory a factory used to construct the model from its part
 * @param setup a method to copy the original model parts and set visibility
 * @param texture the texture of the armor model
 */
class SingleModelHandler<out T: Model>(private val texture: ResourceLocation, private val setup: (T, HumanoidModel<*>, EquipmentSlot) -> Unit,
                                       private val modelFactory: (EntityModelSet) -> T): ModelHandler {

    private lateinit var model: T

    override fun constructModel(modelSet: EntityModelSet) {
        this.model = modelFactory(modelSet)
    }

    override fun getAndSetup(entity: LivingEntity, stack: ItemStack, slot: EquipmentSlot, context: HumanoidModel<*>): Model {
        setup(this.model, context, slot)
        return this.model
    }

    override fun getTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot): ResourceLocation = this.texture
}
