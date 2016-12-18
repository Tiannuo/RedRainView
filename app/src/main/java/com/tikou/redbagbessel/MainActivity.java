package com.tikou.redbagbessel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tikou.library_bessel.Animotion_Bessel;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Animotion_Bessel animotion_bessel;
    private Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send).setOnClickListener(this);
        animotion_bessel= (Animotion_Bessel) findViewById(R.id.animotion_bessel);
        random=new Random();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:

                for (int i=0;i<50;i++){
                    animotion_bessel.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animotion_bessel.addRedBag();
                        }
                    }, (long) ((Math.random()+0.2)*4000));

                }


                break;
        }
    }
}
