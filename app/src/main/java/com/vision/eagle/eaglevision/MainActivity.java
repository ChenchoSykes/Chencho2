package com.vision.eagle.eaglevision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText Usuario,Pass;
    TextView msje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Usuario = (EditText) findViewById(R.id.Texto);
        Pass = (EditText) findViewById(R.id.Pass);
        msje = (TextView) findViewById(R.id.msje);

    }

    public void Ingresar (View laVista){


        String usr = "llamada";
        String Nombre = Usuario.getText().toString();
        String Passs = Pass.getText().toString();
        if (Nombre.equalsIgnoreCase("Zazu")&&Passs.equals("123")){
            Intent crearpantalla = new Intent(this,Inicio.class);
            crearpantalla.putExtra("nombre",Nombre);
            crearpantalla.putExtra("pass",Passs);
            startActivity(crearpantalla);
        }else {
            String error = "Datos invalidos";
            msje.setText(error);
        }
    }
}
