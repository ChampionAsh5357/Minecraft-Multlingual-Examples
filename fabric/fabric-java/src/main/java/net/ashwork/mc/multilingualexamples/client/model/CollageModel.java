/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.client.model;

import com.google.common.collect.ImmutableList;
import net.ashwork.mc.multilingualexamples.item.ExampleArmorMaterials;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

/**
 * The armor model class for {@link ExampleArmorMaterials#COLLAGE}.
 *
 * <p>As armor models are provided through the {@link HumanoidArmorLayer}, it
 * is highly recommended to use {@link AgeableListModel} as the lowest bound.
 * It has basic logic for scaling to baby forms of entities, so it is an ease
 * of convenience.
 */
public class CollageModel extends AgeableListModel<LivingEntity> {
	protected final ModelPart head;
	protected final ModelPart body;
	protected final ModelPart rocket;
	protected final ModelPart bodyBelt;
	protected final ModelPart rightLeg;
	protected final ModelPart rightLegBelt;
	protected final ModelPart rightAnkle;
	protected final ModelPart leftLeg;
	protected final ModelPart leftLegBelt;
	protected final ModelPart leftAnkle;

	/**
	 * Default constructor. Uses the no cull version of the entity cutout
	 * render type (binary alpha, armor never gets skipped during rendering).
	 *
	 * @param root the part representing the base of the model
	 */
	public CollageModel(ModelPart root) {
		this(root, RenderType::entityCutoutNoCull);
	}

	/**
	 * Default constructor.
	 *
	 * @param root the part representing the base of the model
	 * @param renderType the render type to use when uploading the model to the buffer
	 */
	public CollageModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
		super(renderType, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
		/*
		Head elements, no need to get children as they are all visible in the
		same equipment slot.
		 */
		this.head = root.getChild("head");

		/*
		Body elements, need children as rocket and belt are visible in different
		equipment slots.
		 */
		this.body = root.getChild("body");
		this.rocket = this.body.getChild("rocket");
		this.bodyBelt = this.body.getChild("belt");

		/*
		Right leg elements, need children as belt and ankle guard are visible in
		different equipment slots.
		 */
		this.rightLeg = root.getChild("right_leg");
		this.rightLegBelt = this.rightLeg.getChild("right_leg_belt");
		this.rightAnkle = this.rightLeg.getChild("right_ankle");

		/*
		Left leg elements, need children as belt and ankle guard are visible in
		different equipment slots.
		 */
		this.leftLeg = root.getChild("left_leg");
		this.leftLegBelt = this.leftLeg.getChild("left_leg_belt");
		this.leftAnkle = this.leftLeg.getChild("left_ankle");
	}

	/**
	 * Creates a mesh representing this model. Assumes that any offset will
	 * affect all parts uniformly.
	 *
	 * @param deformation the inflation value to apply to the cubes' axes
	 * @param yOffset the offset of the model in the y direction
	 * @return a mesh representing this model
	 */
	public static MeshDefinition createMesh(CubeDeformation deformation, float yOffset) {
		return createMesh(deformation, yOffset, yOffset, yOffset, yOffset);
	}

	/**
	 * Creates a mesh representing this model.
	 *
	 * @param deformation the inflation value to apply to the cubes' axes
	 * @param yHeadOffset the offset of the head in the y direction
	 * @param yBodyOffset the offset of the body in the y direction
	 * @param yRightLegOffset the offset of the right leg in the y direction
	 * @param yLeftLegOffset the offset of the left leg in the y direction
	 * @return a mesh representing this model
	 */
	public static MeshDefinition createMesh(CubeDeformation deformation, float yHeadOffset, float yBodyOffset, float yRightLegOffset, float yLeftLegOffset) {
		// Create the mesh definition and get the root
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		/*
		Head parts. The easiest method to apply transforms from the parent
		model is to contain the same root parts at the appropriate origin
		but with no cubes. Technically, this is not necessary as the part
		pose during copying. However, if a transformation was applied to
		any individual part of the armor model, this parent/child setup will
		allow the pose to not be replaced.
		 */
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, yHeadOffset, 0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create()
						.texOffs(24, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, deformation)
						.texOffs(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 2.0F, 8.0F, deformation),
				PartPose.ZERO);

		// Body parts
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0F, yBodyOffset, 0F));
		body.addOrReplaceChild("belt", CubeListBuilder.create()
						.texOffs(24, 11).addBox(-4.0F, 9.0F, -3.0F, 8.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);
		body.addOrReplaceChild("rocket", CubeListBuilder.create()
						.texOffs(0, 10).addBox(-3.0F, 1.0F, 2.0F, 6.0F, 10.0F, 6.0F, deformation),
				PartPose.ZERO);

		// Right leg parts
		PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12F + yRightLegOffset, 0F));
		rightLeg.addOrReplaceChild("right_leg_belt", CubeListBuilder.create()
						.texOffs(24, 13).addBox(-2.1F, 1.0F, -3.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);
		rightLeg.addOrReplaceChild("right_ankle", CubeListBuilder.create()
						.texOffs(44, 11).addBox(-2.1F, 10.0F, 2.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);

		// Left leg parts
		PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12F + yLeftLegOffset, 0F));
		leftLeg.addOrReplaceChild("left_leg_belt", CubeListBuilder.create()
						.texOffs(34, 13).addBox(-1.9F, 1.0F, -3.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);
		leftLeg.addOrReplaceChild("left_ankle", CubeListBuilder.create().
						texOffs(44, 13).addBox(-1.9F, 10.0F, 2.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);

		return mesh;
	}

	/**
	 * Copies the poses from the original armor model and sets the visibility
	 * of each layer for rendering.
	 *
	 * @param original the original armor model
	 * @param slot the slot being rendered
	 */
	public void copyAndSet(HumanoidModel<?> original, EquipmentSlot slot) {
		/*
		These can be set using '#copyPropertiesTo' if they were both humanoid
		models. Since they are not, we have to do this the manual way and call
		the method internals ourselves. This MUST be done, otherwise, weird
		effects will occur, such as the armor model always being scaled for a
		baby since that's the default.
		 */
		this.attackTime = original.attackTime;
		this.riding = original.riding;
		this.young = original.young;

		// Copy the poses from the original models
		this.head.copyFrom(original.head);
		this.body.copyFrom(original.body);
		this.rightLeg.copyFrom(original.rightLeg);
		this.leftLeg.copyFrom(original.leftLeg);

		// Set the visibilities of the necessary parts
		this.head.visible = slot == EquipmentSlot.HEAD;

		/*
		Parents must be visible for their children to be visible. As such,
		never have a parent for a child that will not be visible for the given
		equipment slot.
		 */
		this.body.visible = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS;
		this.rocket.visible = slot == EquipmentSlot.CHEST;
		this.bodyBelt.visible = slot == EquipmentSlot.LEGS;

		this.rightLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
		this.leftLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;

		this.leftLegBelt.visible = slot == EquipmentSlot.LEGS;
		this.rightLegBelt.visible = slot == EquipmentSlot.LEGS;

		this.leftAnkle.visible = slot == EquipmentSlot.FEET;
		this.rightAnkle.visible = slot == EquipmentSlot.FEET;
	}

	@Override
	public void setupAnim(LivingEntity entity, float animationPosition, float animationSpeed, float bob, float yHeadRot, float xHeadRot) {}

	@Override
	protected Iterable<ModelPart> headParts() {
		/*
		These should include any parts that should be scaled with the head
		when shrinking. You can move the armor model into '#bodyParts' if wanted.
		 */
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}
}
