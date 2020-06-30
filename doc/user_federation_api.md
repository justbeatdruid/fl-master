**数据融合API接口文档**


**简介**：后台API接口

**HOST**:localhost:10086


**联系人**:

**Version**:1.0

**接口路径**：/v2/api-docs



# 用户操作接口

## 注销用户


**接口描述**:

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/del/{userId}`


**请求方式**：`DELETE`


**consumes**:``

**produces**:`["*/*"]`

**请求参数**：

| 参数名称 | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------- | -------- | ---- | -------- | -------- | ------ |
| userId   | 用户ID   | path | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "注销成功",
    "data": [],
    "pageInfo": "",
    "ext": ""
}
```



## 用户基础信息详情


**接口描述**:


**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/detail/{userId}`


**请求方式**：`GET`


**consumes**:``

**produces**:`["*/*"]`

**请求参数**：

| 参数名称 | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------- | -------- | ---- | -------- | -------- | ------ |
| userId   | 用户ID   | path | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "查询成功",
    "data": {
        "id": 3,
        "uuid": "622ca575bbbd4c498a2ab53a11967d26",
        "partyId": "",
        "username": "小白",
        "password": "123",
        "phone": "",
        "email": "",
        "companyPhone": "",
        "companyName": "",
        "address": "",
        "role": "host",
        "token": "",
        "permissionCode": [],
        "delFlag": 0,
        "createdFederation": [],
        "partakeFederation": []
    },
    "pageInfo": "",
    "ext": ""
}
```



## 查询用户列表


**接口描述**:


**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/list`


**请求方式**：`GET`


**consumes**:``

**produces**:`["*/*"]`

**请求参数**：
暂无

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
                "partyId": "",
                "username": "admin",
                "password": "123",
                "phone": "",
                "email": "",
                "companyPhone": "",
                "companyName": "",
                "address": "",
                "role": "",
                "token": "",
                "permissionCode": [],
                "delFlag": 0,
                "createdFederation": [
                    {
                        "id": 10,
                        "name": "xiaobaifederation",
                        "uuid": "346f3bf1ff4e49179f7610e0771f56cf",
                        "createdAt": "",
                        "type": false,
                        "description": "",
                        "guest": "1",
                        "hosts": "",
                        "status": 0,
                        "dataFormat": "",
                        "algorithmId": "",
                        "param": ""
                    }
                ],
                "partakeFederation": [
                    {
                        "id": 11,
                        "name": "ss",
                        "uuid": "cb68ca3fe5b8420c9bf940af9db047e1",
                        "createdAt": "",
                        "type": false,
                        "description": "",
                        "guest": "1",
                        "hosts": "",
                        "status": 0,
                        "dataFormat": "",
                        "algorithmId": "",
                        "param": ""
                    }
                ]
            }
        ],
        "total": 4,
        "size": 10,
        "current": 1,
        "searchCount": true,
        "pages": 1
    },
    "pageInfo": "",
    "ext": ""
}
```



## 用户登录


**接口描述**:


**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/login`


**请求方式**：`POST`


**consumes**:`["application/json"]`

**produces**:`["*/*"]`

**请求参数**：

| 参数名称 | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------- | -------- | ---- | -------- | -------- | ------ |
| username | 用户名   | body | true     | string   |        |
| password | 密码     | body | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "登陆成功！",
    "data": {
        "id": 4,
        "uuid": "880636f45ed946c688647db85b6214f7",
        "partyId": "",
        "username": "小红",
        "password": "123",
        "phone": "",
        "email": "",
        "companyPhone": "",
        "companyName": "",
        "address": "",
        "role": "",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0Iiwic3ViIjoi5bCP57qiIiwiaWF0IjoxNTkzNDE2OTQ5LCJleHAiOjE1OTM0NDYzOTl9.LalA6Qhq8nUI9RKZriMbPKQgS7UKmKhd8Bd3WHxBoWk",
        "permissionCode": [],
        "delFlag": 0,
        "createdFederation": [],
        "partakeFederation": []
    },
    "pageInfo": "",
    "ext": ""
}
```



## 用户搜索


**接口描述**:


**接口地址**:`/com/cmcc/algo/datafusion/api/v1/user/select`


**请求方式**：`GET`

**consumes**:``

**produces**:`["*/*"]`

**请求参数**：

| 参数名称 | 参数说明                 | in   | 是否必须 | 数据类型 | schema |
| -------- | ------------------------ | ---- | -------- | -------- | ------ |
| username | 用户名只针对中文模糊查询 | path | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "查询成功",
    "data": {
        "records": [
            {
                "id": 2,
                "uuid": "d291d89bb5754e38808d29647dd42745",
                "partyId": "",
                "username": "小黑",
                "password": "123",
                "phone": "",
                "email": "",
                "companyPhone": "",
                "companyName": "",
                "address": "",
                "role": "",
                "token": "",
                "permissionCode": [],
                "delFlag": 0,
                "createdFederation": [],
                "partakeFederation": []
            }
        ],
        "total": 1,
        "size": 10,
        "current": 1,
        "searchCount": true,
        "pages": 1
    },
    "pageInfo": "",
    "ext": ""
}
```



# 用户联邦操作接口

## 申请加入联邦


**接口描述**:


**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/apply`


**请求方式**：`POST`


**consumes**:`["application/json"]`

**produces**:`["*/*"]`

**请求参数**：

| 参数名称       | 参数说明      | in     | 是否必须 | 数据类型 | schema |
| -------------- | ------------- | ------ | -------- | -------- | ------ |
| federationUUid | 联邦UUID      | body   | true     | string   |        |
| token          | 头部token信息 | header | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "申请加入成功",
    "data": [],
    "pageInfo": "",
    "ext": ""
}
```



## 删除联邦现有用户


**接口描述**:


**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/delete`


**请求方式**：`DELETE`


**consumes**:``

**produces**:`["*/*"]`

**请求参数**：

| 参数名称       | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------------- | -------- | ---- | -------- | -------- | ------ |
| userId         | 用户ID   | body | true     | string   |        |
| federationUUid | 联邦UUID | body | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "删除成功",
    "data": [],
    "pageInfo": "",
    "ext": ""
}
```



## 我的联邦


**接口描述**:


**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/list`


**请求方式**：`GET`


**consumes**:``

**produces**:`["*/*"]`

**请求参数**：

| 参数名称       | 参数说明 | in   | 是否必须 | 数据类型 | schema |
| -------------- | -------- | ---- | -------- | -------- | ------ |
| federationUUid | 联邦UUID | path | true     | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "请求成功",
    "data": [
        {
            "id": 53,
            "userId": 2,
            "federationUUid": "46e797e1881a48f4a10b0865f1521226",
            "status": "0",
            "user": {
                "id": 2,
                "uuid": "d291d89bb5754e38808d29647dd42745",
                "partyId": "",
                "username": "小建",
                "password": "123",
                "phone": "",
                "email": "",
                "companyPhone": "",
                "companyName": "",
                "address": "",
                "role": "guest",
                "token": "",
                "permissionCode": [],
                "delFlag": 0,
                "createdFederation": [],
                "partakeFederation": []
            }
        }
    ],
    "pageInfo": "",
    "ext": ""
}
```



## 退出联邦


**接口描述**:

**接口地址**:`/com/cmcc/algo/datafusion/api/v1/userFederation/logout


**请求方式**：`DELETE`


**consumes**:``

**produces**:`["*/*"]`

**请求参数**：

| 参数名称       | 参数说明      | in     | 是否必须 | 数据类型 | schema |
| -------------- | ------------- | ------ | -------- | -------- | ------ |
| federationUUid | 联邦UUID      | path   | true     | string   |        |
| token          | 头部token信息 | header | false    | string   |        |

**响应示例**:

```json
{
    "success": true,
    "code": 200,
    "message": "退出成功",
    "data": [],
    "pageInfo": "",
    "ext": ""
}
```








