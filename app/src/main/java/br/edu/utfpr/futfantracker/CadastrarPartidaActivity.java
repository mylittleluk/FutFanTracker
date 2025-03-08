package br.edu.utfpr.futfantracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastrarPartidaActivity extends AppCompatActivity {

    private EditText editTextData, editTextHorario, editTextAdversario;
    private RadioButton radioButtonCasa, radioButtonFora;
    private CheckBox checkBoxAcompanheiPartida;
    private Button buttonSalvar, buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_partida);

        editTextData = findViewById(R.id.editTextData);
        editTextHorario = findViewById(R.id.editTextHorario);
        editTextAdversario = findViewById(R.id.editTextAdversario);
        radioButtonCasa = findViewById(R.id.radioButtonCasa);
        radioButtonFora = findViewById(R.id.radioButtonFora);
        checkBoxAcompanheiPartida = findViewById(R.id.checkBoxAcompanheiPartida);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonLimpar = findViewById(R.id.buttonLimpar);
    }

    public void limparCampos(View view){
        editTextData.setText(null);
        editTextHorario.setText(null);
        editTextAdversario.setText(null);
        radioButtonCasa.setChecked(false);
        radioButtonFora.setChecked(false);
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

        // Validação RadioButton Local
        if((radioButtonCasa.isChecked() == false && radioButtonFora.isChecked()==false) ||
                (radioButtonFora.isChecked()==true && radioButtonCasa.isChecked()==true)){
            Toast.makeText(this,
                    R.string.por_favor_preencha_o_local_adequadamente,
                    Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this,
                       "Data: "+dataFormatada+"\n"
                       +"Horário: "+horarioFormatado,
                       Toast.LENGTH_LONG).show();
    }
}