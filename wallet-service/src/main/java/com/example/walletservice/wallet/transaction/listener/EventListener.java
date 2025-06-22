package com.example.walletservice.wallet.transaction.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletservice.wallet.transaction.domain.event.TransactionDepositEvent;
import com.example.walletservice.wallet.transaction.domain.event.TransactionTransferEvent;
import com.example.walletservice.wallet.transaction.domain.event.TransactionWithdrawEvent;
import com.example.walletservice.wallet.wallet.application.WalletApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/wallet-service/events/wallet")
public class EventListener {

    @Autowired
    private WalletApplication walletApplication;

    @PostMapping("/deposit")
    public void handleDeposit(@RequestBody TransactionDepositEvent event) {
        log.info("[Listener] Received event: {}", TransactionDepositEvent.class.getSimpleName());
        walletApplication.handleDeposit(event);
    }

    @PostMapping("/withdraw")
    public void handleWithdraw(@RequestBody TransactionWithdrawEvent event) {
        log.info("[Listener] Received event: {}", TransactionWithdrawEvent.class.getSimpleName());
        walletApplication.handleWithdraw(event);
    }

    @PostMapping("/transfer")
    public void handleTransfer(@RequestBody TransactionTransferEvent event) {
        log.info("[Listener] Received event: {}", TransactionTransferEvent.class.getSimpleName());
        walletApplication.handleTransfer(event);
    }
}
