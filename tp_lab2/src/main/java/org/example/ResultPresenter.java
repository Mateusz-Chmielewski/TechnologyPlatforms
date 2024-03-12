package org.example;

public class ResultPresenter implements Runnable {
    ResultManager resultManager;

    public ResultPresenter(ResultManager resultManager) {
        this.resultManager = resultManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String result = resultManager.getResult();
                System.out.println(result);
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                return;
            }
        }
    }
}
