package com.example.cliente.viewModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cliente.R;
import com.example.cliente.model.Produtos;
import com.example.cliente.viewModel.fragments.ItemFragment;

import java.util.ArrayList;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {

    Context context;
    ArrayList<Produtos> lista;

    public ProdutoAdapter(Context context, ArrayList<Produtos> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itens_produto, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produtos produto = lista.get(position);
        holder.nome.setText(produto.getNome());
        holder.sabor.setText(produto.getSabor());
        holder.valor.setText(produto.getValor());
        Glide.with(holder.imagem.getContext())
                .load(produto.getImagem())
                .into(holder.imagem);
        Log.d("Esse", "posicao -> "+position);
        Log.d("Esse", "tamanho -> "+lista.size());

        /*Intent intent = new Intent(context, ProdutosFragment.class);
        context.startActivity(intent);
        ((Activity)context).finish();
        ((Activity)context).overridePendingTransition(0,0);*/

        holder.imagem.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.frame_layout,
                            new ItemFragment(
                                    produto.getNome(),
                                    produto.getSabor(),
                                    produto.getValor(),
                                    produto.getImagem()
                            )
                    ).addToBackStack(null).commit();
        });
        holder.nome.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.frame_layout,
                            new ItemFragment(
                                    produto.getNome(),
                                    produto.getSabor(),
                                    produto.getValor(),
                                    produto.getImagem()
                            )
                    ).addToBackStack(null).commit();
        });
        holder.sabor.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.frame_layout,
                            new ItemFragment(
                                    produto.getNome(),
                                    produto.getSabor(),
                                    produto.getValor(),
                                    produto.getImagem()
                            )
                    ).addToBackStack(null).commit();
        });
        holder.valor.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.frame_layout,
                            new ItemFragment(
                                    produto.getNome(),
                                    produto.getSabor(),
                                    produto.getValor(),
                                    produto.getImagem()
                            )
                    ).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome, sabor, valor;
        ImageView imagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome_itens);
            sabor = itemView.findViewById(R.id.txtSabor_itens);
            valor = itemView.findViewById(R.id.txtValor_itens);
            imagem = itemView.findViewById(R.id.imgItens);
        }
    }
}
