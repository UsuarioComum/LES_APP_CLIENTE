package com.example.cliente.viewModel.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cliente.R;
import com.example.cliente.viewModel.ProdutoAdapter;
import com.example.cliente.model.Produtos;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProdutosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProdutosFragment() {
        // Required empty public constructor
    }

    public static ProdutosFragment newInstance(String param1, String param2) {
        ProdutosFragment fragment = new ProdutosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    RecyclerView recyclerView;
    DatabaseReference produtosDB;
    ProdutoAdapter prodAdapter;
    ArrayList<Produtos> lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produtos, container, false);

        // Funcional, comentado apenas para testes
        recyclerView = view.findViewById(R.id.rvProdutos);
        produtosDB = FirebaseDatabase.getInstance().getReference("Produtos");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext())); // view.getContext() == this    ~(Neste caso)

        lista = new ArrayList<>();


        ArrayList<Produtos> ltProd = new ArrayList<>();

        // Acredito que o erro de Adição Inteira da Lista, ocorre aqui
        produtosDB.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.exists()) {
                        Produtos produtos = dataSnapshot.getValue(Produtos.class);
                        lista.add(produtos);
                        Log.d("PdFrag", "->>>> "+lista.size());
                    }

                }
                prodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Erro", "Não foi adicionado ao Realtime DB");
            }
        });
        prodAdapter = new ProdutoAdapter(view.getContext(), lista);
        recyclerView.setAdapter(prodAdapter);
        return view;
    }
}
