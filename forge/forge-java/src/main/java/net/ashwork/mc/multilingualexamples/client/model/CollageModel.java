package net.ashwork.mc.multilingualexamples.client.model;


import com.google.common.collect.ImmutableList;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

//TODO: Document
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
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

	public CollageModel(ModelPart root) {
		this(root, RenderType::entityCutoutNoCull);
	}

	public CollageModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
		super(renderType, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
		this.head = root.getChild("head");

		this.body = root.getChild("body");
		this.rocket = this.body.getChild("rocket");
		this.bodyBelt = this.body.getChild("belt");

		this.rightLeg = root.getChild("right_leg");
		this.rightLegBelt = this.rightLeg.getChild("right_leg_belt");
		this.rightAnkle = this.rightLeg.getChild("right_ankle");

		this.leftLeg = root.getChild("left_leg");
		this.leftLegBelt = this.leftLeg.getChild("left_leg_belt");
		this.leftAnkle = this.leftLeg.getChild("left_ankle");
	}

	public static MeshDefinition createMesh(CubeDeformation deformation, float yOffset) {
		return createMesh(deformation, yOffset, yOffset, yOffset, yOffset);
	}

	public static MeshDefinition createMesh(CubeDeformation deformation, float yHeadOffset, float yBodyOffset, float yRightLegOffset, float yLeftLegOffset) {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, yHeadOffset, 0F));
		head.addOrReplaceChild("hat", CubeListBuilder.create()
						.texOffs(24, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, deformation)
						.texOffs(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 2.0F, 8.0F, deformation),
				PartPose.ZERO);

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0F, yBodyOffset, 0F));
		body.addOrReplaceChild("belt", CubeListBuilder.create()
						.texOffs(24, 11).addBox(-4.0F, 9.0F, -3.0F, 8.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);
		body.addOrReplaceChild("rocket", CubeListBuilder.create()
						.texOffs(0, 10).addBox(-3.0F, 1.0F, 2.0F, 6.0F, 10.0F, 6.0F, deformation),
				PartPose.ZERO);

		PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12F + yRightLegOffset, 0F));
		rightLeg.addOrReplaceChild("right_leg_belt", CubeListBuilder.create()
						.texOffs(24, 13).addBox(-2.1F, 1.0F, -3.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);
		rightLeg.addOrReplaceChild("right_ankle", CubeListBuilder.create()
						.texOffs(44, 11).addBox(-2.1F, 10.0F, 2.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);

		PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12F + yLeftLegOffset, 0F));
		leftLeg.addOrReplaceChild("left_leg_belt", CubeListBuilder.create()
						.texOffs(34, 13).addBox(-1.9F, 1.0F, -3.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);
		leftLeg.addOrReplaceChild("left_ankle", CubeListBuilder.create().
						texOffs(44, 13).addBox(-1.9F, 10.0F, 2.0F, 4.0F, 1.0F, 1.0F, deformation),
				PartPose.ZERO);

		return mesh;
	}

	public void copyAndSet(HumanoidModel<?> original, EquipmentSlot slot) {
		this.attackTime = original.attackTime;
		this.riding = original.riding;
		this.young = original.young;

		this.head.copyFrom(original.head);
		this.body.copyFrom(original.body);
		this.rightLeg.copyFrom(original.rightLeg);
		this.leftLeg.copyFrom(original.leftLeg);

		this.head.visible = slot == EquipmentSlot.HEAD;

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
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}
}