package com.example.entendasuareceita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ListarReceitas extends AppCompatActivity {

    private ListView listaReceitas;
    private Button btnNovaReceita;

    private List<Receita> receitas = new ArrayList<>();
    private ArrayAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    ChildEventListener childEventListener;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_receitas);

        listaReceitas = findViewById(R.id.listView);
        btnNovaReceita = findViewById(R.id.btnAdicionarReceita);

        //Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        btnNovaReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarReceitas.this, MainActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity( intent );
            }
        });

    }
    protected void onStart() {
        super.onStart();
        carregarReceitas();

        receitas.clear();
        query = reference.child("livros");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //datasnapshot é filho do livro, cada hash id

                Receita receita = new Receita();
                receita.setId(snapshot.getKey());
                receita.setNomeMedicamento(snapshot.child("nomeMedicamento").getValue(String.class));
                receita.setQuantidade(snapshot.child("quantidade").getValue(int.class));
                receita.setComoTomar(snapshot.child("comoTomar").getValue(String.class));
                receita.setTipo(snapshot.child("tipoPosologia").getValue(TipoPosologia.class));
                receitas.add(receita);
                //avisar ao adapter qe a lista foi modificada
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String idReceita = snapshot.getKey();
                for(Receita r : receitas){
                    if(r.getId().equals( idReceita )){
                        r.setNomeMedicamento(snapshot.child("nomeMedicamento").getValue(String.class));
                        r.setQuantidade(snapshot.child("quantidade").getValue(int.class));
                        r.setComoTomar(snapshot.child("comoTomar").getValue(String.class));
                        r.setTipo(snapshot.child("tipoPosologia").getValue(TipoPosologia.class));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String idReceita = snapshot.getKey();
                for(Receita r : receitas){
                    if(r.getId().equals( idReceita )){
                        receitas.remove( r );
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addChildEventListener( childEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener( childEventListener );
    }
    private void carregarReceitas(){
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, receitas);
        listaReceitas.setAdapter( adapter );
    }
}
