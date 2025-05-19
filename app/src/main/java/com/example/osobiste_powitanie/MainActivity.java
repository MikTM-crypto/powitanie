package com.example.osobiste_powitanie;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int KOD_ZADANIA_UPRAWNIEN_POWIADOMIEN = 1;
    private EditText edit;
    private String imie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button ButtonPowitanie = findViewById(R.id.buttonPowitanie);
        edit = findViewById(R.id.editTextImie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        KOD_ZADANIA_UPRAWNIEN_POWIADOMIEN);

            }
        }
        PowiadomieniaHelper.utworzKanalyPowiadomien(this);


        ButtonPowitanie.setOnClickListener(view -> sprawdz());
    }

    private void sprawdz() {
        String tekst = edit.getText().toString();
        imie=tekst;
        if(tekst.isEmpty()){
            AlertDialogblad();
        }
        else{
            AlertDialogpotwierdzenie();
        }
    }

    private void AlertDialogblad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Błąd");
        builder.setMessage("Proszę wpisać swoje imię!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        });
        builder.create().show();
    }
    private  void AlertDialogpotwierdzenie(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Potwierdzenie");
        builder.setMessage("Cześć "+imie+"! Czy chcesz otrzymać powiadomienie powitalne?");
        builder.setPositiveButton("Tak, poproszę", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i){
                Toast.makeText(MainActivity.this, "Powiadomienie zostało wysłane!", Toast.LENGTH_SHORT).show();
                    PowiadomieniaHelper.pokazPowiadomienie(
                        MainActivity.this, "Witaj!", "Miło cie widziec," + imie +"!", 1,PowiadomieniaHelper.KANAL_NISKI);
                    }
        });

        builder.setNegativeButton("Nie, dziękuję", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Rozumiem. Nie wysyłam powiadomienia.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }
    }

