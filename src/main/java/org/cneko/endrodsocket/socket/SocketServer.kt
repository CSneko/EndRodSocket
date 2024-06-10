package org.cneko.endrodsocket.socket

import org.cneko.endrodsocket.EndRodSocket
import java.net.ServerSocket
import java.net.Socket
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.io.PrintWriter

import org.cneko.endrodsocket.EndRodSocket.logger
class SocketServer(port: Int) {
    private val serverSocket: ServerSocket = ServerSocket(port)
    private var dataToSend: String = ""
    init {
        handle()
    }
    fun sendData(data: String){
        dataToSend = data
    }

    private fun handle() {
        while (true) {
            val socket = serverSocket.accept()
            logger.info("Client connected: ${socket.inetAddress.hostAddress}")

            // 在每一个客户端连接时，创建一个新的线程来处理该客户端的请求
            Thread(ClientHandler(socket)).start()
        }
    }

    private inner class ClientHandler(private val socket: Socket) : Runnable {
        private val inputStream = socket.getInputStream()
        private val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        private val outputStream = socket.getOutputStream()
        private val printWriter = PrintWriter(OutputStreamWriter(outputStream))

        override fun run() {
            try {
                while (true) {
                    // 读取来自客户端的数据（如果客户端断开连接，readLine()将返回null，此时可以退出循环）
                    val dataFromClient = bufferedReader.readLine() ?: break


                    // 处理客户端数据
                    val response = processClientData(dataFromClient)

                    // 向客户端发送响应
                    printWriter.println(response)
                    printWriter.flush()
                }
            } catch (e: Exception) {
                logger.error("Error handling client: ${e.message}")
            } finally {
                bufferedReader.close()
                printWriter.close()
                socket.close()
            }
        }

        fun processClientData(data: String): String {
            // 发送数据
            val to:String = dataToSend
            dataToSend = ""
            return to
        }
    }

    fun close() {
        serverSocket.close()
    }
}
