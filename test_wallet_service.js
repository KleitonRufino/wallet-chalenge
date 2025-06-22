const axios = require('axios');

function randomName(base) {
  const rand = Math.floor(Math.random() * 1000000);
  return `${base}${rand}`;
}
function randomEmail(name) {
  const rand = Math.floor(Math.random() * 1000000);
  return `${name}${rand}@email.com`;
}

const SEPARATOR = '\n==============================\n';

async function main() {
  try {
    const nameJoao = randomName('joao');
    const emailJoao = randomEmail(nameJoao);
    console.log("Creating user joao...");
    const user1Payload = { name: nameJoao, email: emailJoao };
    console.log("Request:", JSON.stringify(user1Payload));
    const user1Res = await axios.post('http://localhost:8081/user-service/user', user1Payload, { responseType: 'text' });
    const user1Id = user1Res.data;
    console.log("User 1 ID:", user1Id);
    console.log("Name:", nameJoao);
    console.log("Email:", emailJoao);
    console.log("Response body:", user1Res.data);
    console.log(SEPARATOR);

    const nameMaria = randomName('maria');
    const emailMaria = randomEmail(nameMaria);
    console.log("Creating user maria...");
    const user2Payload = { name: nameMaria, email: emailMaria };
    console.log("Request:", JSON.stringify(user2Payload));
    const user2Res = await axios.post('http://localhost:8081/user-service/user', user2Payload, { responseType: 'text' });
    const user2Id = user2Res.data;
    console.log("User 2 ID:", user2Id);
    console.log("Name:", nameMaria);
    console.log("Email:", emailMaria);
    console.log("Response body:", user2Res.data);
    console.log(SEPARATOR);

    console.log("Creating wallet for joao...");
    const wallet1Payload = { userId: user1Id };
    console.log("Request:", JSON.stringify(wallet1Payload));
    const wallet1Res = await axios.post('http://localhost:8082/wallet-service/wallet', wallet1Payload, { responseType: 'text' });
    const wallet1Id = wallet1Res.data;
    console.log("Wallet 1 ID:", wallet1Id);
    console.log("Response body:", wallet1Res.data);
    console.log(SEPARATOR);

    console.log("Creating wallet for maria...");
    const wallet2Payload = { userId: user2Id };
    console.log("Request:", JSON.stringify(wallet2Payload));
    const wallet2Res = await axios.post('http://localhost:8082/wallet-service/wallet', wallet2Payload, { responseType: 'text' });
    const wallet2Id = wallet2Res.data;
    console.log("Wallet 2 ID:", wallet2Id);
    console.log("Response body:", wallet2Res.data);
    console.log(SEPARATOR);

    console.log("Depositing 20 to joao's wallet...");
    const depositPayload = { fromWalletId: wallet1Id, type: "DEPOSIT", amount: 20 };
    console.log("Request:", JSON.stringify(depositPayload));
    const depositRes = await axios.post('http://localhost:8083/transaction-service/transaction', depositPayload);
    console.log("Response body:", JSON.stringify(depositRes.data));
    console.log(SEPARATOR);

    console.log("Withdrawing 2 from joao's wallet...");
    const withdrawPayload = { fromWalletId: wallet1Id, type: "WITHDRAW", amount: 2 };
    console.log("Request:", JSON.stringify(withdrawPayload));
    const withdrawRes = await axios.post('http://localhost:8083/transaction-service/transaction', withdrawPayload);
    console.log("Response body:", JSON.stringify(withdrawRes.data));
    console.log(SEPARATOR);

    console.log("Transferring 3 from joao to maria...");
    const transferPayload = { fromWalletId: wallet1Id, toWalletId: wallet2Id, type: "TRANSFER", amount: 3 };
    console.log("Request:", JSON.stringify(transferPayload));
    const transferRes = await axios.post('http://localhost:8083/transaction-service/transaction', transferPayload);
    console.log("Response body:", JSON.stringify(transferRes.data));
    console.log(SEPARATOR);

    const today = new Date().toISOString().slice(0, 10);

    console.log("Getting joao's wallet balance...");
    const balance1Url = `http://localhost:8080/query-service/wallet/${wallet1Id}/balance?date=${today}`;
    console.log("Request:", balance1Url);
    const balance1Res = await axios.get(balance1Url);
    console.log("Response body:", JSON.stringify(balance1Res.data));
    console.log(SEPARATOR);

    console.log("Getting maria's wallet balance...");
    const balance2Url = `http://localhost:8080/query-service/wallet/${wallet2Id}/balance?date=${today}`;
    console.log("Request:", balance2Url);
    const balance2Res = await axios.get(balance2Url);
    console.log("Response body:", JSON.stringify(balance2Res.data));
    console.log(SEPARATOR);

  } catch (err) {
    if (err.response) {
      console.error("Error:", err.response.data);
    } else {
      console.error("Error:", err.message);
    }
  }
}

main();
