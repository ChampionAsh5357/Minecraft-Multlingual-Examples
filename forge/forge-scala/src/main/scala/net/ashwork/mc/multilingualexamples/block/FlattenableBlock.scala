/*
 * Multilingual Examples
 * Written 2021-2022 by ChampionAsh5357
 * SPDX-License-Identifier: CC0-1.0
 */

package net.ashwork.mc.multilingualexamples.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.{Block, SlabBlock}
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.{BlockStateProperties, SlabType}
import net.minecraftforge.common.{ToolAction, ToolActions}

/**
 * A generic block class that allows the block to be flattened into a slab by a
 * tool with the [[ToolActions.SHOVEL_FLATTEN]] action.
 *
 * @param flattenedBlock the slab this block will become when flattened
 * @param properties the block properties
 */
class FlattenableBlock(flattenedBlock: () => SlabBlock, properties: Properties) extends Block(properties) {

    /*
    Although blocks will be populated before other blocks, there is the off
    chance that the passed in block is loaded after this block, so it is better
    to supply a lazy reference instead, preferably through 'RegistryObject'.
     */
    private lazy val _flattenedBlock: SlabBlock = flattenedBlock.apply

    override def getToolModifiedState(state: BlockState, context: UseOnContext, toolAction: ToolAction, simulate: Boolean): BlockState =
        if (toolAction == ToolActions.SHOVEL_FLATTEN) this._flattenedBlock.defaultBlockState.setValue(BlockStateProperties.SLAB_TYPE, context.getClickedFace match
            case Direction.UP => SlabType.BOTTOM
            case Direction.DOWN => SlabType.TOP // This will never be executed because of how flattening works
            case _ => if (context.getClickLocation.y - context.getClickedPos.getY.toDouble < 0.5) SlabType.BOTTOM else SlabType.TOP
        )
        else null
}
