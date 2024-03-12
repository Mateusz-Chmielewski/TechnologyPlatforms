package org.example;

import java.util.Queue;

public class NumberManager {
    private final Queue<Integer> numbers;

    public NumberManager(Queue<Integer> numbers) {
        this.numbers = numbers;
    }

    public synchronized Integer getNumber() throws InterruptedException {
        if (numbers.isEmpty()) {
            wait();
        }

        return numbers.poll();
    }

    public synchronized void addNumber(Integer number) {
        numbers.add(number);
        notify();
    }
}
