package com.example.walletservice.wallet.wallet.domain.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceVO {
    private String id;
    private BigDecimal balance;
    private LocalDateTime date;
}
