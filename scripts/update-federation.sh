#!/bin/bash

path=$(dirname $0|xargs realpath)
token=$(cat ${path}/token)

if test $1;then
  uuid=$1
else
  echo "没有指定UUID"
  exit 1
fi

curl localhost:10086/com/cmcc/algo/datafusion/api/v1/federations/${uuid} -H'content-type:application/json' -vvv \
  -H"token: ${token}" -XPUT -d'
{
  "name": "hello world update",
  "description": "try for update",
  "dataFormat": {
    "identity": "test",
    "label": "mylabelforupdate",
    "feature": "1;2;3;4;5"
  },
  "param": {
    "param1": 0.2,
    "param2": 100,
    "param3": -12345.6789
  },
  "algorithmId": 4
}'
