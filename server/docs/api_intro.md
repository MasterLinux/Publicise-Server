#Default header

##Content-Type
```
Content-Type: application/json
```

##X-Pretty-Print
Pretty printing for responses is enabled by default. Whenever you want to disable pretty printing just use the `X-Pretty-Print` header.

```
X-Pretty-Print: false
```


#Error handling
Whenever an error occurred an error response with the following schema is returned.

```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "id": "/",
  "type": "object",
  "properties": {
    "errorCode": {
      "id": "errorCode",
      "type": "integer"
    },
    "errorMessage": {
      "id": "errorMessage",
      "type": "string"
    },
    "errorName": {
      "id": "errorName",
      "type": "string"
    },
    "statusCode": {
      "id": "statusCode",
      "type": "integer"
    }
  },
  "required": [
    "errorCode",
    "errorMessage",
    "errorName",
    "statusCode"
  ]
}
```

For example:
```json
{
    "errorCode": 52224,
    "errorMessage": "Connection to database could not be established",
    "errorName": "ERROR_DB_CONNECTION_FAILED",
    "statusCode": 503
}
```