package com.example.tareasensegundoplano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Button buttonStart, buttonStop, buttonStart2, buttonStop2, buttonDescontar;
    TextView crono, crono2;
    ProgressBar progressBar;
    Thread hilo = null;
    boolean hiloActivo = true;
    int contador = 0;
    boolean hiloaActivo2 = true;
    int contador2 = 0;


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
        progressBar = findViewById(R.id.progressBar);
        buttonDescontar = findViewById(R.id.buttonDescontar);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloActivo = true;
                if (hilo == null) {
                    hilo = new Thread() {
                        @Override
                        public void run() {

                            while (hiloActivo) {
                                int segundos = contador % 60;
                                int minutos = contador / 60;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        crono.setText(minutos + ":" + segundos); //falla
                                    }
                                });
                                //   crono.setText(minutos+":"+segundos); //falla
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
                hiloaActivo2 = true;
                MiCronometro miCronometro = new MiCronometro(contador2, crono2);
                miCronometro.execute();
            }
        });
        buttonStop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiloaActivo2 = false;
            }
        });

        buttonDescontar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiCuentaAtras miCuentaAtras = new MiCuentaAtras(progressBar);
                miCuentaAtras.execute();
            }
        });
    }

    private class MiCronometro extends AsyncTask<String, String, String> {
        int micontador;

        TextView textView;

        MiCronometro(int inicio, TextView tv) {
            micontador = inicio;
            textView = tv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            while (hiloaActivo2) {
                int segundos = micontador % 60;
                int minutos = micontador / 60;
                String textoCrono = minutos + ":" + segundos;
                //Actualizo el contador en pantalla
                publishProgress(textoCrono);
                micontador++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            textView.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            contador2 = micontador;
        }
    }

    class MiCuentaAtras extends AsyncTask<String, String, String> {
        ProgressBar miprogressBar;

        MiCuentaAtras(ProgressBar pb) {
            miprogressBar = pb;
        }



        @Override
        protected String doInBackground(String... params) {
            while (miprogressBar.getProgress() > 0) {
                publishProgress();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            miprogressBar.setProgress(miprogressBar.getProgress() - 1);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, "Has finalizado", Toast.LENGTH_SHORT).show();
        }
    }
}