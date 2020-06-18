**数据融合API接口文档**


**简介**：后台API接口

**HOST**:localhost:10086


# user-controller

## 注销用户

**接口描述**:注销用户

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/del/{userId}`


**请求方式**：`DELETE`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------- | -------- | ---- | -------- | -------- | ------ |
| userId   | 用户id   | path | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "注销成功",
    "data": {
        "id": 1,
        "uuid": "5e95e56cd6c8421ca26a28582d4ec6fd",
        "partyId": 0,
        "username": "admin",
        "password": "123",
        "phone": null,
        "email": null,
        "companyPhone": null,
        "companyName": null,
        "address": null,
        "roles": [],
        "token": null,
        "delFlag": 1,
        "federationList": null,
        "joinFederation": null
    },
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
| 204    | No Content   |              |
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
## 用户基础信息详情

**接口描述**:用户基础信息详情

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/detail/{userId}`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------- | -------- | ---- | -------- | -------- | ------ |
| userId   | 用户id   | path | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "查询成功",
    "data": {
        "id": 1,
        "uuid": "5e95e56cd6c8421ca26a28582d4ec6fd",
        "partyId": 0,
        "username": "admin",
        "password": "123",
        "phone": null,
        "email": null,
        "companyPhone": null,
        "companyName": null,
        "address": null,
        "roles": [],
        "token": null,
        "delFlag": 1,
        "federationList": null,
        "joinFederation": null
    },
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
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
| 404    | Not Found    |              |
## 查询用户列表展示

**接口描述**:查询用户列表展示

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/list`


**请求方式**：`GET`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明 | in   | 是否必须 | 数据类型 |      |
| -------- | -------- | ---- | -------- | -------- | ---- |
| user     | 用户列表 |      | false    | string   |      |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "查询成功",
    "data": {
        "records": [
            {
                "id": 1,
                "uuid": "5e95e56cd6c8421ca26a28582d4ec6fd",
                "partyId": 0,
                "username": "admin",
                "password": "123",
                "phone": null,
                "email": null,
                "companyPhone": null,
                "companyName": null,
                "address": null,
                "roles": [],
                "token": null,
                "delFlag": 0,
                "federationList": [
                    {
                        "id": 10,
                        "name": "xiaobai",
                        "uuid": "346f3bf1ff4e49179f7610e0771f56cf",
                        "createdAt": null,
                        "type": false,
                        "description": null,
                        "guest": "1",
                        "hosts": null,
                        "status": 0,
                        "dataFormat": null,
                        "algorithmId": null,
                        "param": null
                    },
                    {
                        "id": 9,
                        "name": "xiaoming",
                        "uuid": "365a8fec7fd24c48b43af5c8c7d37383",
                        "createdAt": null,
                        "type": false,
                        "description": null,
                        "guest": "1",
                        "hosts": null,
                        "status": 0,
                        "dataFormat": null,
                        "algorithmId": null,
                        "param": null
                    }
                ],
                "joinFederation": [
                    {
                        "id": 9,
                        "name": "xiaoming",
                        "uuid": "365a8fec7fd24c48b43af5c8c7d37383",
                        "createdAt": null,
                        "type": false,
                        "description": null,
                        "guest": "1",
                        "hosts": null,
                        "status": 0,
                        "dataFormat": null,
                        "algorithmId": null,
                        "param": null
                    },
                    {
                        "id": 10,
                        "name": "xiaobai",
                        "uuid": "346f3bf1ff4e49179f7610e0771f56cf",
                        "createdAt": null,
                        "type": false,
                        "description": null,
                        "guest": "1",
                        "hosts": null,
                        "status": 0,
                        "dataFormat": null,
                        "algorithmId": null,
                        "param": null
                    }
                ]
            },
            {
                "id": 2,
                "uuid": "d291d89bb5754e38808d29647dd42745",
                "partyId": 0,
                "username": "heiyao",
                "password": "123",
                "phone": null,
                "email": null,
                "companyPhone": null,
                "companyName": null,
                "address": null,
                "roles": [],
                "token": null,
                "delFlag": 0,
                "federationList": [],
                "joinFederation": []
            }
        ],
        "total": 2,
        "size": 10,
        "current": 1,
        "searchCount": true,
        "pages": 1
    },
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
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
| 404    | Not Found    |              |
## 登录

**接口描述**:登录

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/login`

**请求方式**：`POST`


**consumes**:`["application/json"]`

**produces**:`["*/*"]`



**请求参数**：

| 参数名称  | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| --------- | -------- | ---- | -------- | -------- | ------ |
| loginUser | 登录用户 | body | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "登陆成功！",
    "data": {
        "id": 1,
        "uuid": "5e95e56cd6c8421ca26a28582d4ec6fd",
        "partyId": 0,
        "username": "admin",
        "password": "123",
        "phone": null,
        "email": null,
        "companyPhone": null,
        "companyName": null,
        "address": null,
        "roles": [],
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1OTI0NDQ3MTMsImV4cCI6MTU5MjQ5NTk5OX0.sPaw-CL0_lnriZHuxQoIvYQywSRLGQlIuONOxmetY88",
        "delFlag": 0,
        "federationList": null,
        "joinFederation": null
    },
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
# user-federation-controller

## 审批申请中成员

**接口描述**:审批申请中成员

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/access`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称       | 参数说明       | in    | 是否必须 | 数据类型 | schema |
| -------------- | -------------- | ----- | -------- | -------- | ------ |
| federationUUid | federationUUid | query | true     | string   |        |
|                |                |       |          |          |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": "加入成功",
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
## 申请加入

**接口描述**:申请加入

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/apply`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称       | 参数说明       | in     | 是否必须 | 数据类型 | schema |
| -------------- | -------------- | ------ | -------- | -------- | ------ |
| federationUUid | federationUUid | query  | true     | string   |        |
| federationUuid | 联邦UUID       |        | false    | string   |        |
| token          | 头部token信息  | header | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": "申请加入成功，待审批！",
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
## 删除联邦现有用户

**接口描述**:删除联邦现有用户

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/delete`

**请求方式**：`DELETE`


**consumes**:``


**produces**:`["*/*"]`



**请求参数**：

| 参数名称 | 参数说明 | in     | 是否必须 | 数据类型 | schema |
| -------- | -------- | ------ | -------- | -------- | ------ |
| token    | 联邦UUID | header | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "删除成功",
    "data": {
        "id": 1,
        "uuid": "5e95e56cd6c8421ca26a28582d4ec6fd",
        "partyId": 0,
        "username": "admin",
        "password": "123",
        "phone": null,
        "email": null,
        "companyPhone": null,
        "companyName": null,
        "address": null,
        "roles": [],
        "token": null,
        "delFlag": 4,
        "federationList": null,
        "joinFederation": null
    },
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
| 204    | No Content   |              |
| 401    | Unauthorized |              |
| 403    | Forbidden    |              |
## 我的联邦成员列表

**接口描述**:我的联邦成员列表

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/list`


**请求方式**：`POST`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称       | 参数说明       | in    | 是否必须 | 数据类型 | schema |
| -------------- | -------------- | ----- | -------- | -------- | ------ |
| federationUUid | federationUUid | query | true     | string   |        |
| federationUuid | 联邦UUID       |       | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "查询成功",
    "data": {
        "id": 1,
        "uuid": "5e95e56cd6c8421ca26a28582d4ec6fd",
        "partyId": 0,
        "username": "admin",
        "password": "123",
        "phone": null,
        "email": null,
        "companyPhone": null,
        "companyName": null,
        "address": null,
        "roles": [],
        "token": null,
        "delFlag": 0,
        "federationList": null,
        "joinFederation": null
    },
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
| request  | 请求jsonStr，包括'federationType'和'algorithmType'两个字段 | body   | false    | string   |        |
| token    | 头部token信息                                              | header | false    | string   |        |

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