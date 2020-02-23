package com.example.msp.databeam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by murta on 11/23/2016.
 */
public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timer = new Thread()
        {
            public void run()
            {try {
                sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                Intent i = new Intent(Splash.this,MainActivity.class);

                startActivity(i);
            }
            }
        };
        timer.start();
    }
}
