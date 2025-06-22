package com.example.query.wallet.repository;

import com.example.query.wallet.model.Wallet;
import com.example.query.wallet.model.Balance;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public final class WalletRepositoryImpl implements WalletRepository {
	private static final String FILE_PATH = "wallets.json";
	private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	private List<Wallet> wallets = loadFromFile();

	private List<Wallet> loadFromFile() {
		try {
			File file = new File(FILE_PATH);
			if (file.exists()) {
				return mapper.readValue(file, new TypeReference<List<Wallet>>() {
				});
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
		wallets.removeIf(w -> w.getId().equals(wallet.getId()));
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

	@Override
	public BigDecimal findBalanceByUserId(String userId) {
		Wallet wallet = findByUserId(userId);
		return wallet != null ? wallet.getBalance().getBalance() : null;
	}

	@Override
	public Balance findBalanceByDate(String walletId, LocalDate date) {
		Wallet wallet = findById(walletId);
		if (wallet == null)
			return null;
		var balances = new ArrayList<Balance>();
		balances.add(wallet.getBalance());
		balances.addAll(wallet.getBalanceHistory());
		return balances.stream().filter(b -> b.getDate() != null && b.getDate().toLocalDate().equals(date))
				.sorted((b1, b2) -> b2.getDate().compareTo(b1.getDate())) // descending by date
				.findFirst().orElse(null);
	}
}
