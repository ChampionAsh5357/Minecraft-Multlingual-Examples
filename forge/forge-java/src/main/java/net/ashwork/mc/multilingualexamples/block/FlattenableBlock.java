/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A generic block class that allows the block to be flattened into a slab by a
 * tool with the {@link ToolActions#SHOVEL_FLATTEN} action.
 */
public class FlattenableBlock extends Block {

    private final Supplier<? extends SlabBlock> flattenedBlock;

    /**
     * Default constructor.
     *
     * @param flattenedBlock the slab this block will become when flattened
     * @param properties the block properties
     */
    public FlattenableBlock(Supplier<? extends SlabBlock> flattenedBlock, Properties properties) {
        super(properties);
        this.flattenedBlock = flattenedBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction == ToolActions.SHOVEL_FLATTEN) {
            return this.flattenedBlock.get().defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, switch (context.getClickedFace()) {
                case UP ->  SlabType.BOTTOM;
                case DOWN -> SlabType.TOP; // This will never be executed because of how flattening works
                default -> context.getClickLocation().y - (double) context.getClickedPos().getY() < 0.5 ? SlabType.BOTTOM : SlabType.TOP;
            });
        }
        else return null;
    }
}
