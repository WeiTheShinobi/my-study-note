# *《Designing Web APIs》*

> 筆記：Wei the Shinobi

## API 模式

當 API 開始被使用後，API 將難以更動

- REST

與資源有關，用於 CRUD

- RPC

與動作有關，通常含有準備執行的名稱

- GraphQL

讓用戶端定義需要的東西，靈活負擔小

### 事件驅動 API

- WebHook：觸發伺服器提供即時事件
- WebSocket：雙向溝通
- HTTP Streaming：單向通訊

