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

| 参数名称 | 参数说明                                                     | in     | 是否必须 | 数据类型 | schema |
| -------- | ------------------------------------------------------------ | ------ | -------- | -------- | ------ |
| request  | 请求jsonStr，包括'federation_type'和'algorithm_type'两个字段 | body   | false    | string   |        |
| token    | 头部token信息                                                | header | false    | string   |        |

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
| request  | 请求jsonStr，包括'federationUuid'和分页参数（可选）'pageNum','step' | body   | false    | string   |        |
| token    | 头部token信息                                                | header | false    | string   |        |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"ext": {},
	"message": "",
	"pageInfo": {
		"pageNum": 0,
		"pages": 0,
		"step": 0,
		"total": 0
	},
	"success": true
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

| 参数名称       | 参数说明      | in     | 是否必须 | 数据类型 | schema |
| -------------- | ------------- | ------ | -------- | -------- | ------ |
| federationUuid | 联邦UUID      | body   | false    | string   |        |
| token          | 头部token信息 | header | false    | string   |        |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"ext": {},
	"message": "",
	"pageInfo": {
		"pageNum": 0,
		"pages": 0,
		"step": 0,
		"total": 0
	},
	"success": true
}
```

**响应参数**:


暂无





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

| 参数名称    | 参数说明      | in     | 是否必须 | 数据类型 | schema |
| ----------- | ------------- | ------ | -------- | -------- | ------ |
| predictUuid | 预测记录UUID  | body   | false    | string   |        |
| token       | 头部token信息 | header | false    | string   |        |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"ext": {},
	"message": "",
	"pageInfo": {
		"pageNum": 0,
		"pages": 0,
		"step": 0,
		"total": 0
	},
	"success": true
}
```

**响应参数**:


暂无





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
| request  | 请求jsonStr，包括'federationUuid'和分页参数（可选）'pageNum','step' | body   | false    | string   |        |
| token    | 头部token信息                                                | header | false    | string   |        |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"ext": {},
	"message": "",
	"pageInfo": {
		"pageNum": 0,
		"pages": 0,
		"step": 0,
		"total": 0
	},
	"success": true
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

| 参数名称       | 参数说明      | in     | 是否必须 | 数据类型 | schema |
| -------------- | ------------- | ------ | -------- | -------- | ------ |
| federationUuid | 联邦UUID      | body   | false    | string   |        |
| token          | 头部token信息 | header | false    | string   |        |

**响应示例**:

```json
{
	"code": 0,
	"data": {},
	"ext": {},
	"message": "",
	"pageInfo": {
		"pageNum": 0,
		"pages": 0,
		"step": 0,
		"total": 0
	},
	"success": true
}
```

**响应参数**:


暂无





**响应状态**:


| 状态码 | 说明         | schema |
| ------ | ------------ | ------ |
| 200    | OK           |        |
| 201    | Created      |        |
| 401    | Unauthorized |        |
| 403    | Forbidden    |        |
| 404    | Not Found    |        |