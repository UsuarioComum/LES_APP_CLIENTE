package com.example.cliente.viewModel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cliente.R;
import com.example.cliente.viewModel.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.santalu.maskara.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;


public class PerfilFragment extends Fragment {

    private EditText edtNome, edtRua, edtBairro, edtComplemento;
    private MaskEditText edtCPF, edtNumero;
    FirebaseFirestore userDB = FirebaseFirestore.getInstance();
    String userID;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtNome = view.findViewById(R.id.edtNome_alt);
        //edtEmail = view.findViewById(R.id.edtEmail_alt);
        edtCPF = view.findViewById(R.id.edtCPF_alt);
        edtNumero = view.findViewById(R.id.edtNumero_alt);
        edtBairro = view.findViewById(R.id.edtBairro_alt);
        edtRua = view.findViewById(R.id.edtRua_alt);
        edtComplemento = view.findViewById(R.id.edtComplemento_alt);

        Button button = (Button) view.findViewById(R.id.btnDeslogar);
        button.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(view1.getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        Button alterar = view.findViewById(R.id.btnAlterar);
        alterar.setOnClickListener(v -> {
            if(!edtNome.getText().toString().isEmpty() && edtCPF.isDone() && edtNumero.isDone() &&
                    !edtBairro.getText().toString().isEmpty()  && !edtRua.getText().toString().isEmpty()) {
                CollectionReference docReference = userDB.collection("Usuarios");//.document(userID);
                docReference
                        .get()
                        .addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("OIAEITA_ALT", document.getId() + " => " + document.getData());
                                    if (document.getId().equals(userID)) {
                                        Toast.makeText(view.getContext(), "Dados alterados", Toast.LENGTH_SHORT).show();

                                        Map<String, Object> alterado = new HashMap<>();
                                        alterado.put("nome", edtNome.getText().toString().trim());
                                        //alterado.put("email", edtEmail.getText().toString());
                                        alterado.put("cpf", edtCPF.getUnMasked());
                                        alterado.put("numero", edtNumero.getUnMasked());
                                        alterado.put("bairro", edtBairro.getText().toString().trim());
                                        alterado.put("rua", edtRua.getText().toString().trim());
                                        alterado.put("complemento", edtComplemento.getText().toString().trim());
                                        docReference.document(userID).update(alterado);//.addOnCompleteListener()
                                        break;
                                    }
                                }
                            }
                        });
            } else {
                Toast.makeText(view.getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(
                                R.id.frame_layout,
                                new ProdutosFragment()
                        ).addToBackStack(null).commit();
                BottomNavigationView navSecoes = activity.findViewById(R.id.navSecoes);
                navSecoes.getMenu().findItem(R.id.item_produto).setChecked(true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        edtNome = view.findViewById(R.id.edtNome_alt);
        //edtEmail = view.findViewById(R.id.edtEmail_alt);
        edtCPF = view.findViewById(R.id.edtCPF_alt);
        edtNumero = view.findViewById(R.id.edtNumero_alt);
        edtBairro = view.findViewById(R.id.edtBairro_alt);
        edtRua = view.findViewById(R.id.edtRua_alt);
        edtComplemento = view.findViewById(R.id.edtComplemento_alt);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference docReference = userDB.collection("Usuarios").document(userID);
            docReference.addSnapshotListener((documentSnapshot, error) -> {
                if(documentSnapshot != null) {
                    edtNome.setText(documentSnapshot.getString("nome"));
                    //edtEmail.setText(email);
                    edtCPF.setText(documentSnapshot.getString("cpf"));
                    edtNumero.setText(documentSnapshot.getString("numero"));
                    edtBairro.setText(documentSnapshot.getString("bairro"));
                    edtRua.setText(documentSnapshot.getString("rua"));
                    edtComplemento.setText(documentSnapshot.getString("complemento"));
                }
            });
        }
        return view;
    }
}
