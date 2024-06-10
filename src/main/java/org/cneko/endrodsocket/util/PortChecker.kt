package org.cneko.endrodsocket.util

class PortChecker {
    companion object {
        /**
         * 检测端口是否被占用
         * @param port 端口
         * @return true:端口被占用 false:端口未被占用
         */
        fun isPortUsed(port: Int): Boolean {
            try {
                val serverSocket = java.net.ServerSocket(port)
                serverSocket.close()
                return false
            } catch (e: java.lang.Exception) {
                return true
            }
        }
    }
}