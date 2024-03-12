package org.example;

import java.util.Queue;

public class PrimeNumbers implements Runnable {
    NumberManager numberManager;
    ResultManager resultManager;

    public PrimeNumbers(NumberManager numberManager, ResultManager resultManager) {
        this.numberManager = numberManager;
        this.resultManager = resultManager;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Integer number = numberManager.getNumber();

                String result = isPrimeNumber(number)
                        ? " is prime number "
                        : " is not prime number ";

                result = number + result;

                resultManager.addResult(result);

            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                return;
            }
        }
    }

    private boolean isPrimeNumber(int number) {
        for (int i = 2; i < number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
