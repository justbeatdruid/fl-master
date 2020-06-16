#!/bin/bash

path=$(dirname $0|xargs realpath)
token=$(cat ${path}/token)

curl localhost:10086/com/cmcc/algo/datafusion/api/v1/federations -H'content-type:application/json' -vvv \
  -H"token: ${token}" -d'
{
  "name": "hello world again",
  "description": "hello",
  "dataFormat": {
    "identity": "test",
    "label": "mylabel",
    "feature": "1;2;3;4;5"
  },
  "param": {
    "param1": 0.1,
    "param2": 100,
    "param3": -12345.6789
  },
  "algorithmId": 4
}'
