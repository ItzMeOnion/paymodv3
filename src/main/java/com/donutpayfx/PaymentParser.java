package com.donutpayfx;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PaymentParser {
    // Tune these patterns if DonutSMP changes its chat format.
    private static final Pattern PAID_YOU = Pattern.compile(
        "^\\s*([A-Za-z0-9_.-]{1,32})\\s+paid\\s+you\\s+\\$?\\s*([0-9.,]+(?:\\.[0-9]+)?[kmbtKMBT]?)\\s*[.!]?(?:.*)$"
    );

    private PaymentParser() {}

    public static Optional<Payment> parse(String raw) {
        String clean = raw.replaceAll("§.", "").trim();
        Matcher m = PAID_YOU.matcher(clean);
        if (!m.matches()) return Optional.empty();
        try {
            return Optional.of(new Payment(m.group(1), parseAmount(m.group(2)), System.currentTimeMillis()));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    public static long parseAmount(String input) {
        String s = input.toLowerCase(Locale.ROOT).replace(",", "").replace("$", "").trim();
        long multiplier = 1L;
        if (s.endsWith("k")) { multiplier = 1_000L; s = s.substring(0, s.length()-1); }
        else if (s.endsWith("m")) { multiplier = 1_000_000L; s = s.substring(0, s.length()-1); }
        else if (s.endsWith("b")) { multiplier = 1_000_000_000L; s = s.substring(0, s.length()-1); }
        else if (s.endsWith("t")) { multiplier = 1_000_000_000_000L; s = s.substring(0, s.length()-1); }
        return Math.round(Double.parseDouble(s) * multiplier);
    }
}
