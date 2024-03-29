# 指令使用
当输入指令为 `ban=EA_MOM` 时, 实质上等于 `ban --param=EA_MOM`  

某些指令不需要使用者强制附带 `param` 参数，即使参数为空  
如 `ltk` 指令，输入 `ltk` 与 `ltk=` 是一样的效果  

当某些指令例如 `ltk` 提示缺少参数或其他字样时，可试试 `ltk=`

# 指令列表
## help
说明：  
查看当前KookBot所加载并支持的指令

## ban
说明：  
在指定服务器封禁指定名字的玩家

参数：
- **param**: 玩家名字
- **sid**: 选填，服务器标识符
  - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行

示例：
- `ban=EA_MOM`
  - 封禁 `EA_MOM` 玩家，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `ban --param=EA_MOM --sid=a1`
  - 封禁 `EA_MOM` 玩家，并且指定了在标识符为 `a1` 的服务器中执行

## ltk
说明：  
在指定服务器查看最近50条聊天记录

参数：
- **param**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行
- **sid**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行

示例：
- `ltk`
    - 查看服务器最近50条聊天记录，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `ltk --sid=a1`
    - 查看 `a1` 服务器最近50条聊天记录
- `ltk --param=a1`
    - 查看 `a1` 服务器最近50条聊天记录

## pb
说明：  
在指定服务器查看指定玩家的屏蔽记录 / 在指定服务器查看最近50条屏蔽记录

参数：
- **param**: 选填，玩家名字
- **sid**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行

示例：
- `pb=EA_MOM`
    - 查看 `EA_MOM` 玩家的屏蔽记录，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `pb --param=EA_MOM --sid=a1`
    - 查看 `EA_MOM` 玩家在 `a1` 服务器的屏蔽记录
- `pb --sid=a1`
    - 查看 `a1` 服务器最近50条屏蔽记录
- `pb=a1`
    - 查看 `a1` 服务器最近50条屏蔽记录
- `pb`
    - 查看某个服务器最近50条屏蔽记录，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_

## cx
说明：  
查询玩家的信息

参数：
- **param**: 玩家名字

示例：
- `cx=EA_MOM`

## fwq
说明：  
查询服务器信息（只显示20条符合的）

参数：
- **param**: 服务器关键词

示例：
- `fwq=DICE`

## rp
说明：  
查询指定服务器中，最近的活跃玩家列表

参数：
- **param**: 玩家名字关键词

示例：
- `rp`
  - 返回最近80名活跃玩家
- `rp=EA`
  - 从最近80名活跃玩家中，找到名字包含 `EA` 的玩家，并返回对应的集合

## exec
说明：  
在指定服务器执行原始指令

参数：
- **param**: 指令
- **sid**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行

示例：
- `exec="context"`
    - 直接在ARL执行 `context` 指令，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `exec --param="context" --sid=a1`
    - 直接在标识符为 `a1` 的ARL执行 `context` 指令

## task
说明：  
在指定服务器上执行自动任务(Task)

参数：
- **param**: Task名字，可参考 [自动任务](https://github.com/H4rry217/bfvrobot-ocr/blob/master/_resources/AUTO_MANAGE.md#自动任务)
- **sid**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行

示例：
- `task=CreateTask`
    - 执行 `CreateTask(创建房间)` 的任务，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `task --param=CreateTask --sid=a1`
    - 在 `a1` 执行 `CreateTask(创建房间)` 的任务

## jt
说明：  
在指定服务器上截图

参数：
- **param**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行
- **sid**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行

示例：
- `jt`
    - 截图，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `jt --sid=a1`
    - 在 `a1` 服务器进行截图
- `jt=a1`
    - 在 `a1` 服务器进行截图

## lt
说明：  
在指定服务器发送管理员大喇叭

参数：
- **param**: 发送内容
- **sid**: 选填，服务器标识符
    - 当此参数留空时，ARSJ会根据已连接的ARL来自动选择一个ServerId执行

示例：
- `lt=Test测试内容`
    - 发送 `Test测试内容`，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `lt="Test 带空格的内容"`
    - 发送 `Test 带空格的内容`，_但由于并没有指定在哪个服务器执行，所以ARSJ会自动选择一个已连接的服务器_
- `lt --params=Test测试内容 --sid=a1`
    - 发送 `Test测试内容` 至 `a1` 服务器

## help
说明：  
查看所有已经连接的服务器及其状态