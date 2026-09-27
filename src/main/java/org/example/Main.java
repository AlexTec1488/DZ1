package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        int n = text.length();
        for (int i = 0; i < n / 2; i++) {
            if (text.charAt(i) != text.charAt(n - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        char first = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAscending(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void incrementCounter(int length) {
        if (length == 3) counter3.incrementAndGet();
        else if (length == 4) counter4.incrementAndGet();
        else if (length == 5) counter5.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        Thread sameLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        Thread ascendingThread = new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        palindromeThread.start();
        sameLetterThread.start();
        ascendingThread.start();

        palindromeThread.join();
        sameLetterThread.join();
        ascendingThread.join();

        System.out.println("Красивых слов с длиной 3: " + counter3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5.get() + " шт");
    }
}
