package com.vany;

public class Animate implements Runnable {
    private BlockBreakerPanel bp;

    Animate(BlockBreakerPanel b){
        this.bp = b;
    }

    @Override
    public void run() {
        while(true){
            bp.update();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
