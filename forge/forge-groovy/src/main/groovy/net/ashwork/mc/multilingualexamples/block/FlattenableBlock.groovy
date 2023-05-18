/*
 * Multilingual Examples
 * Written 2021-2023 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.SlabType
import net.minecraftforge.common.ToolAction
import net.minecraftforge.common.ToolActions
/**
 * A generic block class that allows the block to be flattened into a slab by a
 * tool with the {@link ToolActions#SHOVEL_FLATTEN} action.
 */
class FlattenableBlock extends Block {

    private final Closure<? extends SlabBlock> flattenedBlock

    /**
     * Default constructor.
     *
     * @param flattenedBlock the slab this block will become when flattened
     * @param properties the block properties
     */
    FlattenableBlock(Properties properties, Closure<? extends SlabBlock> flattenedBlock) {
        super(properties)
        this.flattenedBlock = flattenedBlock.memoize()
    }

    @Override
    BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.SHOVEL_FLATTEN ?
                this.flattenedBlock().defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, switch (context.clickedFace) {
                    case Direction.UP -> SlabType.BOTTOM
                    case Direction.DOWN -> SlabType.TOP // This will never be executed because of how flattening works
                    default -> context.clickLocation.y - context.clickedPos.y.toDouble() < 0.5 ? SlabType.BOTTOM : SlabType.TOP
                }) : null
    }
}
