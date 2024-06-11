import socket
import json

def receive_and_process_messages(client_socket):
    buffer = b''  # 初始化缓冲区来存储接收的数据
    while True:
        # 接收数据
        chunk = client_socket.recv(1024)
        if not chunk:
            break
        buffer += chunk
        
        # 尝试从缓冲区中解析JSON对象
        while buffer:
            try:
                # 尝试找到一个JSON对象的边界
                idx = buffer.index(b'}') + 1  # 假设消息以'}'结尾
                
                # 提取并尝试解析这一段
                message = buffer[:idx]
                buffer = buffer[idx:]  # 移除已处理的部分
                j: dict = json.loads(message.decode('utf-8'))
                
                player: str = j["player"]
                item: str = j["item"]
                times: int = j["times"]
                print(f"{player} 使用 {item} 紫薇了 {times} 次")
                print(f"物品nbt: {j.get('itemNbt', '无')}")
            
            except ValueError:  # JSONDecodeError 的父类，用于捕捉解析错误
                # 如果没有找到完整的JSON对象或解析失败，则跳出本次尝试，等待更多数据
                break
            except IndexError:  # 未找到'}'，意味着数据还在继续
                # 缓冲区中的数据还不完整，等待下一次接收
                break
            
    # 当没有更多数据可读时，关闭套接字
    client_socket.close()

# 创建一个 TCP 套接字
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 连接到服务器
server_address = ('127.0.0.1', 10000)
client_socket.connect(server_address)

# 发送消息
message = 'Hello, world!'.encode('utf-8')
client_socket.send(message)

# 开始接收并处理消息
receive_and_process_messages(client_socket)