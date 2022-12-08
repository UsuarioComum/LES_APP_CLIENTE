package com.example.cliente.viewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.cliente.R;
import com.example.cliente.viewModel.fragments.CarrinhoFragment;
import com.example.cliente.viewModel.fragments.PerfilFragment;
import com.example.cliente.viewModel.fragments.ProdutosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mudarFragment(new ProdutosFragment());

         BottomNavigationView navSecoes = findViewById(R.id.navSecoes);
         navSecoes.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.item_produto:
                    mudarFragment(new ProdutosFragment());
                    break;
                case R.id.item_carrinho:
                    mudarFragment(new CarrinhoFragment());
                    break;
                case R.id.item_perfil:
                    mudarFragment(new PerfilFragment());
                    break;
            }
            return true;
        });
    }

    private void mudarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
