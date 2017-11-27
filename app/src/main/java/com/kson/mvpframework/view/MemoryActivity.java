package com.kson.mvpframework.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kson.mvpframework.R;

public class MemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        testThread();
    }

    private void testThread() {
      new mythread().start();
    }

    static class mythread extends Thread{
        @Override
        public void run() {
            for (int i = 0;i<10000;i++){
                try {
                    Thread.sleep(100);
                    System.out.println("------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
