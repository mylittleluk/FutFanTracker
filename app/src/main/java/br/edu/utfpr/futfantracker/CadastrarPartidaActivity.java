package br.edu.utfpr.futfantracker;

import android.os.Bundle;
import android.view.View;
//import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;

public class CadastrarPartidaActivity extends AppCompatActivity {

    private EditText editTextData, editTextHorario, editTextAdversario, editTextResultadoCasa, editTextResultadoFora;
    private RadioGroup radioGroupLocal;
    private CheckBox checkBoxAcompanheiPartida, checkBoxPartidaOcorreu;
    private Spinner spinnerCompeticao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_partida);

        editTextData = findViewById(R.id.editTextData);
        editTextHorario = findViewById(R.id.editTextHorario);
        editTextAdversario = findViewById(R.id.editTextAdversario);
        editTextResultadoCasa = findViewById(R.id.editTextNumberResultadoCasa);
        editTextResultadoFora = findViewById(R.id.editTextNumberResultadoFora);
        radioGroupLocal = findViewById(R.id.radioGroupLocal);
        checkBoxAcompanheiPartida = findViewById(R.id.checkBoxAcompanheiPartida);
        checkBoxPartidaOcorreu = findViewById(R.id.checkBoxPartidaOcorreu);
        spinnerCompeticao = findViewById(R.id.spinnerCompeticao);

        editTextResultadoCasa.setEnabled(false);
        editTextResultadoFora.setEnabled(false);
        checkBoxAcompanheiPartida.setEnabled(false);

        //popularSpinner();

    }

//    private void popularSpinner(){
//        ArrayList<String> competicoes = new ArrayList<>();
//        competicoes.add(getString(R.string.campeonato_estadual));
//        competicoes.add(getString(R.string.campeonato_brasileiro));
//        competicoes.add(getString(R.string.copa_do_brasil));
//        competicoes.add(getString(R.string.copa_libertadores));
//        competicoes.add(getString(R.string.copa_sulamericana));
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                                                         android.R.layout.simple_list_item_1,
//                                                         competicoes);
//
//        spinnerCompeticao.setAdapter(adapter);
//    }

    public void limparCampos(View view){
        editTextData.setText(null);
        editTextHorario.setText(null);
        editTextAdversario.setText(null);
        editTextResultadoCasa.setText(null);
        editTextResultadoFora.setText(null);
        radioGroupLocal.clearCheck();
        checkBoxPartidaOcorreu.setChecked(false);
        checkBoxAcompanheiPartida.setChecked(false);
        checkBoxAcompanheiPartida.setEnabled(false);
        editTextResultadoCasa.setText(null);
        editTextResultadoCasa.setEnabled(false);
        editTextResultadoFora.setText(null);
        editTextResultadoFora.setEnabled(false);
        editTextData.requestFocus();
        spinnerCompeticao.setSelection(0);

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

        // Validação Adversário
        if(adversario == null || adversario.trim().isEmpty()){
            Toast.makeText(this,
                    R.string.por_favor_preencha_o_adversario,
                    Toast.LENGTH_LONG).show();
            editTextAdversario.requestFocus();
            return;
        }

        adversario = adversario.trim();

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

        // Check Spinner competições
        String competicao = (String) spinnerCompeticao.getSelectedItem();
        if(competicao == null){
            Toast.makeText(this,
                    R.string.o_spinner_competicoes_nao_possui_valores,
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Validação Sobre a Partida
        boolean partidaOcorreu = checkBoxPartidaOcorreu.isChecked();
        boolean resultadosValidos = false;
        int resultadoCasa=0 , resultadoFora =0;

        if(partidaOcorreu){
            try{
                String placarCasa = editTextResultadoCasa.getText().toString().trim();
                String placarFora = editTextResultadoFora.getText().toString().trim();

                // Validação Sobre campos inválidos ou vazios antes do Parse
                if((placarCasa==null || placarCasa.isEmpty()) ||
                        (placarFora==null || placarFora.isEmpty())){
                    throw new Exception();
                }

                resultadoCasa = Integer.parseInt(placarCasa);
                resultadoFora = Integer.parseInt(placarFora);

                // Validação sobre Placares Absurdos
                if((resultadoCasa<0 || resultadoCasa>=15) ||
                        (resultadoFora<0 || resultadoFora>=15)){
                            throw new Exception();
                }

                // Booleano de controle de resultados válidos
                resultadosValidos = true;
            }catch(Exception e){
                Toast.makeText(this,
                              R.string.por_favor_insira_o_resultado_adequadamente,
                              Toast.LENGTH_LONG).show();
                return;
            }
        }

        // Checagem do Estado do Checkbox
        boolean acompanheiPartida = false;
        if(checkBoxAcompanheiPartida.isEnabled()){
            if(checkBoxAcompanheiPartida.isChecked()){
                acompanheiPartida = true;
            }
        } else {
            acompanheiPartida = false;
        }

        Toast.makeText(this,
                       getString(R.string.data_valor)+dataFormatada+"\n"
                       +getString(R.string.horario_valor)+horarioFormatado+"\n"
                       +getString(R.string.adversario_valor)+adversario+"\n"
                       +getString(R.string.local_valor)+localPartida+"\n"
                       +getString(R.string.competicao_valor)+competicao+"\n"
                       +(acompanheiPartida ? getString(R.string.acompanhei_a_partida) : getString(R.string.nao_acompanhei_a_partida))+"\n"
                       +(partidaOcorreu && resultadosValidos ? getString(R.string.placar_valor)+resultadoCasa+getString(R.string.x_valor)+resultadoFora : getString(R.string.partida_ainda_nao_ocorreu) ),
                       Toast.LENGTH_LONG).show();
    }

    // Metodo de controle dos elementos da partida de acordo com estado do CheckBox
    public void partidaOcorreu(View view){
        if(checkBoxPartidaOcorreu.isChecked()){
            checkBoxAcompanheiPartida.setEnabled(true);
            editTextResultadoCasa.setEnabled(true);
            editTextResultadoFora.setEnabled(true);
        } else {
            checkBoxAcompanheiPartida.setChecked(false);
            checkBoxAcompanheiPartida.setEnabled(false);
            editTextResultadoCasa.setText(null);
            editTextResultadoCasa.setEnabled(false);
            editTextResultadoFora.setText(null);
            editTextResultadoFora.setEnabled(false);
        }
    }
}