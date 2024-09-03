package com.example.cassandra;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public void create(@RequestParam int numRecords) {
        transactionService.insertData(numRecords);
    }

    @GetMapping("/query")
    public List<Transaction> query() {
        return transactionService.queryData();
    }
}
