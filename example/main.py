import socket

# 创建一个 TCP 套接字
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 连接到服务器
server_address = ('127.0.0.1', 10000)
client_socket.connect(server_address)

# 发送消息
message = 'Hello, world!'.encode('utf-8')
client_socket.send(message)

while True:
    # 接收响应
    response = client_socket.recv(1024)
    if not response:
        break
    print(response.decode('utf-8'))

# 关闭套接字
client_socket.close()
