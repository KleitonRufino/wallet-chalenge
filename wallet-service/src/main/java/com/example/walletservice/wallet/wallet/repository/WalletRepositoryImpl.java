package com.example.walletservice.wallet.wallet.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.walletservice.wallet.wallet.domain.Wallet;
import com.example.walletservice.wallet.wallet.domain.WalletDomainRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public final class WalletRepositoryImpl implements WalletDomainRepository {
    private static final String FILE_PATH = "wallets.json";
    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private List<Wallet> wallets = loadFromFile();

    private List<Wallet> loadFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return mapper.readValue(file, new TypeReference<List<Wallet>>() {});
            }
        } catch (IOException e) {
            log.error("Error loading wallets from file", e);
        }
        return new ArrayList<>();
    }

    private void saveToFile() {
        try {
            mapper.writeValue(new File(FILE_PATH), wallets);
        } catch (IOException e) {
            log.error("Error saving wallets to file", e);
        }
    }

    @Override
    public void save(Wallet wallet) {
        wallets.removeIf(w -> w.getId().equals(wallet.getId())); // avoids duplicates
        wallets.add(wallet);
        saveToFile();
        log.info("Saved wallet: {}", wallet);
    }

    @Override
    public Wallet findById(String id) {
        List<Wallet> wallets = loadFromFile();
        return wallets.stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Wallet findByUserId(String userId) {
        List<Wallet> wallets = loadFromFile();
        return wallets.stream().filter(w -> w.getUserId().equals(userId)).findFirst().orElse(null);
    }

    @Override
    public List<Wallet> findAll() {
        return new ArrayList<>(loadFromFile());
    }
}
