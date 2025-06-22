package com.example.transactionservice.transaction.api.dto;

import java.math.BigDecimal;
import com.example.transactionservice.transaction.domain.model.TransactionTypeEnum;
import lombok.Data;

@Data
public class TransactionDTO {
    private String fromWalletId;
    private String toWalletId;
    private TransactionTypeEnum type;
    private BigDecimal amount;
}
