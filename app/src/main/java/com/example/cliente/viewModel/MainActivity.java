package com.example.cliente.viewModel;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cliente.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Classes
        //usuarios = new Usuarios();

        // Views
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        // Quando clicado no botão Registrar, enviar o usuario para a tela de Cadastro
        btnRegister.setOnClickListener((View view) -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Quando clicado no botão Login, tentar efetuar o Login
        btnLogin.setOnClickListener((View view) -> {
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(view.getContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
            } else {
                autenticarUser();
            }

        });
    }
// Sempre que for "startada" ele verifica e faz o login automatico
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(userAtual != null) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }

    private void autenticarUser() {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(this, "Login efetuado com Sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                android.util.Log.w(TAG, "Autenticacão", task.getException());
                Toast.makeText(this,"Ocorreu um erro no login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
