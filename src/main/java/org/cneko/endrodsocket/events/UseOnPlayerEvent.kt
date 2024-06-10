package org.cneko.endrodsocket.events

import net.minecraft.world.InteractionHand
import org.cneko.more_end_rod.api.EndRodEvents
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import org.cneko.endrodsocket.socket.DataBuilder
import org.cneko.endrodsocket.socket.Sockets

class UseOnPlayerEvent {
    companion object {
        fun init() {
            // 注册事件
            EndRodEvents.USE_ON_PLAYER.register(EndRodEvents.UseOnPlayer { item, user , player, hand ->
                onUse(item,user,player,hand)
                InteractionResult.SUCCESS
            })
        }

        fun onUse(stack: ItemStack, user: Player, player: Player, hand: InteractionHand) {
            val data:String = DataBuilder()
                .setPlayer(user.name.string)
                .setPlayerNbt(user.tags.toString())
                .setTargetPlayer(player.name.string)
                .setTargetPlayerNbt(player.tags.toString())
                .setItem(stack.item.toString())
                .setItemNbt(stack.tags.toString())
                .setType("attack")
                .setTimes(1)
                .build()
            // 发送数据
            if(Sockets.server != null){
                Sockets.server!!.sendData(data)
            }
        }


    }
}