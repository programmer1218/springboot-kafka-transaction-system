package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class TransactionListener {

    @Autowired
    private RestTemplate restTemplate;

    @KafkaListener(
        topics = "${general.kafka-topic}",
        groupId = "midas-group"
    )
    public void listen(Transaction transaction) {

        System.out.println("Received Transaction Amount: " + transaction.getAmount());

        // 🔥 CALL INCENTIVE API
        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        float incentiveAmount = incentive != null ? incentive.getAmount() : 0;

        System.out.println("Incentive: " + incentiveAmount);

        // ⚠️ NOTE:
        // Here you must already have your existing logic:
        // - find sender
        // - find recipient
        // - validate
        // - update balances

        // 🔥 IMPORTANT CHANGE (Task 4 logic)
        // sender balance → - amount
        // recipient balance → + amount + incentive

        // Example (you must integrate with your existing code):
        /*
        sender.setBalance(sender.getBalance() - transaction.getAmount());

        recipient.setBalance(
            recipient.getBalance() + transaction.getAmount() + incentiveAmount
        );
        */
    }
}