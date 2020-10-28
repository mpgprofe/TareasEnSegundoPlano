package com.example.tareasensegundoplano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button buttonStart, buttonStop;
TextView crono;
Thread hilo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        crono = findViewById(R.id.textViewCrono);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hilo ==null){
                    hilo = new Thread(){
                        @Override
                        public void run() {
                            int contador = 0;
                            while(true){
                                int segundos = contador % 60;
                                int minutos = contador / 60;
                                //crono.setText(minutos+":"+segundos); //falla
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Log.i("CRONO", minutos+":"+segundos);
                                contador++;
                            }
                        }
                    };
                  hilo.start();
                }
            }
        });
    }
}