package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int threadsNumber;
        threadsNumber = Integer.parseInt(args[0]);

        NumberManager numberManager = new NumberManager(new LinkedList<>());
        ResultManager resultManager = new ResultManager();

        List<Thread> threads = new ArrayList<>(threadsNumber);
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(new PrimeNumbers(numberManager, resultManager)));
        }

        threads.add(new Thread(new ResultPresenter(resultManager)));
        threads.forEach(Thread::start);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String ss = scanner.nextLine();

            if (ss.equals("quit")) {
                break;
            }

            numberManager.addNumber(Integer.parseInt(ss));
        }

        threads.forEach(Thread::interrupt);
    }
}