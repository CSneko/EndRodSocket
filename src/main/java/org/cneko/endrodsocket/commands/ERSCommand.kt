package org.cneko.endrodsocket.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands.literal
import net.minecraft.commands.Commands.argument
import net.minecraft.network.chat.Component
import org.cneko.endrodsocket.socket.Sockets
import org.cneko.endrodsocket.util.PortChecker

class ERSCommand {
    companion object {
        fun init() {
            CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback {dispatcher, registryAccess, environment ->
                dispatcher.register(literal("esr")
                    .requires { source -> source.hasPermission(4) }
                    .then(literal("port")
                        .then(argument("port", IntegerArgumentType.integer(1024, 65535))
                            .executes(::port)
                        )
                    )
                )
            })
        }

        fun port(context: CommandContext<CommandSourceStack>?): Int {
             // 检查端口是否被占用
            val p: Int = IntegerArgumentType.getInteger(context, "port")
            if (PortChecker.isPortUsed(p)) {
                context!!.source.sendFailure(Component.translatable("command.esr.port.used"))
                return 0
            }
            // 启动socket服务器
            Sockets.setServer(p)
            context!!.source.sendSystemMessage(Component.translatable("command.esr.port.success", p))
            return 1
        }
    }
}