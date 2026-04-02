package com.example.projeto1_titulo_eleitor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText data;
    private Button btnContinuar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nome = findViewById(R.id.nome);
        data = findViewById(R.id.data);
        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(v -> {

            String txtNome = nome.getText().toString().trim();
            String txtData = data.getText().toString().trim();

            if (camposEstaoCorretos(txtNome, txtData)) {
                Intent intent = new Intent(MainActivity.this, RelatorioActivity.class);
                intent.putExtra("nome", txtNome);
                intent.putExtra("data", txtData);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Por favor preencha os campos corretamente!",
                        Toast.LENGTH_SHORT).show();
            }

        });

        data.addTextChangedListener(new TextWatcher() {
            boolean estaAtualizando = false;

            @Override
            public void afterTextChanged(Editable s) {
                if(estaAtualizando) {
                    return;
                }
                estaAtualizando = true;

                String limpo = s.toString().replaceAll("[^\\d]", "");

                if (limpo.isEmpty()) {
                    data.setText("");
                    estaAtualizando = false;
                    return;
                }

                if (limpo.length() > 8) {
                    limpo = limpo.substring(limpo.length() - 8);
                } else {
                    limpo = String.format(Locale.getDefault(),
                            "%08d", Integer.parseInt(limpo));
                }

                String dia = limpo.substring(0,2);
                String mes = limpo.substring(2,4);
                String ano = limpo.substring(4,8);

                String dataFormatada = dia + "/" + mes + "/" + ano;

                data.setText(dataFormatada);
                data.setSelection(dataFormatada.length());
                estaAtualizando = false;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    private boolean camposEstaoCorretos(String nome, String data) {
        //verifica se os campos estão vazios
        if (nome.isEmpty() || data.isEmpty()) { return false; }

        int anoAtual = LocalDate.now().getYear();

        String[] partes = data.split("/");
        
        if (partes.length > 3) { return false; }

        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        int[] mesesCom30 = {4, 6, 9,11};

        // verifica datas impossíveis
        if (dia < 1 || dia > 31 ||
                mes < 1 || mes > 12 ||
                ano > anoAtual || ano < (anoAtual - 110))
        {return false;}

        // meses com 30 dias não podem ter dia 31
        if (Arrays.binarySearch(mesesCom30, mes) > -1 && dia > 30) {return false;}

        if (mes == 2 && dia > 29) {return false;}

        return true;
    }
}