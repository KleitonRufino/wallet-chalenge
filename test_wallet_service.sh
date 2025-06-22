#!/bin/bash

# Bash script to test Wallet Service APIs using curl

RANDOM_JOAO=$((RANDOM % 1000000))
RANDOM_MARIA=$((RANDOM % 1000000))
NAME_JOAO="joao${RANDOM_JOAO}"
NAME_MARIA="maria${RANDOM_MARIA}"
EMAIL_JOAO="${NAME_JOAO}${RANDOM_JOAO}@email.com"
EMAIL_MARIA="${NAME_MARIA}${RANDOM_MARIA}@email.com"

SEPARATOR="\n==============================\n"

echo "Creating user joao..."
USER1_PAYLOAD='{"name":"'$NAME_JOAO'","email":"'$EMAIL_JOAO'"}'
echo "Request: curl -X POST http://localhost:8081/user-service/user -H 'Content-Type: application/json' -d '$USER1_PAYLOAD'"
USER1_RESPONSE=$(curl -s -X POST http://localhost:8081/user-service/user -H "Content-Type: application/json" -d "$USER1_PAYLOAD")
USER1_ID="$USER1_RESPONSE"
echo "User 1 ID: $USER1_ID"
echo "Name: $NAME_JOAO"
echo "Email: $EMAIL_JOAO"
echo "Response body: $USER1_RESPONSE"
echo -e $SEPARATOR

echo "Creating user maria..."
USER2_PAYLOAD='{"name":"'$NAME_MARIA'","email":"'$EMAIL_MARIA'"}'
echo "Request: curl -X POST http://localhost:8081/user-service/user -H 'Content-Type: application/json' -d '$USER2_PAYLOAD'"
USER2_RESPONSE=$(curl -s -X POST http://localhost:8081/user-service/user -H "Content-Type: application/json" -d "$USER2_PAYLOAD")
USER2_ID="$USER2_RESPONSE"
echo "User 2 ID: $USER2_ID"
echo "Name: $NAME_MARIA"
echo "Email: $EMAIL_MARIA"
echo "Response body: $USER2_RESPONSE"
echo -e $SEPARATOR

echo "Creating wallet for joao..."
WALLET1_PAYLOAD='{"userId":"'$USER1_ID'"}'
echo "Request: curl -X POST http://localhost:8082/wallet-service/wallet -H 'Content-Type: application/json' -d '$WALLET1_PAYLOAD'"
WALLET1_RESPONSE=$(curl -s -X POST http://localhost:8082/wallet-service/wallet -H "Content-Type: application/json" -d "$WALLET1_PAYLOAD")
WALLET1_ID="$WALLET1_RESPONSE"
echo "Wallet 1 ID: $WALLET1_ID"
echo "Response body: $WALLET1_RESPONSE"
echo -e $SEPARATOR

echo "Creating wallet for maria..."
WALLET2_PAYLOAD='{"userId":"'$USER2_ID'"}'
echo "Request: curl -X POST http://localhost:8082/wallet-service/wallet -H 'Content-Type: application/json' -d '$WALLET2_PAYLOAD'"
WALLET2_RESPONSE=$(curl -s -X POST http://localhost:8082/wallet-service/wallet -H "Content-Type: application/json" -d "$WALLET2_PAYLOAD")
WALLET2_ID="$WALLET2_RESPONSE"
echo "Wallet 2 ID: $WALLET2_ID"
echo "Response body: $WALLET2_RESPONSE"
echo -e $SEPARATOR

echo "Depositing 20 to joao's wallet..."
DEPOSIT_PAYLOAD='{"fromWalletId":"'$WALLET1_ID'","type":"DEPOSIT","amount":20}'
echo "Request: curl -X POST http://localhost:8083/transaction-service/transaction -H 'Content-Type: application/json' -d '$DEPOSIT_PAYLOAD'"
DEPOSIT_RESPONSE=$(curl -s -X POST http://localhost:8083/transaction-service/transaction -H "Content-Type: application/json" -d "$DEPOSIT_PAYLOAD")
echo "Response body: $DEPOSIT_RESPONSE"
echo -e $SEPARATOR

echo "Withdrawing 2 from joao's wallet..."
WITHDRAW_PAYLOAD='{"fromWalletId":"'$WALLET1_ID'","type":"WITHDRAW","amount":2}'
echo "Request: curl -X POST http://localhost:8083/transaction-service/transaction -H 'Content-Type: application/json' -d '$WITHDRAW_PAYLOAD'"
WITHDRAW_RESPONSE=$(curl -s -X POST http://localhost:8083/transaction-service/transaction -H "Content-Type: application/json" -d "$WITHDRAW_PAYLOAD")
echo "Response body: $WITHDRAW_RESPONSE"
echo -e $SEPARATOR

echo "Transferring 3 from joao to maria..."
TRANSFER_PAYLOAD='{"fromWalletId":"'$WALLET1_ID'","toWalletId":"'$WALLET2_ID'","type":"TRANSFER","amount":3}'
echo "Request: curl -X POST http://localhost:8083/transaction-service/transaction -H 'Content-Type: application/json' -d '$TRANSFER_PAYLOAD'"
TRANSFER_RESPONSE=$(curl -s -X POST http://localhost:8083/transaction-service/transaction -H "Content-Type: application/json" -d "$TRANSFER_PAYLOAD")
echo "Response body: $TRANSFER_RESPONSE"
echo -e $SEPARATOR

echo "Getting joao's wallet balance..."
BALANCE1_URL="http://localhost:8080/query-service/wallet/$WALLET1_ID/balance?date=$(date +%Y-%m-%d)"
echo "Request: curl -X GET '$BALANCE1_URL'"
BALANCE1_RESPONSE=$(curl -s -X GET "$BALANCE1_URL")
echo "Response body: $BALANCE1_RESPONSE"
echo -e $SEPARATOR

echo "Getting maria's wallet balance..."
BALANCE2_URL="http://localhost:8080/query-service/wallet/$WALLET2_ID/balance?date=$(date +%Y-%m-%d)"
echo "Request: curl -X GET '$BALANCE2_URL'"
BALANCE2_RESPONSE=$(curl -s -X GET "$BALANCE2_URL")
echo "Response body: $BALANCE2_RESPONSE"
echo -e $SEPARATOR
