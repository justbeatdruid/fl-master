数据融合API接口文档**


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
    "message": "请求成功",
    "data": "注销成功",
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
|          |          |      |          |          |      |
|          |          |      |          |          |      |

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
## 按用户名搜索

**接口描述**:用户名搜索

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/select`

**请求方式**：`GET`

| 参数名   | 描述                 |      |
| -------- | -------------------- | ---- |
| username | 只针对中文名模糊查询 |      |



# user-federation-controller

## 审批申请中成员

**接口描述**:审批申请中成员

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/access`


**请求方式**：`PUT`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称       | 参数说明       | in    | 是否必须 | 数据类型 | schema |
| -------------- | -------------- | ----- | -------- | -------- | ------ |
| federationUUid | federationUUid | query | true     | string   |        |
| type           | 同意:1,拒绝:2  |       |          | string   |        |
| userId         | 用户id         |       |          | int      |        |

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

| 参数名称       | 参数说明 | in     | 是否必须 | 数据类型 | schema |
| -------------- | -------- | ------ | -------- | -------- | ------ |
| federationUUid | 联邦UUid | query  | true     | string   |        |
| token          | 头部信息 | header | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": "申请加入成功！",
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

| 参数名称       | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------------- | -------- | ---- | -------- | -------- | ------ |
| userId         | 用户id   |      |          | string   |        |
| federationUUid | 联邦id   |      |          | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": "删除成功",
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

**请求方式**：`GET`


**consumes**:`["application/json"]`


**produces**:`["*/*"]`



**请求参数**：

| 参数名称       | 参数说明    | in   | 是否必须 | 数据类型 | schema |
| -------------- | ----------- | ---- | -------- | -------- | ------ |
| status         | 0：现有成员 |      | true     | string   |        |
| federationUUid | 联邦id      |      |          | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": [],
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
