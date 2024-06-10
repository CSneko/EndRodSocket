package org.cneko.endrodsocket.socket

import org.cneko.endrodsocket.EndRodSocket.logger
import org.cneko.endrodsocket.util.PortChecker

class Sockets {
    companion object{
        var server: SocketServer? = null
        fun setServer(port : Int) {
            // 如果已经有存在的服务器则关闭
            if(server!=null){
                server?.close()
            }
            // 检查端口是否被占用
            if(!PortChecker.isPortUsed(port)){
                // 在新线程中启动服务器
                val t = Thread {
                    server = SocketServer(port)
                }
                t.start()
                logger.info("Server started on port $port")
            }
        }
    }
}