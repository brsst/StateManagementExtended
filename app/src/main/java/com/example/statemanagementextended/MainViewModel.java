package com.example.statemanagementextended;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private int licznik = 0;

    // zwraca wartość licznika
    public int pobierzLicznik() {
        return licznik;
    }

    // zwiększa wartość licznika
    public void zwiekszLicznik() {
        licznik++;
    }
}