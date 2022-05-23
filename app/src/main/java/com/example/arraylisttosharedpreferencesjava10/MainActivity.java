package com.example.arraylisttosharedpreferencesjava10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Declaração de Variáveis de Escopo Global: */
    EditText edTxtName;
    EditText edtTxtAge;
    TextView txtVwList;
    Button btnSave;
    ArrayList<ModelClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Inicialização de Variáveis: */
        edTxtName = findViewById(R.id.edtTxt_addItem_actvtMain);
        edtTxtAge = findViewById(R.id.edt_age);
        txtVwList = findViewById(R.id.tv_size);
        btnSave = findViewById(R.id.btn_addItem_actvtMain);
        loadData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(edTxtName.getText().toString(), edtTxtAge.getText().toString());
            }
        });

    }

    private void saveData(String name, String age) {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        arrayList.add(new ModelClass(name, Integer.parseInt(age)));
        String json = gson.toJson(arrayList);
        editor.putString("student_data", json);
        editor.apply();
        txtVwList.setText("List Data \n");
        loadData();

    }

    private void loadData() {

        SharedPreferences sharedPreferences = getApplicationContext().
                getSharedPreferences("DATA", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("student_data", null);

        Type type = new TypeToken<ArrayList<ModelClass>>() {
        }.getType();

        arrayList = gson.fromJson(json, type);

        if (arrayList == null) {

            arrayList = new ArrayList<>();
            txtVwList.setText("" + 0);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                txtVwList.setText(txtVwList.getText().toString() + "\n" + arrayList.get(i).name + "\n");
            }
        }
    }
}
