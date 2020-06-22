#!/bin/bash

path=$(dirname $0|xargs realpath)
token=$(cat ${path}/token)

curl "localhost:10086/com/cmcc/algo/datafusion/api/v1/federations/$1/datasets?type=$2" -H'content-type:application/json' -vvv \
  -H"token: ${token}" \
  -d"{\"id\": $3}"
