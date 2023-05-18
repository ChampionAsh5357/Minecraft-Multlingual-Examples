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
 * tool with the [ToolActions.SHOVEL_FLATTEN] action.
 *
 * @param flattenedBlock the slab this block will become when flattened
 * @param properties the block properties
 */
class FlattenableBlock(properties: Properties, flattenedBlock: () -> SlabBlock): Block(properties) {

    /*
    Although blocks will be populated before other blocks, there is the off
    chance that the passed in block is loaded after this block, so it is better
    to supply a lazy reference instead, preferably through 'RegistryObject'.
     */
    private val flattenedBlock: SlabBlock by lazy(flattenedBlock)

    override fun getToolModifiedState(state: BlockState, context: UseOnContext, toolAction: ToolAction, simulate: Boolean): BlockState? {
        return if (toolAction == ToolActions.SHOVEL_FLATTEN) {
            this.flattenedBlock.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, when (context.clickedFace) {
                Direction.UP -> SlabType.BOTTOM
                Direction.DOWN -> SlabType.TOP // This will never be executed because of how flattening works
                else -> if (context.clickLocation.y - context.clickedPos.y.toDouble() < 0.5) SlabType.BOTTOM else SlabType.TOP
            })
        } else null
    }
}
