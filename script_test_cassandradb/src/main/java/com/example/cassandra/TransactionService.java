package com.example.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void insertData(int numRecords) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numRecords; i++) {
            Transaction transaction = new Transaction();
            transaction.setTransaction_id(UUID.randomUUID());
            transaction.setUser_id(UUID.randomUUID());
            transaction.setAmount(BigDecimal.valueOf(100.0));
            transaction.setTransaction_date(Instant.now());
            transaction.setStatus("PENDING");
            transactionRepository.save(transaction);
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Inserted " + numRecords + " records in " + duration + " milliseconds");
    }

    public List<Transaction> queryData() {
        long startTime = System.currentTimeMillis();
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Queried " + transactions.size() + " records in " + duration + " milliseconds");
        return transactions;
    }
}
