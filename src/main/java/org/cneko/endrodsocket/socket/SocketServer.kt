package org.cneko.endrodsocket.socket

import org.cneko.endrodsocket.EndRodSocket
import java.net.ServerSocket
import java.net.Socket
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.util.concurrent.ConcurrentLinkedQueue

import org.cneko.endrodsocket.EndRodSocket.logger
class SocketServer(port: Int) {
    private val messageQueue = ConcurrentLinkedQueue<String>()
    private val serverSocket: ServerSocket = ServerSocket(port)
    private var dataToSend: String = ""
    fun sendData(data: String) {
        messageQueue.add(data)
    }
    fun handle() {
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
                    // 读取客户端数据
                    /*
                    val dataFromClient = bufferedReader.readLine() ?: break
                    val response = processClientData(dataFromClient)
                    printWriter.println(response)
                    printWriter.flush()
                    */

                    // 检查并发送消息队列中的消息
                    var message: String? = messageQueue.poll()
                    while (message != null) {
                        printWriter.println(message)
                        printWriter.flush()
                        message = messageQueue.poll() // 直接在此处再次尝试poll下一个消息
                    }
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
            dataToSend = "12345"
            return to
        }
    }

    fun close() {
        serverSocket.close()
    }
}
