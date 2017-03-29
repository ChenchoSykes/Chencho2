package com.vision.eagle.eaglevision;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Inicio extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextView cuantasPersonas;
    Button fecha, hora;
    SeekBar barraPersonas;

    SimpleDateFormat horaFormato, fechaFormato;


    String numPersonas = "";
    String fechaSel = "", horaSel = "";
    Date fechaConv;
    String cuantasPersonasFormat = "";
    int personas = 1; // Valor por omision, al menos 1 persona tiene que reservar

    Calendar calendario;
    TextView pantalla;
    String dinosaurio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_reserva);
        pantalla = (TextView) findViewById(R.id.Name);
        Intent intento2 = getIntent();
        dinosaurio = intento2.getExtras().getString("nombre");


        pantalla.setText(pantalla.getText().toString() + dinosaurio);

        cuantasPersonas = (TextView) findViewById(R.id.cuantasPersonas);
        barraPersonas = (SeekBar) findViewById(R.id.personas);

        fecha = (Button) findViewById(R.id.fecha);
        hora = (Button) findViewById(R.id.hora);

        barraPersonas.setOnSeekBarChangeListener(this);

        cuantasPersonasFormat = cuantasPersonas.getText().toString();
        // cuantasPersonasFormat = "personas: %d";
        cuantasPersonas.setText("Personas: 1"); // condicion inicial

        // Para seleccionar la fecha y la hora
        Calendar fechaSeleccionada = Calendar.getInstance();
        fechaSeleccionada.set(Calendar.HOUR_OF_DAY, 12); // hora inicial
        fechaSeleccionada.clear(Calendar.MINUTE); // 0
        fechaSeleccionada.clear(Calendar.SECOND); // 0

        // formatos de la fecha y hora
        fechaFormato = new SimpleDateFormat(fecha.getText().toString());
        horaFormato = new SimpleDateFormat("HH:mm");
        // horaFormato = new SimpleDateFormat(hora.getText().toString());

        // La primera vez mostramos la fecha actual
        Date fechaReservacion = fechaSeleccionada.getTime();
        fechaSel = fechaFormato.format(fechaReservacion);
        fecha.setText(fechaSel); // fecha en el

        horaSel = horaFormato.format(fechaReservacion);
        // boton
        hora.setText(horaSel); // hora en el boton

        // Otra forma de ocupar los botones
        fecha.setOnClickListener(this);
        hora.setOnClickListener(this);
    }
    @Override
    public void onProgressChanged(SeekBar barra, int progreso,
                                  boolean delUsuario) {

        numPersonas = String.format(cuantasPersonasFormat, barraPersonas.getProgress() + 1);
        personas = barraPersonas.getProgress() + 1; // este es el valor que se
        // guardara en la BD
        // Si no se mueve la barra, enviamos el valor personas = 1
        cuantasPersonas.setText(numPersonas);
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
    }

    @Override
    public void onClick(View v) {
        if (v == fecha) {
            Calendar calendario = parseCalendar(fecha.getText(), fechaFormato);
            new DatePickerDialog(this, this, calendario.get(Calendar.YEAR),
                    calendario.get(Calendar.MONTH),
                    calendario.get(Calendar.DAY_OF_MONTH)).show();
        } else if (v == hora) {
            Calendar calendario = parseCalendar(hora.getText(), horaFormato);
            new TimePickerDialog(this, this,
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE), false) // /true = 24 horas
                    .show();
        }
    }

    private Calendar parseCalendar(CharSequence text,
                                   SimpleDateFormat fechaFormat2) {
        try {
            fechaConv = fechaFormat2.parse(text.toString());
        } catch (ParseException e) { // import java.text.ParsedExc
            throw new RuntimeException(e);
        }
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaConv);
        return calendario;
    }

    @Override
    public void onDateSet(DatePicker picker, int anio, int mes, int dia) {
        calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes);
        calendario.set(Calendar.DAY_OF_MONTH, dia);

        fechaSel = fechaFormato.format(calendario.getTime());
        fecha.setText(fechaSel);

    }

    public void onTimeSet(TimePicker picker, int horas, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minutos);

        horaSel = horaFormato.format(calendar.getTime());
        hora.setText(horaSel);
    }

    public void IniciarSesion(View miView)
    {
        Toast.makeText(getApplicationContext(), "Accediendo...",Toast.LENGTH_SHORT).show();
        System.out.println("Tu gfa");
        Intent itemintent = new Intent(Inicio.this, VerReserva.class);
        Bundle datos = new Bundle();

        datos.putString("nombre", dinosaurio);
        datos.putInt("personas", personas);
        datos.putString("fecha", fechaSel);
        datos.putString("hora", horaSel);
        itemintent.putExtras(datos);
        System.out.println("Tu gfa2");
        finish();
        startActivity(itemintent);
        System.out.println("Tu gfa final");
    }
}
