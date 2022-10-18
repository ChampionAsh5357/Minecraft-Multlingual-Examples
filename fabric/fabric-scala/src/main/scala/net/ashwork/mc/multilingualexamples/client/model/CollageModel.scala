/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client.model

import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials
import net.minecraft.client.model.geom.builders.{CubeDeformation, CubeListBuilder, MeshDefinition}
import net.minecraft.client.model.geom.{ModelPart, PartPose}
import net.minecraft.client.model.{AgeableListModel, HumanoidModel}
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.{EquipmentSlot, LivingEntity}

import java.lang
import scala.jdk.javaapi.{CollectionConverters, FunctionConverters}

/**
 * The armor model class for [[ExampleArmorMaterials.COLLAGE]].
 *
 * <p>As armor models are provided through the [[HumanoidArmorLayer]], it
 * is highly recommended to use [[AgeableListModel]] as the lowest bound.
 * It has basic logic for scaling to baby forms of entities, so it is an ease
 * of convenience.
 *
 * @param root the part representing the base of the model
 * @param renderType the render type to use when uploading the model to the buffer
 */
class CollageModel(root: ModelPart, renderType: ResourceLocation => RenderType) extends
    AgeableListModel[LivingEntity](FunctionConverters.asJavaFunction(renderType), true, 16F, 0F, 2F, 2F, 24F) {

    /**
     * Default constructor. Uses the no cull version of the entity cutout
     * render type (binary alpha, armor never gets skipped during rendering).
     *
     * @param root the part representing the base of the model
     */
    def this(root: ModelPart) = this(root, RenderType.entityCutoutNoCull)

    /*
	Head elements, no need to get children as they are all visible in the
	same equipment slot.
    */
    private val head: ModelPart = root.getChild("head")

    /*
	Body elements, need children as rocket and belt are visible in different
	equipment slots.
    */
    private val body: ModelPart = root.getChild("body")
    private val rocket: ModelPart = this.body.getChild("rocket")
    private val bodyBelt: ModelPart = this.body.getChild("belt")

    /*
	Right leg elements, need children as belt and ankle guard are visible in
	different equipment slots.
	*/
    private val rightLeg: ModelPart = root.getChild("right_leg")
    private val rightLegBelt: ModelPart = this.rightLeg.getChild("right_leg_belt")
    private val rightAnkle: ModelPart = this.rightLeg.getChild("right_ankle")

    /*
	Left leg elements, need children as belt and ankle guard are visible in
	different equipment slots.
	*/
    private val leftLeg: ModelPart = root.getChild("left_leg")
    private val leftLegBelt: ModelPart = this.leftLeg.getChild("left_leg_belt")
    private val leftAnkle: ModelPart = this.leftLeg.getChild("left_ankle")

    /**
     * Copies the poses from the original armor model and sets the visibility
     * of each layer for rendering.
     *
     * @param original the original armor model
     * @param slot the slot being rendered
     */
    def copyAndSet(original: HumanoidModel[_], slot: EquipmentSlot): Unit = {
        /*
		These can be set using '#copyPropertiesTo' if they were both humanoid
		models. Since they are not, we have to do this the manual way and call
		the method internals ourselves. This MUST be done, otherwise, weird
		effects will occur, such as the armor model always being scaled for a
		baby since that's the default.
		*/
        this.attackTime = original.attackTime
        this.riding = original.riding
        this.young = original.young

        // Copy the poses from the original models
        this.head.copyFrom(original.head)
        this.body.copyFrom(original.body)
        this.rightLeg.copyFrom(original.rightLeg)
        this.leftLeg.copyFrom(original.leftLeg)

        // Set the visibilities of the necessary parts
        this.head.visible = slot == EquipmentSlot.HEAD

        /*
		Parents must be visible for their children to be visible. As such,
		never have a parent for a child that will not be visible for the given
		equipment slot.
		*/
        this.body.visible = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS
        this.rocket.visible = slot == EquipmentSlot.CHEST
        this.bodyBelt.visible = slot == EquipmentSlot.LEGS

        this.rightLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET
        this.leftLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET

        this.leftLegBelt.visible = slot == EquipmentSlot.LEGS
        this.rightLegBelt.visible = slot == EquipmentSlot.LEGS

        this.leftAnkle.visible = slot == EquipmentSlot.FEET
        this.rightAnkle.visible = slot == EquipmentSlot.FEET
    }

    override def setupAnim(entity: LivingEntity, animationPosition: Float, animationSpeed: Float, bob: Float, yHeadRot: Float, xHeadRot: Float): Unit = {}

    override def headParts: lang.Iterable[ModelPart] = CollectionConverters.asJava(List(this.head))

    override def bodyParts: lang.Iterable[ModelPart] = CollectionConverters.asJava(List(this.body, this.rightLeg, this.leftLeg))
}

/**
 * The global instance used to hold mesh methods.
 */
object CollageModel {

    /**
     * Creates a mesh representing this model. Assumes that any offset will
     * affect all parts uniformly.
     *
     * @param deformation the inflation value to apply to the cubes' axes
     * @param yOffset the offset of the model in the y direction
     * @return a mesh representing this model
     */
    def createMesh(deformation: CubeDeformation, yOffset: Float): MeshDefinition = createMesh(deformation, yOffset, yOffset, yOffset, yOffset)

    def createMesh(deformation: CubeDeformation, yHeadOffset: Float, yBodyOffset: Float, yRightLegOffset: Float, yLeftLegOffset: Float): MeshDefinition = {
        // Create the mesh definition and get the root
        val mesh = MeshDefinition()
        val root = mesh.getRoot

        /*
        Head parts. The easiest method to apply transforms from the parent
        model is to contain the same root parts at the appropriate origin
        but with no cubes. Technically, this is not necessary as the part
        pose during copying. However, if a transformation was applied to
        any individual part of the armor model, this parent/child setup will
        allow the pose to not be replaced.
        */
        val head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0f, yHeadOffset, 0f))
        head.addOrReplaceChild(
            "hat", CubeListBuilder.create()
                    .texOffs(24, 0).addBox(-5.0f, -9.0f, -5.0f, 10.0f, 1.0f, 10.0f, deformation)
                    .texOffs(0, 0).addBox(-4.0f, -11.0f, -4.0f, 8.0f, 2.0f, 8.0f, deformation),
            PartPose.ZERO
        )

        // Body parts
        val body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0f, yBodyOffset, 0f))
        body.addOrReplaceChild(
            "belt", CubeListBuilder.create()
                    .texOffs(24, 11).addBox(-4.0f, 9.0f, -3.0f, 8.0f, 1.0f, 1.0f, deformation),
            PartPose.ZERO
        )
        body.addOrReplaceChild(
            "rocket", CubeListBuilder.create()
                    .texOffs(0, 10).addBox(-3.0f, 1.0f, 2.0f, 6.0f, 10.0f, 6.0f, deformation),
            PartPose.ZERO
        )

        // Right leg parts
        val rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9f, 12f + yRightLegOffset, 0f))
        rightLeg.addOrReplaceChild(
            "right_leg_belt", CubeListBuilder.create()
                    .texOffs(24, 13).addBox(-2.1f, 1.0f, -3.0f, 4.0f, 1.0f, 1.0f, deformation),
            PartPose.ZERO
        )
        rightLeg.addOrReplaceChild(
            "right_ankle", CubeListBuilder.create()
                    .texOffs(44, 11).addBox(-2.1f, 10.0f, 2.0f, 4.0f, 1.0f, 1.0f, deformation),
            PartPose.ZERO
        )

        // Left leg parts
        val leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9f, 12f + yLeftLegOffset, 0f))
        leftLeg.addOrReplaceChild(
            "left_leg_belt", CubeListBuilder.create()
                    .texOffs(34, 13).addBox(-1.9f, 1.0f, -3.0f, 4.0f, 1.0f, 1.0f, deformation),
            PartPose.ZERO
        )
        leftLeg.addOrReplaceChild(
            "left_ankle",
            CubeListBuilder.create().texOffs(44, 13).addBox(-1.9f, 10.0f, 2.0f, 4.0f, 1.0f, 1.0f, deformation),
            PartPose.ZERO
        )

        mesh
    }
}
