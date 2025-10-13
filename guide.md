## Module 3 API test guide

Base URL: http://localhost:8080. Bodies are JSON. Follow steps in order.

### 0) Seed data (do this first)
These endpoints are public to simplify setup.

1. Create a mode
   - POST `/modes`
   - Body:
```
{ "name": "easy" }
```

2. Create some cards (at least 2 that form a pair theme)
   - POST `/cards`
   - Body example (image optional for now):
```
{ "name": "apple", "dataImage": null }
```
   - Repeat with another name, e.g. `apple_pair`.

3. Link cards to the mode using `card_set`
   - POST `/card-sets?modeId=1&cardId=<cardId1>`
   - POST `/card-sets?modeId=1&cardId=<cardId2>`

Notes:
- If `/game/create` fails with code 2002 (CARD_SET_NOT_FOUND), add more `card_set` rows for the chosen mode.
- If it fails with 2001 (MODE_NOT_FOUND), verify the mode ID you pass exists.

### 1) Register users (once)
POST `/users`
Body:
```
{
  "username": "alice",
  "password": "alicepassword"
}
```
Repeat for a second user (e.g., `bob`).

### 2) Login to get tokens
POST `/auth/log-in`
Body:
```
{
  "username": "alice",
  "password": "alicepassword"
}
```
Response contains `token`.

Use this header for authenticated requests:
Authorization: Bearer <token>

### 3) Create a game (public endpoint)
POST `/game/create?modeId=1&userAId=<aliceId>&userBId=<bobId>`

Response:
```
{
  "code": 1000,
  "result": { "gameBoardId": 123 }
}
```

### 4) Get current scores (auth required)
GET `/game/{gameBoardId}/scores`
Headers: Authorization: Bearer <token>

### 5) Update score (auth required)
POST `/game/{gameBoardId}/score?userId=<playerId>&delta=20`
Headers: Authorization: Bearer <token>
Tip: use `delta=20` for a matched pair; negative to deduct if needed.

### 6) End game (auth required)
POST `/game/{gameBoardId}/end`
Headers: Authorization: Bearer <token>

### Optional auth utilities
- POST `/auth/refresh` body `{ "token": "<oldToken>" }`
- POST `/auth/logout` body `{ "token": "<token>" }`


