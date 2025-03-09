package br.edu.utfpr.futfantracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastrarPartidaActivity extends AppCompatActivity {

    private EditText editTextData, editTextHorario, editTextAdversario;
    private RadioGroup radioGroupLocal;
    private CheckBox checkBoxAcompanheiPartida;
    private Button buttonSalvar, buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_partida);

        editTextData = findViewById(R.id.editTextData);
        editTextHorario = findViewById(R.id.editTextHorario);
        editTextAdversario = findViewById(R.id.editTextAdversario);
        radioGroupLocal = findViewById(R.id.radioGroupLocal);
        checkBoxAcompanheiPartida = findViewById(R.id.checkBoxAcompanheiPartida);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonLimpar = findViewById(R.id.buttonLimpar);
    }

    public void limparCampos(View view){
        editTextData.setText(null);
        editTextHorario.setText(null);
        editTextAdversario.setText(null);
        radioGroupLocal.clearCheck();
        checkBoxAcompanheiPartida.setChecked(false);
        editTextData.requestFocus();

        Toast.makeText(this,
                       getString(R.string.os_campos_foram_limpos),
                       Toast.LENGTH_LONG).show();
    }

    public void salvarCampos(View view){
        String adversario = editTextAdversario.getText().toString();
        String dataInserida = editTextData.getText().toString();
        String horarioInserido = editTextHorario.getText().toString();
        Date dataValidada;
        Date horarioValidado;
        String dataFormatada;
        String horarioFormatado;

        // Validação Adversário
        if(adversario == null || adversario.trim().isEmpty()){
            Toast.makeText(this,
                           R.string.por_favor_preencha_o_adversario,
                           Toast.LENGTH_LONG).show();
            editTextAdversario.requestFocus();
            return;
        }

        adversario = adversario.trim();

        // Validação Data
        SimpleDateFormat padraoEntradaData = new SimpleDateFormat("dd/MM/yyyy");
        padraoEntradaData.setLenient(false);

        try {
            dataValidada = padraoEntradaData.parse(dataInserida);
            dataFormatada = padraoEntradaData.format(dataValidada);

        } catch (Exception e){
            Toast.makeText(this,
                    R.string.por_favor_preencha_uma_data_valida,
                    Toast.LENGTH_LONG).show();
            editTextData.requestFocus();
            return;
        }

        // Validação Horário
        SimpleDateFormat padraoEntradaHorario = new SimpleDateFormat("HH:mm");
        padraoEntradaHorario.setLenient(false);

        try {
            horarioValidado = padraoEntradaHorario.parse(horarioInserido);
            horarioFormatado = padraoEntradaHorario.format(horarioValidado);
        } catch (Exception e){
            Toast.makeText(this,
                    R.string.por_favor_insira_um_horario_valido,
                    Toast.LENGTH_LONG).show();
            editTextHorario.requestFocus();
            return;
        }

        // Checagem do Estado do Checkbox
        boolean acompanheiPartida = checkBoxAcompanheiPartida.isChecked();

        String localPartida;
        int radioButtonId = radioGroupLocal.getCheckedRadioButtonId();

        // Validação RadioButton Local
        if(radioButtonId == R.id.radioButtonCasa){
            localPartida = getString(R.string.casa);
        } else if (radioButtonId == R.id.radioButtonFora){
            localPartida = getString(R.string.fora);
        } else {
            Toast.makeText(this,
                    R.string.por_favor_preencha_o_local_adequadamente,
                    Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this,
                       getString(R.string.data_valor)+dataFormatada+"\n"
                       +getString(R.string.horario_valor)+horarioFormatado+"\n"
                       +(acompanheiPartida ? getString(R.string.acompanhei_a_partida) : getString(R.string.nao_acompanhei_a_partida))+"\n"
                       +getString(R.string.adversario_valor)+adversario+"\n"
                       +getString(R.string.local_valor)+localPartida,
                       Toast.LENGTH_LONG).show();
    }
}