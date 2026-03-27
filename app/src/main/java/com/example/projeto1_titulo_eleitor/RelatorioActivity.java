package com.example.projeto1_titulo_eleitor;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class RelatorioActivity extends AppCompatActivity {

//    private String texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_relatorio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        String nome = getIntent().getStringExtra("nome");
        String data = getIntent().getStringExtra("data");

        LocalDate anoRecebido = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate anoAtual = LocalDate.now();

        long idade = Period.between(anoRecebido, anoAtual).getYears();

        String situacao;
        if (idade < 16){
            situacao = "Não pode votar";
        } else if (idade < 18) {
            situacao = "Voto opcional";
        } else if (idade < 70) {
            situacao = "Voto obrigatório";
        } else {
            situacao = "Voto opcional";
        }



    }
}