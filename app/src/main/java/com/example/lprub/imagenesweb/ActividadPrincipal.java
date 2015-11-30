package com.example.lprub.imagenesweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ActividadPrincipal extends AppCompatActivity {
    private EditText ed;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
        inicio();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void inicio(){
        ed=(EditText) findViewById(R.id.edUrl);
    }

    public void descargar(View v) {
//        String nompag="http://"+ed.getText().toString();
        String nompag = ed.getText().toString();
        Intent i=new Intent(this, ActividadDescarga.class);
        Bundle b=new Bundle();
        b.putString("url",nompag);
        i.putExtras(b);
        startActivity(i);
    }

    public String p(String nompag)
            throws MalformedURLException, IOException {
        URL url=new URL(nompag);
        BufferedReader in=null;
        String linea,out="";
        try {
            in=new BufferedReader(
                    new InputStreamReader(url.openStream()));
            while ((linea = in.readLine()) != null) {
                if (linea.startsWith("<img") || linea.contains("<img"))
                    out += (linea.trim() + "\n");
                in.close();
            }
        }catch (Exception e){
            out="ERROR";
            Log.v("****** ", out);
        }
        tostada(out);
        return out;
    }

    public void tostada(String s){
        Toast.makeText(ActividadPrincipal.this, s , Toast.LENGTH_SHORT).show();
    }

}
