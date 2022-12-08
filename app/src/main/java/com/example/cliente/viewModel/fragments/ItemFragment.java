package com.example.cliente.viewModel.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cliente.R;
import com.example.cliente.model.carrinhoModel;

import java.util.ArrayList;

public class ItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String nome, sabor, valor, imagem;

    public ItemFragment() {
    }

    public ItemFragment(String nome, String sabor, String valor, String imagem) {
        this.nome = nome;
        this.sabor = sabor;
        this.valor = valor;
        this.imagem = imagem;
    }

    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    // Variaveis a serem acessadas pelo carrinhoFragment
    static ArrayList<carrinhoModel> itemADD = new ArrayList<>();
    static Double somaTOTAL = 00.00;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_item, container, false);

        ImageView imgHolder = view.findViewById(R.id.imgItem);
        TextView nomeHolder = view.findViewById(R.id.txtNome_ProdItem);
        TextView saborHolder = view.findViewById(R.id.txtSabor_ProdItem);
        TextView valorHolder = view.findViewById(R.id.txtValor_ProdItem);

        nomeHolder.setText(nome);
        saborHolder.setText(sabor);
        valorHolder.setText(valor);
        Glide.with(getContext()).load(imagem).into(imgHolder);

        Button adicionar = view.findViewById(R.id.btnADDcar);
        adicionar.setOnClickListener(v -> {
            Log.d("OIA", "nome -> "+nome);
            Log.d("OIA", "sabor -> "+sabor);
            Log.d("OIA", "valor -> "+valor);
            Log.d("OIA", "imagem -> "+imagem);

            carrinhoModel objADD = new carrinhoModel(imagem, nome, sabor, valor);
            itemADD.add(objADD);
            somaTOTAL += Double.parseDouble(valor.replace(",","."));


            Toast.makeText(v.getContext(), "Item adicionado ao carrinho", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
