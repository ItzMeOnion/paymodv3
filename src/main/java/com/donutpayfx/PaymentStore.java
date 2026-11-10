package com.donutpayfx;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class PaymentStore {
    private final List<Payment> payments = new CopyOnWriteArrayList<>();

    public void add(Payment payment) { payments.add(payment); }

    public List<Payment> recent() {
        return payments.stream()
            .sorted(Comparator.comparingLong(Payment::timeMillis).reversed())
            .limit(50).toList();
    }

    public List<Map.Entry<String, Long>> leaderboard() {
        Map<String, Long> totals = new HashMap<>();
        for (Payment p : payments) totals.merge(p.player(), p.amount(), Long::sum);
        return totals.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(25).toList();
    }

    public long totalFor(String player) {
        return payments.stream().filter(p -> p.player().equalsIgnoreCase(player))
            .mapToLong(Payment::amount).sum();
    }
}
