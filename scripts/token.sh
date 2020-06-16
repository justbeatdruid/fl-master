curl localhost:10086/com/cmcc/algo/datafusion/api/v1/user/login -H'content-type:application/json' \
  -d'
{
  "username": "admin",
  "password": "123"
}' 2>/dev/null | jq -r .data.token > scripts/token
