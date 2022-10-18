/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client.model

import com.google.common.collect.{HashBasedTable, Table}
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.ashwork.mc.multilingualexamples.registrar.ItemRegistrar
import net.fabricmc.fabric.api.client.rendering.v1.{ArmorRenderer, EntityModelLayerRegistry}
import net.minecraft.client.model.geom.builders.{CubeDeformation, LayerDefinition}
import net.minecraft.client.model.{HumanoidModel, Model}
import net.minecraft.client.model.geom.{EntityModelSet, ModelLayerLocation, ModelPart}
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.{Entity, EntityType, EquipmentSlot, LivingEntity}
import net.minecraft.world.item.{ArmorItem, ArmorMaterial, ArmorMaterials, ItemStack}

import scala.Option
import scala.jdk.javaapi.CollectionConverters

/**
 * A manager used for handling armor models on any given entity.
 */
class ArmorModelManager {

    private val entityArmorModels: Table[EntityType[_], ArmorMaterial, ModelHandler] = HashBasedTable.create()
    private val playerArmorModels: Table[String, ArmorMaterial, ModelHandler] = HashBasedTable.create()

    /**
     * Registers a single handler for an armor model for use with the
     * specified entity types.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param modelFactory a factory used to construct the model from its part
     * @param setup a method to copy the original model parts and set visibility
     * @param types the entity types to apply the armor to
     * @tparam T the type of the armor model
     */
    private inline def registerArmorModel[T <: Model](material: ArmorMaterial, register: ModelLayerLocation => Unit, modelFactory: ModelPart => T, setup: (T, HumanoidModel[_], EquipmentSlot) => Unit, types: EntityType[_]*): Unit =
        registerArmorModelHandler(material, register, ArmorModelManager.createSingleModel(material, modelFactory, setup), types:_*)

    /**
     * Registers an armor model handler for use with the specified entity
     * types.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param handler the armor model handler
     * @param types the entity types to apply the armor to
     */
    private inline def registerArmorModelHandler(material: ArmorMaterial, register: ModelLayerLocation => Unit, handler: ModelLayerLocation => ModelHandler, types: EntityType[_]*): Unit = {
        for (`type` <- types) {
            val mll = ModelLayerLocation(Registry.ENTITY_TYPE.getKey(`type`), material.getName)
            register(mll)
            entityArmorModels.put(`type`, material, handler(mll))
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
     * @tparam T the type of the armor model
     */
    private inline def registerPlayerArmorModel[T <: Model](material: ArmorMaterial, register: ModelLayerLocation => Unit, modelFactory: ModelPart => T, setup: (T, HumanoidModel[_], EquipmentSlot) => Unit, types: String*): Unit =
        registerPlayerArmorModelHandler(material, register, ArmorModelManager.createSingleModel(material, modelFactory, setup), types:_*)

    /**
     * Registers an armor model handler for use with the specified player
     * models.
     *
     * @param material the material of the armor model
     * @param register the register method of the layer definition
     * @param handler the armor model handler
     * @param types the player models to apply the armor to
     */
    private inline def registerPlayerArmorModelHandler(material: ArmorMaterial, register: ModelLayerLocation => Unit, handler: ModelLayerLocation => ModelHandler, types: String*): Unit = {
        for (`type` <- types) {
            val mll = ModelLayerLocation(ResourceLocation(s"player_${`type`}"), material.getName)
            register(mll)
            playerArmorModels.put(`type`, material, handler(mll))
        }
    }

    /**
     * A method used to initialize the custom armor model handlers.
     */
    def init(): Unit = {
        val renderer: ArmorRenderer = (poseStack, bufferSource, stack, entity, slot, light, context) => {
            stack.getItem match {
                case item: ArmorItem =>
                    val handler = getHandler(item.getMaterial, entity)
                    ArmorRenderer.renderPart(poseStack, bufferSource, light, stack, handler.getAndSetup(entity, stack, slot, context), handler.getTexture(stack, entity, slot))
                case _ =>
            }
        }
        ItemRegistrar.CUSTOM_ARMOR_MODEL_ITEMS.foreach(ArmorRenderer.register(renderer, _))

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
        val basicCollageLayerDef: ModelLayerLocation => Unit = {
            EntityModelLayerRegistry.registerModelLayer(_, () => LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 0F), 64, 32))
        }
        val armor: ArmorMaterial = ExampleArmorMaterials.COLLAGE
        registerArmorModel(armor, basicCollageLayerDef, CollageModel(_), _.copyAndSet(_, _),
            EntityType.DROWNED, EntityType.GIANT, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.SKELETON,
            EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN)
        registerArmorModel(armor,
            EntityModelLayerRegistry.registerModelLayer(_, () => LayerDefinition.create(CollageModel.createMesh(CubeDeformation.NONE, 1F, 0F, -1F, -1F), 64, 32)),
                CollageModel(_), _.copyAndSet(_, _), EntityType.ARMOR_STAND)
        registerPlayerArmorModel(armor, basicCollageLayerDef, CollageModel(_), _.copyAndSet(_, _),
            "default", "slim")
    }

    /**
     * A method used to reload to entity models.
     *
     * @param modelSet the reloaded model set
     */
    def reloadModels(modelSet: EntityModelSet): Unit =  {
        /*
        As the armor models are associated with a layer but are technically
        separate from them, we need to build our own baking and caching system.
        Entity models are currently not data driven, but it is likely they will
        be (and already is in some loaders/mods), so it is best to update them
        with the new models to render according to their rendered definitions.
         */
        CollectionConverters.asScala(playerArmorModels.values()).foreach { _.constructModel(modelSet) }
        CollectionConverters.asScala(entityArmorModels.values()).foreach { _.constructModel(modelSet) }
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
    private def getHandler(material: ArmorMaterial, entity: Entity): ModelHandler = {
        val handler = Option(entity match {
            case p: AbstractClientPlayer => playerArmorModels.get(p.getModelName, material)
            case _ => entityArmorModels.get(entity.getType, material)
        })

        handler match {
            case Some(a) => a
            case None => playerArmorModels.get("default", material)
        }
    }
}

/**
 * The global instance for independent methods.
 */
object ArmorModelManager {

    /**
     * Constructs a handler for armor models with only one model. Assumes the
     * texture data to be in one file.
     *
     * @param material the material of the armor model
     * @param modelFactory a factory used to construct the model from its part
     * @param setup a method to copy the original model parts and set visibility
     * @return the handler
     * @tparam T the type of the armor model
     */
    private inline def createSingleModel[T <: Model](material: ArmorMaterial, modelFactory: ModelPart => T, setup: (T, HumanoidModel[_], EquipmentSlot) => Unit): ModelLayerLocation => ModelHandler = {
        val split = material.getName.split(":")
        val texture = ResourceLocation(s"${split(0)}:textures/models/armor/${split(1)}.png")
        mll => {
            SingleModelHandler(texture, setup, modelSet => modelFactory(modelSet.bakeLayer(mll)))
        }
    }
}

/**
 * A handler for managing models not attached to any entity renderer.
 */
trait ModelHandler {

    /**
     * Constructs the model anytime the assets are reloaded.
     *
     * @param modelSet the model set containing the model definitions
     */
    def constructModel(modelSet: EntityModelSet): Unit

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
    def getAndSetup(entity: LivingEntity, stack: ItemStack, slot: EquipmentSlot, context: HumanoidModel[_]): Model

    /**
     * Gets the texture to apply to the model.
     *
     * @param stack the armor currently being worn
     * @param entity the entity wearing the armor
     * @param slot the slot the armor is in
     * @return the full path and extension of the texture
     */
    def getTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot): ResourceLocation
}

/**
 * An armor handler for one armor model at a single deformation.
 *
 * @tparam T the type of the armor model
 * @param modelFactory a factory used to construct the model from its part
 * @param setup a method to copy the original model parts and set visibility
 * @param texture the texture of the armor model
 */
class SingleModelHandler[T <: Model](private val texture: ResourceLocation, private val setup: (T, HumanoidModel[_], EquipmentSlot) => Unit,
                                     private val modelFactory: EntityModelSet => T) extends ModelHandler {

    private var model: Option[T] = None

    override def constructModel(modelSet: EntityModelSet): Unit = model = Some(modelFactory(modelSet))

    override def getAndSetup(entity: LivingEntity, stack: ItemStack, slot: EquipmentSlot, context: HumanoidModel[_]): Model = {
        val _model = model.get
        setup(_model, context, slot)
        _model
    }

    override def getTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot): ResourceLocation = this.texture
}
