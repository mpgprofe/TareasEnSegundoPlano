package com.example.tareasensegundoplano;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonStart, buttonStop,buttonStart2, buttonStop2;
    TextView crono, crono2;
    Thread hilo = null;
    boolean hiloActivo = true;
    int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        crono = findViewById(R.id.textViewCrono);
        buttonStart2 = findViewById(R.id.buttonStart2);
        buttonStop2 = findViewById(R.id.buttonStop2);
        crono2 = findViewById(R.id.textViewCrono2);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo=true;
                if (hilo == null) {
                    hilo = new Thread() {
                        @Override
                        public void run() {

                               while (hiloActivo) {
                                    int segundos = contador % 60;
                                    int minutos = contador / 60;
                                    //crono.setText(minutos+":"+segundos); //falla
                                    try {
                                        sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i("CRONO", minutos + ":" + segundos);
                                    contador++;
                                }

                        }
                    };
                    hilo.start();
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo = false;
                hilo = null;
                Log.i("CRONO", "Parado");

            }
        });

        buttonStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiCronometro miCronometro = new MiCronometro(0, crono2);
                miCronometro.execute();
            }
        });
    }

    private class MiCronometro extends AsyncTask<String, String, String>{
        int contador = 0;
        TextView textView;

        MiCronometro(int inicio, TextView tv){
            contador = inicio;
            textView = tv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            while(true){
                int segundos = contador % 60;
                int minutos =  contador /60;
                String textoCrono = minutos+":"+segundos;
                //Actualizo el contador en pantalla
                publishProgress(textoCrono);
                contador++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
            textView.setText(values[0]);
        }
    }
}