package com.example.statemanagementextended;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private TextView licznikTextView;
    private EditText poleWprowadzania;
    private CheckBox checkboxPokazUkryty;
    private TextView ukrytyTextView;
    private Switch przelacznikTrybuNocnego;
    private boolean czyTrybNocny;

    private SharedPreferences preferencje;
    private SharedPreferences.Editor edytorPreferencji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicjalizujWidoki();

        konfigurujTrybNocny();

        konfigurujWidocznoscCheckboxa();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        aktualizujTekstLicznika();

        konfigurujPrzyciskZwiekszania();
    }

    private void inicjalizujWidoki() {
        licznikTextView = findViewById(R.id.textViewCount);
        poleWprowadzania = findViewById(R.id.userInput);
        przelacznikTrybuNocnego = findViewById(R.id.switchChoice);
        checkboxPokazUkryty = findViewById(R.id.checkBoxChoice);
        ukrytyTextView = findViewById(R.id.invisibleText);
    }

    private void konfigurujTrybNocny() {
        preferencje = getSharedPreferences("Tryb", Context.MODE_PRIVATE);
        czyTrybNocny = preferencje.getBoolean("nocny", false);

        przelacznikTrybuNocnego.setChecked(czyTrybNocny);
        ustawTrybNocny(czyTrybNocny);

        przelacznikTrybuNocnego.setOnClickListener(view -> {
            czyTrybNocny = !czyTrybNocny;
            ustawTrybNocny(czyTrybNocny);
            edytorPreferencji = preferencje.edit();
            edytorPreferencji.putBoolean("nocny", czyTrybNocny);
            edytorPreferencji.apply();
        });
    }

    private void ustawTrybNocny(boolean trybNocny) {
        AppCompatDelegate.setDefaultNightMode(trybNocny ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void konfigurujWidocznoscCheckboxa() {
        ukrytyTextView.setVisibility(View.INVISIBLE);
        boolean czyTekstWidoczny = preferencje.getBoolean("tekstWidoczny", false);
        ukrytyTextView.setVisibility(czyTekstWidoczny ? View.VISIBLE : View.INVISIBLE);
        checkboxPokazUkryty.setChecked(czyTekstWidoczny);

        checkboxPokazUkryty.setOnClickListener(view -> {
            ukrytyTextView.setVisibility(checkboxPokazUkryty.isChecked() ? View.VISIBLE : View.INVISIBLE);
            edytorPreferencji = preferencje.edit();
            edytorPreferencji.putBoolean("tekstWidoczny", checkboxPokazUkryty.isChecked());
            edytorPreferencji.apply();
        });
    }

    private void konfigurujPrzyciskZwiekszania() {
        Button przyciskZwieksz = findViewById(R.id.buttonIncrement);
        przyciskZwieksz.setOnClickListener(view -> {
            viewModel.zwiekszLicznik();
            aktualizujTekstLicznika();
        });
    }

    private void aktualizujTekstLicznika() {
        licznikTextView.setText("Licznik: " + viewModel.pobierzLicznik());
    }
}