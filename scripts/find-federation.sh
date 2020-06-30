#!/bin/bash

path=$(dirname $0|xargs realpath)
token=$(cat ${path}/token)

curl "localhost:10086/com/cmcc/algo/datafusion/api/v1/federations?name=$1" -H'content-type:application/json' -vvv \
  -H"token: ${token}"
