#!/bin/bash

path=$(dirname $0|xargs realpath)
token=$(cat ${path}/token)

suffix=
if test $1;then
  suffix="/${1}"
fi

curl localhost:10086/com/cmcc/algo/datafusion/api/v1/federations${suffix} -H'content-type:application/json' -vvv \
  -H"token: ${token}"
