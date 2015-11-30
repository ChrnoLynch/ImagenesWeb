package com.example.lprub.imagenesweb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ActividadDescarga extends AppCompatActivity {

    private ListView lvDescarga;
    private Bitmap bt;
    private Adaptador adp;
    private ProgressBar pb;
    private TextView estado;
    private boolean cambio=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga);
        lvDescarga= (ListView) findViewById(R.id.lvDescarga);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        estado= (TextView) findViewById(R.id.tvEstado);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        DescargaImagenes descarga = new DescargaImagenes();
        descarga.execute(bundle.getString("url"));
    }

    public class DescargaImagenes extends AsyncTask<String, Integer, ArrayList<String>> {
        ArrayList<Bitmap> imagenes = new ArrayList<>();
        int recorrido=0;
        int extra=0;
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
                String linea, out = "";
                for (recorrido = 0; (linea = input.readLine()) != null; recorrido++) {
                    publishProgress(recorrido);
                    out =out + linea + " ";
                }
                input.close();
                ArrayList<String> rutaImagenes=sacarImagenes(out);
                cambio=true;
                for (String cadena : rutaImagenes) {
                    recorrido++;
                    publishProgress(recorrido);
                    try {
                        URL urlimg = new URL(cadena);
                        imagenes.add(BitmapFactory.decodeStream(urlimg.openConnection().getInputStream()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return rutaImagenes;
            } catch (MalformedURLException ex) {
            } catch (IOException ex) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            pb.setProgress(0);
            pb.setMax(1000);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[values.length - 1]);
            if(cambio==false && pb.getProgress()==(1000+extra)){
                extra=extra+200;
                pb.setMax(1000+extra);
            }
            if (cambio==true) {
                estado.setText(R.string.descargando);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> rutaImagenes) {
            pb.setVisibility(View.GONE);
            estado.setVisibility(View.GONE);
            adp = new Adaptador(ActividadDescarga.this,R.layout.item, rutaImagenes);
            lvDescarga.setAdapter(adp);
            lvDescarga.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(ActividadDescarga.this, ActividadVisualizador.class);
                            i.putExtra("imagen", imagenes.get(position));
                            startActivity(i);
                        }
                    }
            );
        }

        public ArrayList sacarImagenes(String cadena) {
            ArrayList<String> rutaImagenes = new ArrayList<>();
            int posInicial = 0, posFinal;
            String aux;
            System.out.println(cadena.indexOf("<img src=\"", posInicial));
            while (cadena.indexOf("<img src=\"", posInicial) != -1) {
                posInicial = cadena.indexOf("<img src=\"", posInicial);
                posInicial=posInicial+10;
                posFinal = cadena.indexOf("\"", posInicial);
                aux=cadena.substring(posInicial , posFinal);
                if (!aux.contains(".gif")&&aux.contains("http"))
                rutaImagenes.add(aux);
            }
            return rutaImagenes;
        }
    }
}
