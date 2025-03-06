package com.proyecto.jessuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.proyecto.jessuri.R;

public class SitioWeb extends AppCompatActivity {
    private WebView webView;
    Button btnSalir;
    int idU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitio_web);
        webView = findViewById(R.id.xwebView);
        btnSalir = findViewById(R.id.xbtnSalir);
        Bundle bundle = getIntent().getExtras();
        idU = bundle.getInt("idUser");

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://bellisima.mx/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        View.OnClickListener salida = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        btnSalir.setOnClickListener(salida);
    }
}