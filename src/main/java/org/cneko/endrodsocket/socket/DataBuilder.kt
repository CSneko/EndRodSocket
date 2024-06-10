package org.cneko.endrodsocket.socket

import org.cneko.ctlib.common.file.JsonConfiguration

class DataBuilder {
    val data:JsonConfiguration = JsonConfiguration("{}")
    // 设置玩家
    fun setPlayer(player:String):DataBuilder{
        data.set("player", player)
        return this
    }
    // 设置玩家nbt
    fun setPlayerNbt(nbt:String):DataBuilder{
        data.set("playerNbt", nbt)
        return this
    }
    // 设置目标玩家
    fun setTargetPlayer(player:String):DataBuilder{
        data.set("targetPlayer", player)
        return this
    }
    // 设置目标玩家nbt
    fun setTargetPlayerNbt(nbt:String):DataBuilder{
        data.set("targetPlayerNbt", nbt)
        return this
    }
    // 设置使用的物品
    fun setItem(item:String):DataBuilder{
        data.set("item", item)
        return this
    }
    // 设置物品nbt
    fun setItemNbt(nbt:String):DataBuilder{
        data.set("itemNbt", nbt)
        return this
    }
    // 设置类型
    fun setType(type:String):DataBuilder{
        data.set("type", type)
        return this
    }
    // 设置次数
    fun setTimes(times:Int):DataBuilder{
        data.set("times", times)
        return this
    }
    fun build():String{
        return data.toString()
    }

}