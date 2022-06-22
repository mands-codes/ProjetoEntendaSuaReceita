package com.example.entendasuareceita;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spTiposPosologia;
    private EditText etMedicamento, etQuantidade, etDurante, etHoras1;
    private Button btnTraduzir, btnSalvar, btnCancelar, btnDefinirAlarme, btnVisualizarAlarme;
    private TextView txtComoTomar, txtSugestaoHorarios, txtExibirHora, txtHorarioAlarme;

    int hora, minutos;
    FirebaseDatabase database;
    DatabaseReference reference;

    ChildEventListener childEventListener;
    Query query;

    public TimePickerDialog timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    spTiposPosologia = findViewById(R.id.spinnerTipos);

    etMedicamento = findViewById(R.id.editNomeMedicamento);
    etQuantidade = findViewById(R.id.editQuantidade);
    etDurante = findViewById(R.id.editDuracao);
    etHoras1 = findViewById(R.id.editHora1);

    txtExibirHora = findViewById(R.id.txtHora2);
    txtComoTomar = findViewById(R.id.txtComoTomar);
    txtHorarioAlarme = findViewById(R.id.txtHorarioAlarme);

    txtSugestaoHorarios = findViewById(R.id.txtSugestoes);
    btnTraduzir = findViewById(R.id.btnTraduzir);
    btnSalvar = findViewById(R.id.btnSalvar);
    btnCancelar = findViewById(R.id.btnCancelar);
    btnDefinirAlarme = findViewById(R.id.btnDefinirAlarme);
    btnVisualizarAlarme = findViewById(R.id.btnAbrirAlarme);

    carregarTipos();

        //Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


        btnTraduzir.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calcularTempos();
        }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        btnDefinirAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hora = hourOfDay;
                        minutos = minute;
                        Calendar calendario = Calendar.getInstance();
                        calendario.set(0,0,0,hora, minutos);
                        txtHorarioAlarme.setText(hora+":"+minutos);
                    }
                }, hora, minutos, true);
                timePicker.setTitle("Selecionar horário");
                timePicker.show();
            }
        });

        btnVisualizarAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = txtHorarioAlarme.getText().toString();
                String mensagem = "Tomar o remedio " +  etMedicamento;
                if(!texto.isEmpty()){
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, hora);
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, minutos);
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, mensagem);
                    startActivity(intent);
                }
            }
        });
}
    public void calcularTempos(){

        int hora = 0;
        int resultado = 0;

        hora =  Integer.parseInt(etHoras1.getText().toString());

        resultado = 24 / hora;

        String mensagem = "Você precisará tomar a medicação " + resultado + " vezes ao dia, por " + etDurante.getText().toString();
        txtComoTomar.setText(mensagem);


        switch (hora){
            case 4:
                txtSugestaoHorarios.setText("Você poderá tomar o medicamento nos horários: \n 08h - 12h - 16h - 20h - 00h - 04h");
                break;
            case 6:
                txtSugestaoHorarios.setText("Você poderá tomar o medicamento nos horários: \n 06h - 12h - 18h - 00h");
                break;
            case 8:
                txtSugestaoHorarios.setText("Você poderá tomar o medicamento nos horários: \n 08h - 16h - 00h ");
                break;
            case 12:
                txtSugestaoHorarios.setText("Você poderá tomar o medicamento nos horários: \n 08h - 20h OU 10h - 22h");
                break;
        }

    }

    public void carregarTipos(){

        TipoPosologia comprimidos = new TipoPosologia(0, "comprimidos");
        TipoPosologia ml = new TipoPosologia(0, "ml");
        TipoPosologia gotas = new TipoPosologia(0, "gotas");
        TipoPosologia sache = new TipoPosologia(0, "sache");

        List<TipoPosologia> lista = new ArrayList<>();
        lista.add(0, comprimidos);
        lista.add(1, gotas);
        lista.add(2, sache);
        lista.add(3,ml);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista );
        spTiposPosologia.setAdapter( adapter );

    }

    private void salvar() {
        Receita receita = new Receita();

        String medicamento = etMedicamento.getText().toString();
        int quantidade = Integer.parseInt(etQuantidade.getText().toString());
        String comoTomar = txtComoTomar.getText().toString();

        if (!medicamento.isEmpty() && spTiposPosologia.getSelectedItemPosition() > 0) {
            receita.setNomeMedicamento(medicamento);
            receita.setTipo((TipoPosologia) spTiposPosologia.getSelectedItem());
            receita.setComoTomar(comoTomar);
            receita.setQuantidade(quantidade);
            ////salvar no firebase
            database = FirebaseDatabase.getInstance();
            reference = database.getReference();
            reference.child("receitas").push().setValue(receita);


            finish();
        }
    }
    }