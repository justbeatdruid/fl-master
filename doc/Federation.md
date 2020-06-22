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
  "dataFormat": {   //数据格式
    "identity": "test",  //数据标识
    "label": "mylabel",  //数据标签
    "feature": "1;2;3;4;5"  //数据特征
  },
  "algorithmId": 1,  //算法ID
  "param": {
        "param1": 0.1,
        "param2": 100,
        "param3": -12345.6789
      } //联邦训练参数
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
    "dataFormat": {   //数据格式
    "identity": "test",  //数据标识
    "label": "mylabel",  //数据标签
    "feature": "1;2;3;4;5"  //数据特征
     }, 
    "algorithmId": 1,  //联邦关联算法ID
    "param": {
        "param1": 0.1,
        "param2": 100,
        "param3": -12345.6789
    }, //联邦训练参数
    "guestName": "admin",
    "userCount": 1,
    "role": "创建者",
    "enterable": false
  }
}
```

#### 查询联邦列表
1. 请求方法：GET
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations?name=xxx&private=true
3. 请求参数

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
| name | string | 联邦名字模糊查询，可为空 |
| private | bool | 查询我的联邦列表 |

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
    "dataFormat": {   //数据格式
      "identity": "test",  //数据标识
      "label": "mylabel",  //数据标签
      "feature": "1;2;3;4;5"  //数据特征
     },
    "algorithmId": null,
    "param": {
        "param1": 0.1,
        "param2": 100,
        "param3": -12345.6789
    },
    "guestName": "admin",
    "userCount": 1,
    "role": "创建者",
    "enterable": false
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
    "param": {
        "param1": 0.1,
        "param2": 100,
        "param3": -12345.6789
    },
    "guestName": "admin",
    "userCount": 1,
    "role": "创建者",
    "enterable": false
  }],
  "pageInfo": null,
  "ext": null
}
```

#### 删除联邦
1. 请求方法：DELETE
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
  "data_format": {   //数据格式
    "identity": "test",  //数据标识
    "label": "mylabel",  //数据标签
    "feature": "1;2;3;4;5"  //数据特征
   }, //数据格式
  "algorithm_id": 1,  //算法ID
  "param": {
        "param1": 0.1,
        "param2": 100,
        "param3": -12345.6789
      } //联邦训练参数
}
```


#### 将联邦状态转为就绪
1. 请求方法：PUT
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/{uuid}/ready
3. 请求参数：

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
uuid | string | 联邦uuid 

#### 联邦统计信息
1. 请求方法：GET
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/statistic
3. 返回Body：
```json
{
  "success": true,
  "code": 200,
  "message": "请求成功",
  "data": [
    {
      "name": "等待",
      "value": 2
    },
    {
      "name": "就绪",
      "value": 0
    },
    {
      "name": "运行中",
      "value": 0
    },
    {
      "name": "成功",
      "value": 0
    },
    {
      "name": "失败",
      "value": 0
    }
  ],
  "pageInfo": null,
  "ext": null
}
```

#### 数据集接口
1. 请求方法：GET
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/datasets
3. 返回Body：
```json
{
  "success": true,
  "code": 200,
  "message": "请求成功",
  "data": [
    {
      "name": "aaaaa.csv",
      "updatedAt": 1592454896000,
      "size": "20MB",
      "rows": 100000
    },
    {
      "name": "ddddd.csv",
      "updatedAt": 1592454896000,
      "size": "20MB",
      "rows": 100000
    },
    {
      "name": "ddddd.csv",
      "updatedAt": 1592627696000,
      "size": "5KB",
      "rows": 100
    }
  ],
  "pageInfo": "",
  "ext": ""
}
```

#### 联邦数据集接口
1. 请求方法：GET
2. 请求路径：/com/cmcc/algo/datafusion/api/v1/federations/{uuid}/datasets?type=0
3. 请求参数：

| 字段 | 类型 | 说明 |
| ---- | ---- | ---- |
uuid | string | 联邦uuid
type | string | 类型，0表示训练数据，1表示预测数据 
4. 返回Body：
```json
{
  "success": true,
  "code": 200,
  "message": "请求成功",
  "data": [
    {
      "id": 1,
      "name": "aaaaa.csv",
      "updatedAt": 1592454896000,
      "size": "20MB",
      "rows": 100000,
      "userId": 1,
      "federationUuid": "cb68ca3fe5b8420c9bf940af9db047e1",
      "type": 0 // 
    }
  ],
  "pageInfo": "",
  "ext": ""
}
```
