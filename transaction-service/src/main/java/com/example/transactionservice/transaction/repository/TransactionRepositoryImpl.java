package com.example.transactionservice.transaction.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.transactionservice.transaction.domain.model.Transaction;
import com.example.transactionservice.transaction.domain.model.TransactionDomainRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public final class TransactionRepositoryImpl implements TransactionDomainRepository {
	private static final String FILE_PATH = "transactions.json";
	private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	private List<Transaction> transactions = loadFromFile();

	private List<Transaction> loadFromFile() {
		try {
			File file = new File(FILE_PATH);
			if (file.exists()) {
				return mapper.readValue(file, new TypeReference<List<Transaction>>() {
				});
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
		transactions.removeIf(t -> t.getId().equals(transaction.getId()));
		transactions.add(transaction);
		saveToFile();
		log.info("Saved transaction: {}", transaction);
	}

	@Override
	public Transaction findById(String id) {
		List<Transaction> transactions = loadFromFile();
		return transactions.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public List<Transaction> findAll() {
		return new ArrayList<>(loadFromFile());
	}
}
