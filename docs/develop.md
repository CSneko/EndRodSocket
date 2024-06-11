# 开发文档
此文档主要针对开发者使用，如果你是普通玩家，请查看[用户文档](https://github.com/csneko/endrodsocket/tree/master/docs/user.md)。

## 前提条件
要开发翻译层，你需要确保你会任意一种支持网络通信的语言（例如:Python,JavaScript,C/C++,Java/Kotlin/Scala,golang,php等），本文档中主要使用Python（不会也没关系，你只需要知道大致逻辑即可）。

## 建立连接
首先，你需要创建一个Socket客户端来与Minecraft建立Socket连接，可参考以下代码：
```Python
import socket
ip = '127.0.0.1' # 替换为你的实际ip，如果本地可使用127.0.0.1
port = 10000 # 替换为你在Minecraft中使用/ers命令创建的端口
# 创建一个 TCP 套接字
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 连接到服务器
server_address = (ip, port)
client_socket.connect(server_address)
```
本段代码的作用是创建一个 TCP Socket 连接，其中ip为服务器ip（如果是在本地请使用127.0.0.1），端口为使用/ers命令创建的端口。
## 接收数据
连接成功后，你就需要接收来自Minecraft的数据了，以下是参考代码:
```Python
 while True:
        # 接收数据
        chunk = client_socket.recv(1024)
	# 处理数据
```
这一段代码是一个简单的实现，通过循环的方式来等待数据。
## 解析数据
Socket客户端会接收到的数据为[JSON格式](https://www.json.org/json-en.html)，值如下:
```json
{
	"player":"abc",
	"playerNbt":[],
	"targetPlayer":"abc",
	"targetPlayerNbt":[],
	"item":"electric_rod",
	"itemNbt":[],
	"times": 20,
	"type":"self"
}
```
以下是对每个键的解释:
- `player`: 玩家名称
- `playerNbt`: 玩家的nbt数据
- `targetPlayer`: 被艹的玩家名称（如果`type`为`self`，那么此项与`player`相同）
- `targetPlayerNbt`: 被艹的玩家nbt（同上）
- `item`: 物品id（可使用`/give @s more_end_rod:物品id`来获取）
- `times`: 被艹的次数
- `type`: 方式（`attack`为被其它玩家艹，`self`为紫薇）
 
