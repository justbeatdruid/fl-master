**数据融合API接口文档**


**简介**：后台API接口

**HOST**:localhost:10088


**联系人**:


**Version**:1.0

**接口路径**：/v2/api-docs


# 算法接口

## 获取算法列表

**接口描述**:获取算法列表

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/algorithm/list`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明                                                   | in     | 是否必须 | 数据类型 | schema |
| -------- | ---------------------------------------------------------- | ------ | -------- | -------- | ------ |
| request  | 请求jsonStr，包括'federationType'和'algorithmType'两个字段 | body   | true     | string   |        |
| token    | 头部token信息                                              | header | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": [
        {
            "id": 1,
            "algorithmName": "HeteroLR",
            "displayName": "纵向逻辑回归",
            "federationType": 0,
            "algorithmType": 0,
            "param": "[{"paramName":"learning_rate","displayName":"学习率","defaultValue":0.015,"validRule":{"min":0.001,"max":100}},{"paramName":"loss","displayName":"最终损失","defaultValue":0.00001,"validRule":{"min":0,"max":1}},{"paramName":"iteration","displayName":"迭代次数","defaultValue":100,"validRule":{"min":10,"max":1000}}]",
            "algorithmDesc": null,
            "template": "HeteroLR.ftl"
        }
    ],
    "pageInfo": null,
    "ext": null
}
```

**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 状态码   | integer(int32) | integer(int32) |
| data     | 返回内容 | object         |                |
| ext      | 其他信息 | object         |                |
| message  | 返回消息 | string         |                |
| pageInfo | 分页信息 | PageInfo       | PageInfo       |
| success  | 成功标志 | boolean        |                |



**schema属性说明**




**PageInfo**

| 参数名称 | 参数说明     | 类型           | schema |
| -------- | ------------ | -------------- | ------ |
| pageNum  | 当前页       | integer(int64) |        |
| pages    | 总页数       | integer(int64) |        |
| step     | 每页展示条数 | integer(int64) |        |
| total    | 总记录数     | integer(int64) |        |

**响应状态**:


| 状态码 | 说明         | schema       |
| ------ | ------------ | ------------ |
| 200    | OK           | CommonResult |
| 201    | Created      |              |
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
| 404    | Not Found    |              |


## 根据ID查询算法

**接口地址** `/com/cmcc/algo/datafusion/api/v1/algorithm/one`


**请求方式** `POST`


**consumes** `["application/json"]`


**produces** `["*/*"]`


**接口描述** `根据ID查询算法`

**请求参数**

| 参数名称    | 参数说明      | 请求类型 | 是否必须 | 数据类型 | schema |
| ----------- | ------------- | -------- | -------- | -------- | ------ |
| algorithmId | 算法ID        | body     | true     | Integer  |        |
| token       | 头部token信息 | header   | true     | string   |        |




**响应状态**

| 状态码 | 说明         | schema       |
| ------ | ------------ | ------------ |
| 200    | OK           | CommonResult |
| 201    | Created      |              |
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
| 404    | Not Found    |              |




**响应参数**

| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 状态码   | integer(int32) | integer(int32) |
| data     | 返回内容 | object         |                |
| ext      | 其他信息 | object         |                |
| message  | 返回消息 | string         |                |
| pageInfo | 分页信息 | PageInfo       | PageInfo       |
| success  | 成功标志 | boolean        |                |




**schema属性说明**

**PageInfo**

| 参数名称 | 参数说明     | 类型           | schema |
| -------- | ------------ | -------------- | ------ |
| pageNum  | 当前页       | integer(int64) |        |
| pages    | 总页数       | integer(int64) |        |
| step     | 每页展示条数 | integer(int64) |        |
| total    | 总记录数     | integer(int64) |        |



**响应示例**


```json
{
	"code": 200,
	"data": {
            "id": 1,
            "algorithmName": "HeteroLR",
            "displayName": "纵向逻辑回归",
            "federationType": 0,
            "algorithmType": 0,
            "param": "[{"paramName":"learning_rate","displayName":"学习率","defaultValue":0.015,"validRule":{"min":0.001,"max":100}},{"paramName":"loss","displayName":"最终损失","defaultValue":0.00001,"validRule":{"min":0,"max":1}},{"paramName":"iteration","displayName":"迭代次数","defaultValue":100,"validRule":{"min":10,"max":1000}}]",
            "algorithmDesc": null,
            "template": "HeteroLR.ftl"
        },
	"ext": {},
	"message": "请求成功",
	"pageInfo": {
		"pageNum": 0,
		"pages": 0,
		"step": 0,
		"total": 0
	},
	"success": true
}
```

# 训练任务接口

## 查询训练记录

**接口描述**:查询训练记录

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/train/list`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明                                                     | in     | 是否必须 | 数据类型 | schema |
| -------- | ------------------------------------------------------------ | ------ | -------- | -------- | ------ |
| request  | 请求jsonStr，包括'federationUuid'和分页参数（可选）'pageNum','step' | body   | true     | string   |        |
| token    | 头部token信息                                                | header | true     | string   |        |

**请求示例**:

```json
{
    "federationUuid":"98a2984d5e9d4c6ab94d0770767a366e"
}
```

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": [
        {
            "id": 1,
            "uuid": "98a2984d5e9d4c6ab94d0770767a35a8",
            "federationUuid": "98a2984d5e9d4c6ab94d0770767a366e",
            "status": 0,
            "jobUrl": "https:fateboard/9876567891.com",
            "startTime": "2020-06-22 16:18:26",
            "duration": "2min30s",
            "auc": "",
            "accuracy": "",
            "algorithmId": "",
            "trainParam": "{\"param1\":0.05,\"param2\":0.0001,\"param3\":100.0}",
            "model": ""
        },
        {
            "id": 3,
            "uuid": "571d884d5e9d4c6ab94d0770767a35a8",
            "federationUuid": "98a2984d5e9d4c6ab94d0770767a366e",
            "status": 1,
            "jobUrl": "https:fateboard/9876567890.com",
            "startTime": "2020-06-20 20:34:58",
            "duration": "11min7s",
            "auc": 1.0,
            "accuracy": 1.0,
            "algorithmId": 1,
            "trainParam": "{\"param1\":0.035,\"param2\":0.01001,\"param3\":100.0}",
            "model": "{\r\n    \"model_id\": \"arbiter-10000#guest-10000#host-10000#model\",\r\n    \"model_version\": \"20190810154805024303_1\"\r\n}"
        }
    ],
    "pageInfo": {
        "pages": 1,
        "total": 2,
        "pageNum": 1,
        "step": 10
    },
    "ext": ""
}
```

**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 状态码   | integer(int32) | integer(int32) |
| data     | 返回内容 | object         |                |
| ext      | 其他信息 | object         |                |
| message  | 返回消息 | string         |                |
| pageInfo | 分页信息 | PageInfo       | PageInfo       |
| success  | 成功标志 | boolean        |                |



**schema属性说明**




**PageInfo**

| 参数名称 | 参数说明     | 类型           | schema |
| -------- | ------------ | -------------- | ------ |
| pageNum  | 当前页       | integer(int64) |        |
| pages    | 总页数       | integer(int64) |        |
| step     | 每页展示条数 | integer(int64) |        |
| total    | 总记录数     | integer(int64) |        |

**响应状态**:


| 状态码 | 说明         | schema       |
| ------ | ------------ | ------------ |
| 200    | OK           | CommonResult |
| 201    | Created      |              |
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
| 404    | Not Found    |              |
## 开始训练

**接口描述**:开始训练

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/train/submit`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明             | in     | 是否必须 | 数据类型 | schema |
| -------- | -------------------- | ------ | -------- | -------- | ------ |
| request  | 包含'federationUuid' | body   | true     | string   |        |
| token    | 头部token信息        | header | true     | string   |        |

**请求示例**:

```json
{
    "federationUuid":"98a2984d5e9d4c6ab94d0770767a366e"
}
```

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": true,
    "pageInfo": "",
    "ext": ""
}
```

**响应参数**:

| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 状态码   | integer(int32) | integer(int32) |
| data     | 返回内容 | object         |                |
| ext      | 其他信息 | object         |                |
| message  | 返回消息 | string         |                |
| pageInfo | 分页信息 | PageInfo       | PageInfo       |
| success  | 成功标志 | boolean        |                |

**响应状态**:


| 状态码 | 说明         | schema |
| ------ | ------------ | ------ |
| 200    | OK           |        |
| 201    | Created      |        |
| 401    | Unauthorized |        |
| 403    | Forbidden    |        |
| 404    | Not Found    |        |
# 预测任务接口


## 导出结果

**接口描述**:导出结果

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/predict/export`


**请求方式**：`POST`

**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明          | in     | 是否必须 | 数据类型 | schema |
| -------- | ----------------- | ------ | -------- | -------- | ------ |
| request  | 包含'predictUuid' | body   | true     | string   |        |
| token    | 头部token信息     | header | true     | string   |        |

**请求示例**:

```json
{
    "predictUuid":"98a2984d5e9d4c6ab94d0770767a366e"
}
```

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": "/output/path",
    "pageInfo": "",
    "ext": ""
}
```

**响应参数**:

| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 状态码   | integer(int32) | integer(int32) |
| data     | 返回内容 | object         |                |
| ext      | 其他信息 | object         |                |
| message  | 返回消息 | string         |                |
| pageInfo | 分页信息 | PageInfo       | PageInfo       |
| success  | 成功标志 | boolean        |                |





**响应状态**:


| 状态码 | 说明         | schema |
| ------ | ------------ | ------ |
| 200    | OK           |        |
| 201    | Created      |        |
| 401    | Unauthorized |        |
| 403    | Forbidden    |        |
| 404    | Not Found    |        |
## 查询预测记录

**接口描述**:查询预测记录

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/predict/list`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明                                                     | in     | 是否必须 | 数据类型 | schema |
| -------- | ------------------------------------------------------------ | ------ | -------- | -------- | ------ |
| request  | 请求jsonStr，包括'federationUuid'和分页参数（可选）'pageNum','step' | body   | true     | string   |        |
| token    | 头部token信息                                                | header | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": [
        {
            "id": 1,
            "uuid": "98a2984d5e9d4c6ab94d077075c2366e",
            "federationUuid": "98a2984d5e9d4c6ab94d0770767a366e",
            "trainUuid": "571d884d5e9d4c6ab94d0770767a35a8",
            "algorithmId": 1,
            "status": 0,
            "jobUrl": "https:fateboard/2345154321",
            "startTime": "2020-06-23 17:12:33",
            "duration": "1min5s",
            "predictParam": "{\"param1\":0.035,\"param2\":0.01001,\"param3\":100.0}",
            "jobId": "",
            "outputPath": ""
        },
        {
            "id": 2,
            "uuid": "98a2984d5e9d4c6ab94d07707194366e",
            "federationUuid": "98a2984d5e9d4c6ab94d0770767a366e",
            "trainUuid": "571d884d5e9d4c6ab94d0770767a35a8",
            "algorithmId": 1,
            "status": 1,
            "jobUrl": "https:fateboard/2345154320",
            "startTime": "2020-06-22 21:05:44",
            "duration": "8min37s",
            "predictParam": "{\"param1\":0.035,\"param2\":0.01001,\"param3\":100.0}",
            "jobId": "20190810154805024303",
            "outputPath": "/root/output"
        }
    ],
    "pageInfo": {
        "pages": 1,
        "total": 2,
        "pageNum": 1,
        "step": 10
    },
    "ext": ""
}
```

**响应参数**:


| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 状态码   | integer(int32) | integer(int32) |
| data     | 返回内容 | object         |                |
| ext      | 其他信息 | object         |                |
| message  | 返回消息 | string         |                |
| pageInfo | 分页信息 | PageInfo       | PageInfo       |
| success  | 成功标志 | boolean        |                |



**schema属性说明**




**PageInfo**

| 参数名称 | 参数说明     | 类型           | schema |
| -------- | ------------ | -------------- | ------ |
| pageNum  | 当前页       | integer(int64) |        |
| pages    | 总页数       | integer(int64) |        |
| step     | 每页展示条数 | integer(int64) |        |
| total    | 总记录数     | integer(int64) |        |

**响应状态**:


| 状态码 | 说明         | schema       |
| ------ | ------------ | ------------ |
| 200    | OK           | CommonResult |
| 201    | Created      |              |
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
| 404    | Not Found    |              |
## 开始预测

**接口描述**:开始预测

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/predict/submit`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明             | in     | 是否必须 | 数据类型 | schema |
| -------- | -------------------- | ------ | -------- | -------- | ------ |
| request  | 包含'federationUuid' | body   | true     | string   |        |
| token    | 头部token信息        | header | true     | string   |        |

**请求示例**:

```json
{
    "federationUuid":"98a2984d5e9d4c6ab94d0770767a366e"
}
```

响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": true,
    "pageInfo": "",
    "ext": ""
}
```

**响应参数**:

| 参数名称 | 参数说明 | 类型           | schema         |
| -------- | -------- | -------------- | -------------- |
| code     | 状态码   | integer(int32) | integer(int32) |
| data     | 返回内容 | object         |                |
| ext      | 其他信息 | object         |                |
| message  | 返回消息 | string         |                |
| pageInfo | 分页信息 | PageInfo       | PageInfo       |
| success  | 成功标志 | boolean        |                |



**响应状态**:


| 状态码 | 说明         | schema |
| ------ | ------------ | ------ |
| 200    | OK           |        |
| 201    | Created      |        |
| 401    | Unauthorized |        |
| 403    | Forbidden    |        |
| 404    | Not Found    |        |