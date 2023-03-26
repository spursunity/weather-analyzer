# Java Spring test project "Weather analyzer"

## App implements:

### Schedule Task:
It requests periodically data from this API (https://rapidapi.com/weatherapi/api/weatherapi-com).

### 'Current weather' endpoint (method = `GET`)
It returns last weather data.

### 'Average weather values' endpoint (method = `GET`)
It returns average weather values. May contain 2 query params `from` and `to`.
Both are optional, but if `to` param specified, then `from` param is required.

### 'Average weather values' endpoint (method = `POST`)
It returns average weather values.
Request body should contain `from` field and may contain `to` param.