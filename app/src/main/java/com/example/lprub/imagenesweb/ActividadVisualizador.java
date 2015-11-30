package com.example.lprub.imagenesweb;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ActividadVisualizador extends AppCompatActivity {
        private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizador);
        this.iv = (ImageView) findViewById(R.id.ivFoto);

        Bitmap bitmap = getIntent().getParcelableExtra("imagen");
        iv.setImageBitmap(bitmap);
    }
}
