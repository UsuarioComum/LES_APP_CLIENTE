package com.example.cliente.viewModel;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cliente.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static EditText edtNome;   // EditNome - RG
    private static EditText edtEmail;  // EditEmail - RG
    private static EditText edtSenha;  // EditSenha - RG
    private static MaskEditText edtCPF;    // EditCpf - RG
    private static MaskEditText edtNumero; // EditNumero - RG
    private static EditText edtBairro; // EditBairro - RG
    private static EditText edtRua;    // EditRua - RG
    private static EditText edtComplemento; // EditComplemetno - RG

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNome = findViewById(R.id.edtNomeRg);
        edtEmail = findViewById(R.id.edtEmailRg);
        edtSenha = findViewById(R.id.edtSenhaRg);
        edtCPF = findViewById(R.id.edtCpfRg);
        edtNumero = findViewById(R.id.edtNumeroRg);
        edtBairro = findViewById(R.id.edtBairroRg);
        edtRua = findViewById(R.id.edtRuaRg);
        edtComplemento = findViewById(R.id.edtComplementoRg);
        Button btnCadastro = findViewById(R.id.btnCadastroRg);

        // Verifica se todos os campos estão preenchidos
        btnCadastro.setOnClickListener((View view) -> {
            String nome = edtNome.getText().toString();
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();
            String cpf = edtCPF.getUnMasked();
            String numero = edtNumero.getUnMasked();
            String bairro = edtBairro.getText().toString();
            String rua = edtRua.getText().toString();
            String complemento = edtComplemento.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() ||
            numero.isEmpty() || bairro.isEmpty() || rua.isEmpty() || complemento.isEmpty()) {
                Toast.makeText(view.getContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
            } else {
                registrarUsuario(view);
            }
        });
    }

    private void registrarUsuario(View view) {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
                    salvarDadosUser();
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish(); // Para encerrar o contexto da intent atual, dessa forma não apos sair da tela de cadastro não tera como o usuario voltar a tela em que estava antes :D
                    FirebaseAuth.getInstance().signOut();
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    String msgErro;

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        msgErro = "Senha fraca (minimo 6 caracteres)";
                    } catch (FirebaseAuthUserCollisionException e) {
                        msgErro = "Este email já esta cadastrado";
                    }  catch (FirebaseAuthInvalidCredentialsException e) {
                        msgErro = "Email inválido";
                    } catch (Exception e) {
                        msgErro = "Ocorreu um erro";
                    }

                    Toast.makeText(view.getContext(),msgErro,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void salvarDadosUser() {
        android.util.Log.d("salvarDadosUser()", "salvarDadosUser() foi chamado");
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String numero = edtNumero.getUnMasked();
        String cpf = edtCPF.getUnMasked();
        String bairro = edtBairro.getText().toString().trim();
        String rua = edtRua.getText().toString().trim();
        String complemento = edtComplemento.getText().toString().trim();

        FirebaseFirestore userDB = FirebaseFirestore.getInstance();

        Map<String, Object> users = new HashMap<>();
        users.put("nome", nome); // Chave "nome" com objeto nome
        users.put("email", email);
        users.put("numero", numero);
        users.put("cpf", cpf);
        users.put("bairro", bairro);
        users.put("rua", rua);
        users.put("complemento", complemento);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docReference = userDB.collection("Usuarios").document(userID);
        docReference.set(users);
    }
}
