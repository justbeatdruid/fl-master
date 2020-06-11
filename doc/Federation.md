### 联邦管理接口

#### 创建联邦
1. 请求方法：POST
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations
3. 请求Header：Content-Type:application/json
4. 请求Body：
body参数：
```json
{
  "name": "联邦名字",
  "description": "", //联邦描述
  "type": 0,  //0表示横向联邦，1表示纵向联邦
  "data_format": "？？？", //数据格式
  "algorithm_id": 1,  //算法ID
  "param": "？？？" //联邦训练参数
}
```

#### 查询联邦详情
1. 请求方法：GET
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/{uuid}
3. 请求参数：

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
| uuid | string | 联邦uuid |

4. 返回Body：
```json
{
  "success": true,
  "code": 200,
  "message": "请求成功",
  "data": {
    "name": "testtest1225", //联邦名称
    "uuid": "50bae40e9fe341a09c166305af7dae86", //联邦uuid
    "createdAt": "2020-06-09 17:29:23", //联邦创建时间
    "type": true, //联邦类型，false表示横向联邦，true表示纵向联邦
    "description": null,  //联邦描述
    "guest": "",  //guest用户
    "hosts": [],  //hosts用户列表
    "status": 0, //联邦状态
    "displayStatus": "等待", //联邦状态中文显示
    "dataFormat": "",  //联邦数据格式
    "algorithmId": 1,  //联邦关联算法ID
    "param": "" //联邦训练参数
  }
}
```

#### 查询联邦列表
1. 请求方法：GET
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations?name=xxx
3. 请求参数

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
| name | string | 联邦名字模糊查询，可为空 |

4. 返回body:
```json
{
  "success": true,
  "code": 200,
  "message": "请求成功",
  "data": [{
    "name": "xuxutest",
    "uuid": "095d9e9684dd4f338d4897740f99da91",
    "createdAt": "2020-06-08 00:00:00",
    "type": null,
    "description": "hello world这是一个测试联邦",
    "guest": "",
    "hosts": null,
    "status": 0,
    "displayStatus": "等待",
    "dataFormat": null,
    "algorithmId": null,
    "param": null
  }, {
    "name": "testtest1225",
    "uuid": "50bae40e9fe341a09c166305af7dae86",
    "createdAt": "2020-06-09 00:00:00",
    "type": true,
    "description": null,
    "guest": "",
    "hosts": null,
    "status": 0,
    "displayStatus": "等待",
    "dataFormat": null,
    "algorithmId": null,
    "param": null
  }],
  "pageInfo": null,
  "ext": null
}
```

#### 删除联邦
1. 请求方法：GET
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/{uuid}
3. 请求参数：

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
uuid | string | 联邦uuid 


#### 更新联邦
1. 请求方法：PUT
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/{uuid}
3. 请求Header：Content-Type:application/json
4. 请求参数：

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
uuid | string | 联邦uuid 
5. 请求Body：
body参数：
```json
{
  "name": "联邦名字",
  "description": "", //联邦描述
  "type": 0,  //0表示横向联邦，1表示纵向联邦
  "data_format": "？？？", //数据格式
  "algorithm_id": 1,  //算法ID
  "param": "？？？" //联邦训练参数
}
```


#### 将联邦状态转为就绪
1. 请求方法：PUT
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/{uuid}/ready
3. 请求参数：

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
uuid | string | 联邦uuid 