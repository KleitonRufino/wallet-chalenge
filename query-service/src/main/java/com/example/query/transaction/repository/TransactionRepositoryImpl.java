package com.example.query.transaction.repository;

import com.example.query.transaction.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public final class TransactionRepositoryImpl implements TransactionRepository {
    private static final String FILE_PATH = "transactions.json";
    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private List<Transaction> transactions = loadFromFile();

    private List<Transaction> loadFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return mapper.readValue(file, new TypeReference<List<Transaction>>() {});
            }
        } catch (IOException e) {
            log.error("Error loading transactions from file", e);
        }
        return new ArrayList<>();
    }

    private void saveToFile() {
        try {
            mapper.writeValue(new File(FILE_PATH), transactions);
        } catch (IOException e) {
            log.error("Error saving transactions to file", e);
        }
    }

    @Override
    public void save(Transaction transaction) {
        transactions = loadFromFile();
        transactions.removeIf(t -> t.getId().equals(transaction.getId()));
        transactions.add(transaction);
        saveToFile();
        log.info("Transaction saved: {}", transaction.getId());
    }

    @Override
    public Transaction findById(String id) {
        List<Transaction> transactions = loadFromFile();
        return transactions.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }
}
