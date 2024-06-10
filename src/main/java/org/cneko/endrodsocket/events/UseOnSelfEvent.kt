package org.cneko.endrodsocket.events

import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import org.cneko.endrodsocket.socket.DataBuilder
import org.cneko.endrodsocket.socket.Sockets
import org.cneko.more_end_rod.api.EndRodEvents
import java.util.UUID

class UseOnSelfEvent {
    companion object {
        val timer:Map<UUID,Int> = mapOf()
        fun init() {
            EndRodEvents.USE_ON_SELF.register(EndRodEvents.UseOnSelf { stack, user ->
                onUse(stack, user)
                InteractionResult.SUCCESS
            })
        }

        private fun onUse(stack: ItemStack, user: Player) {
            // 每20tick发送一次数据
            val uuid = user.uuid
            if(!timer.containsKey(uuid)){
                timer.plus(Pair(uuid,1))
                return
            }
            // 计数器已经到达20
            if(timer[uuid]!! >= 20){
                timer.plus(Pair(uuid,1))
                // 构建数据
                val data = DataBuilder()
                    .setPlayer(user.name.string)
                    .setPlayerNbt(user.tags.toString())
                    .setTargetPlayer(user.name.string)
                    .setTargetPlayerNbt(user.tags.toString())
                    .setItem(stack.item.toString())
                    .setItemNbt(stack.tags.toString())
                    .setType("self")
                    .setTimes(20)
                    .build()
                // 发送数据
                Sockets.server?.sendData(data)
                return
            }
            timer.plus(Pair(uuid,timer[uuid]!!+1))

        }
    }
}