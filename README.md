# Wallet Service - API Testing Guide

## Prerequisites

- Java and Maven installed
- All microservices running locally on their respective ports:
  - user-service: 8081
  - wallet-service: 8082
  - transaction-service: 8083
  - query-service: 8080
- [Postman](https://www.postman.com/) installed (or use curl/another HTTP client)

## Step-by-Step Testing Instructions

### 1. Add Users
Send a `POST` request to `http://localhost:8081/user-service/user` with the following body:
```json
{
  "name": "joao",
  "email": "joao@email.com"
}
```
Repeat for other users (e.g., "maria").
**Note:** The response will contain the `userId` needed for wallet creation.

### 2. Create Wallets
Send a `POST` request to `http://localhost:8082/wallet-service/wallet` with the following body:
```json
{
  "userId": "<userId-from-previous-step>"
}
```
Do this for each user.
**Note:** The response will contain the `walletId` needed for transactions.

### 3. Deposit Funds
Send a `POST` request to `http://localhost:8083/transaction-service/transaction` with the following body:
```json
{
  "fromWalletId": "<walletId>",
  "type": "DEPOSIT",
  "amount": 20
}
```

### 4. Withdraw Funds
Send a `POST` request to `http://localhost:8083/transaction-service/transaction` with the following body:
```json
{
  "fromWalletId": "<walletId>",
  "type": "WITHDRAW",
  "amount": 2
}
```

### 5. Transfer Funds
Send a `POST` request to `http://localhost:8083/transaction-service/transaction` with the following body:
```json
{
  "fromWalletId": "<walletId-from>",
  "toWalletId": "<walletId-to>",
  "type": "TRANSFER",
  "amount": 3
}
```

### 6. Query Wallet Balance
Send a `GET` request to:
```
http://localhost:8080/query-service/wallet/<walletId>/balance?date=YYYY-MM-DD
```
Replace `<walletId>` and `date` as needed to retrieve current or historical balances.

## Available APIs

### user-service

#### POST /user-service/user
- **Description:** Creates a new user. Returns the user ID as plain text.
- **Request body:**
  ```json
  {
    "name": "string",
    "email": "string"
  }
  ```
- **Response:**
  - `201 Created`: User ID (string)
  - `409 Conflict`: User already exists

---

### wallet-service

#### POST /wallet-service/wallet
- **Description:** Creates a new wallet for a user. Returns the wallet ID as plain text.
- **Request body:**
  ```json
  {
    "userId": "string"
  }
  ```
- **Response:**
  - `201 Created`: Wallet ID (string)
  - `409 Conflict`: Wallet already exists

---

### transaction-service

#### POST /transaction-service/transaction
- **Description:** Creates a new transaction (deposit, withdraw, or transfer) for a wallet. Returns the transaction ID as plain text.
- **Request body:**
  ```json
  {
    "fromWalletId": "string",
    "toWalletId": "string (optional for deposit/withdraw)",
    "type": "DEPOSIT | WITHDRAW | TRANSFER",
    "amount": number
  }
  ```
- **Response:**
  - `201 Created`: Transaction ID (string)

---

### query-service

#### GET /query-service/user
- **Description:** Returns a list of all users.

#### GET /query-service/user/{userId}
- **Description:** Returns the user with the specified ID.

#### GET /query-service/user/login?email={email}
- **Description:** Returns the user with the specified email.

#### GET /query-service/wallet/{walletId}/balance?date=YYYY-MM-DD
- **Description:** Returns the balance (current or historical) for the specified wallet at the given date.

## Notes
- Always use the `userId` and `walletId` returned from the creation endpoints in subsequent requests.
- You can import the provided Postman collection (`Wallet Service.postman_collection.json`) into Postman for ready-to-use requests.
- All endpoints expect and return JSON.

---

## [Optional] Run All API Tests Automatically

You can use the provided scripts to automatically execute all main API flows and print the responses to the console. This is useful for quick end-to-end testing.

### Bash Script (Linux, macOS, Git Bash, or WSL)

1. Open a terminal in the project folder.
2. Make the script executable (if needed):
   ```sh
   chmod +x test_wallet_service.sh
   ```
3. Run the script:
   ```sh
   ./test_wallet_service.sh
   ```
   Or, if using Git Bash/WSL on Windows:
   ```sh
   bash test_wallet_service.sh
   ```

### Node.js Script (Cross-platform)

1. Make sure you have [Node.js](https://nodejs.org/) installed.
2. Install dependencies (only needed once):
   ```sh
   npm install axios
   ```
3. Run the script:
   ```sh
   node test_wallet_service.js
   ```

Both scripts will create users, wallets, perform transactions, and print all requests and responses, including IDs, to the console with clear separation between steps.
