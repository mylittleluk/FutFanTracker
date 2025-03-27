package br.edu.utfpr.futfantracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;

import br.edu.utfpr.futfantracker.utils.UtilsAlert;

public class CadastrarPartidaActivity extends AppCompatActivity {

    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_HORARIO = "KEY_HORARIO";
    public static final String KEY_ADVERSARIO = "KEY_ADVERSARIO";
    public static final String KEY_LOCAL = "KEY_LOCAL";
    public static final String KEY_COMPETICAO = "KEY_COMPETICAO";
    public static final String KEY_ACOMPANHEI_PARTIDA = "KEY_ACOMPANHEI_PARTIDA";
    public static final String KEY_PARTIDA_OCORREU = "KEY_PARTIDA_OCORREU";
    public static final String KEY_RESULTADO_CASA = "KEY_RESULTADO_CASA";
    public static final String KEY_RESULTADO_FORA = "KEY_RESULTADO_FORA";
    public static final String KEY_MODO = "MODO";
    public static final String KEY_SUGERIR_COMPETICAO = "SUGERIR_COMPETICAO";
    public static final String KEY_ULTIMA_COMPETICAO = "ULTIMA_COMPETICAO";
    public static final int MODO_NOVO = 0;
    public static final int MODO_EDITAR = 1;
    private int modo;
    private Partida partidaOriginal;
    private boolean sugerirCompeticao = false;
    private int ultimaCompeticao = 0;
    private EditText editTextData, editTextHorario, editTextAdversario, editTextResultadoCasa, editTextResultadoFora;
    private RadioGroup radioGroupLocal;
    private CheckBox checkBoxAcompanheiPartida, checkBoxPartidaOcorreu;
    private Spinner spinnerCompeticao;
    private RadioButton radioButtonCasa, radioButtonFora;


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
        radioButtonCasa = findViewById(R.id.radioButtonCasa);
        radioButtonFora = findViewById(R.id.radioButtonFora);

        // Lê a preferencia presente no SharedPreferences direto na criação da activity
        lerPreferencias();

        editTextResultadoCasa.setEnabled(false);
        editTextResultadoFora.setEnabled(false);
        checkBoxAcompanheiPartida.setEnabled(false);

        // Recupera a intent que foi usada pra abrir a activity
        Intent intentAbertura = getIntent();

        // Recupero as extras que foram passadas na intent
        Bundle bundle = intentAbertura.getExtras();

        if(bundle != null){
            // recupera a chave de modo que veio na intent de abertura da activity
            modo = bundle.getInt(KEY_MODO);
            if(modo == MODO_NOVO){
                setTitle(getString(R.string.cadastrar_partida));

                // Reflete o valor salvo no SharedPreferences direto no elemento da Activity
                if(sugerirCompeticao){
                    spinnerCompeticao.setSelection(ultimaCompeticao);
                }

            } else {
                // Alterando o titulo da activity conforme o modo
                setTitle(getString(R.string.editar_partida));

                // Recuperando os parametros do objeto partida recebidos via intent de
                // abertura da activity
                String data = bundle.getString(CadastrarPartidaActivity.KEY_DATA);
                String horario = bundle.getString(CadastrarPartidaActivity.KEY_HORARIO);
                String adversario = bundle.getString(CadastrarPartidaActivity.KEY_ADVERSARIO);
                String localTexto = bundle.getString(CadastrarPartidaActivity.KEY_LOCAL);
                int competicao = bundle.getInt(CadastrarPartidaActivity.KEY_COMPETICAO);
                int resultadoCasa = bundle.getInt(CadastrarPartidaActivity.KEY_RESULTADO_CASA);
                int resultadoFora = bundle.getInt(CadastrarPartidaActivity.KEY_RESULTADO_FORA);
                boolean jaOcorreu = bundle.getBoolean(CadastrarPartidaActivity.KEY_PARTIDA_OCORREU);
                boolean acompanheiPartida = bundle.getBoolean(CadastrarPartidaActivity.KEY_ACOMPANHEI_PARTIDA);
                Local local = Local.valueOf(localTexto);

                partidaOriginal = new Partida(data, horario, adversario, local, competicao, resultadoCasa, resultadoFora, jaOcorreu, acompanheiPartida);

                // Usar os parametros recuperados para atualizar os elementos da activity
                editTextData.setText(data);
                editTextHorario.setText(horario);
                editTextAdversario.setText(adversario);
                if(local == Local.Casa){
                    radioButtonCasa.setChecked(true);
                } else if (local == Local.Fora) {
                    radioButtonFora.setChecked(true);
                }
                spinnerCompeticao.setSelection(competicao);
                editTextResultadoCasa.setText(String.valueOf(resultadoCasa));
                editTextResultadoFora.setText(String.valueOf(resultadoFora));
                checkBoxPartidaOcorreu.setChecked(jaOcorreu);
                checkBoxAcompanheiPartida.setChecked(acompanheiPartida);
//                if(!jaOcorreu){
//                    editTextResultadoCasa.setEnabled(false);
//                    editTextResultadoFora.setEnabled(false);
//                    checkBoxAcompanheiPartida.setEnabled(false);
//                }
            }
        }

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

    public void limparCampos(){

        final String data = editTextData.getText().toString();
        final String horario = editTextHorario.getText().toString();
        final String adversario = editTextAdversario.getText().toString();
        final int local = radioGroupLocal.getCheckedRadioButtonId();
        final boolean partidaOcorreu = checkBoxPartidaOcorreu.isChecked();
        final boolean acompanheiPartida = checkBoxAcompanheiPartida.isChecked();
        final String resultadoCasa = editTextResultadoCasa.getText().toString();
        final String resultadoFora = editTextResultadoFora.getText().toString();
        final int competicao = spinnerCompeticao.getSelectedItemPosition();


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

        final ScrollView scrollView = findViewById(R.id.main);
        final View viewComFoco = scrollView.findFocus();
        Snackbar snackbar = Snackbar.make(scrollView,
                                          R.string.os_campos_foram_limpos,
                                          Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextData.setText(data);
                editTextHorario.setText(horario);
                editTextAdversario.setText(adversario);
                checkBoxPartidaOcorreu.setChecked(partidaOcorreu);
                checkBoxAcompanheiPartida.setChecked(acompanheiPartida);
                spinnerCompeticao.setSelection(competicao);
                editTextResultadoCasa.setText(resultadoCasa);
                editTextResultadoFora.setText(resultadoFora);

                if(partidaOcorreu){
                    checkBoxAcompanheiPartida.setChecked(acompanheiPartida);
                    if(acompanheiPartida){
                        checkBoxAcompanheiPartida.setEnabled(acompanheiPartida);
                    }
                } else {
                    checkBoxAcompanheiPartida.setChecked(false);
                    checkBoxAcompanheiPartida.setEnabled(false);
                }

                if(local == R.id.radioButtonCasa){
                    radioButtonCasa.setChecked(true);
                } else if (local == R.id.radioButtonFora) {
                    radioButtonFora.setChecked(true);
                }

                if(viewComFoco != null){
                    viewComFoco.requestFocus();
                }
            }
        });

        snackbar.show();
        //UtilsAlert.mostrarAviso(this, R.string.os_campos_foram_limpos);
    }

    public void salvarCampos(){
        String adversario = editTextAdversario.getText().toString();
        String dataInserida = editTextData.getText().toString();
        String horarioInserido = editTextHorario.getText().toString();
        Date dataValidada;
        Date horarioValidado;
        String dataFormatada;
        String horarioFormatado;
        boolean partidaOcorreu;
        int resultadoCasa=0 , resultadoFora=0;
        int competicao;
        boolean acompanheiPartida = false;

        // Validação Data
        SimpleDateFormat padraoEntradaData = new SimpleDateFormat("dd/MM/yyyy");
        padraoEntradaData.setLenient(false);

        try {
            dataValidada = padraoEntradaData.parse(dataInserida);
            dataFormatada = padraoEntradaData.format(dataValidada);

        } catch (Exception e){
            UtilsAlert.mostrarAviso(this, R.string.por_favor_preencha_uma_data_valida);
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
            UtilsAlert.mostrarAviso(this,R.string.por_favor_insira_um_horario_valido);
            editTextHorario.requestFocus();
            return;
        }

        // Validação Adversário
        if(adversario == null || adversario.trim().isEmpty()){
            UtilsAlert.mostrarAviso(this,R.string.por_favor_preencha_o_adversario);
            editTextAdversario.requestFocus();
            return;
        }

        adversario = adversario.trim();

        Local localPartida;
        int radioButtonId = radioGroupLocal.getCheckedRadioButtonId();

        // Validação RadioButton Local
        if(radioButtonId == R.id.radioButtonCasa){
            localPartida = Local.Casa;
        } else if (radioButtonId == R.id.radioButtonFora){
            localPartida = Local.Fora;
        } else {
            UtilsAlert.mostrarAviso(this,R.string.por_favor_preencha_o_local_adequadamente);
            return;
        }

        // Check Spinner competições
        competicao = spinnerCompeticao.getSelectedItemPosition();
        if(competicao == AdapterView.INVALID_POSITION){
            UtilsAlert.mostrarAviso(this,R.string.o_spinner_competicoes_nao_possui_valores);
            return;
        }

        // Validação Sobre a Partida
        partidaOcorreu = checkBoxPartidaOcorreu.isChecked();
        //boolean resultadosValidos = false;


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
                //resultadosValidos = true;
            }catch(Exception e){
                UtilsAlert.mostrarAviso(this,R.string.por_favor_insira_o_resultado_adequadamente);
                return;
            }
        }

        // Checagem do Estado do Checkbox
        if(checkBoxAcompanheiPartida.isEnabled()){
            if(checkBoxAcompanheiPartida.isChecked()){
                acompanheiPartida = true;
            }
        } else {
            acompanheiPartida = false;
        }

        if(modo == MODO_EDITAR &&
                dataFormatada.equalsIgnoreCase(partidaOriginal.getData()) &&
                horarioFormatado.equalsIgnoreCase(partidaOriginal.getHorario()) &&
                adversario.equalsIgnoreCase(partidaOriginal.getAdversario()) &&
                (localPartida.toString()).equalsIgnoreCase(String.valueOf(partidaOriginal.getLocal())) &&
                competicao == partidaOriginal.getCompeticao() &&
                acompanheiPartida == partidaOriginal.isAcompanheiPartida() &&
                partidaOcorreu == partidaOriginal.isJaOcorreu() &&
                resultadoCasa == partidaOriginal.getResultadoCasa() &&
                resultadoFora == partidaOriginal.getResultadoFora()){

            // Valores iguais, sem alteração
            setResult(CadastrarPartidaActivity.RESULT_CANCELED);
            finish();
            return;

        }

        // salvar a ultima cometicao selecionada no sharedpreferences
        salvarUltimaCompeticao(competicao);

        Intent intentResposta = new Intent();
        intentResposta.putExtra(KEY_DATA, dataFormatada);
        intentResposta.putExtra(KEY_HORARIO, horarioFormatado);
        intentResposta.putExtra(KEY_ADVERSARIO, adversario);
        intentResposta.putExtra(KEY_LOCAL, localPartida.toString());
        intentResposta.putExtra(KEY_COMPETICAO, competicao);
        intentResposta.putExtra(KEY_ACOMPANHEI_PARTIDA, acompanheiPartida);
        intentResposta.putExtra(KEY_PARTIDA_OCORREU, partidaOcorreu);
        intentResposta.putExtra(KEY_RESULTADO_CASA, resultadoCasa);
        intentResposta.putExtra(KEY_RESULTADO_FORA, resultadoFora);

        setResult(CadastrarPartidaActivity.RESULT_OK, intentResposta);
        finish();


//        Toast.makeText(this,
//                       getString(R.string.data_valor)+dataFormatada+"\n"
//                       +getString(R.string.horario_valor)+horarioFormatado+"\n"
//                       +getString(R.string.adversario_valor)+adversario+"\n"
//                       +getString(R.string.local_valor)+localPartida+"\n"
//                       +getString(R.string.competicao_valor)+competicao+"\n"
//                       +(acompanheiPartida ? getString(R.string.acompanhei_a_partida) : getString(R.string.nao_acompanhei_a_partida))+"\n"
//                       +(partidaOcorreu && resultadosValidos ? getString(R.string.placar_valor)+resultadoCasa+getString(R.string.x_valor)+resultadoFora : getString(R.string.partida_ainda_nao_ocorreu) ),
//                       Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastrar_partida_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Reflete o valor do SharedPreferences na criação do menu
        MenuItem item = menu.findItem(R.id.menuItemSugerirCompeticao);
        item.setChecked(sugerirCompeticao);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();
        if(idMenuItem == R.id.menuItemSalvar){
            salvarCampos();
            return true;
        } else if(idMenuItem == R.id.menuItemLimpar){
            limparCampos();
            return true;
        } else if (idMenuItem == R.id.menuItemSugerirCompeticao){
            boolean menuItemValor = !item.isChecked();
            salvarSugerirCompeticao(menuItemValor);
            item.setChecked(menuItemValor);

            // Ao ativar a opção, o elemento já é imediatamente atualizado com o ultimo valor
            if(sugerirCompeticao){
                spinnerCompeticao.setSelection(ultimaCompeticao);
            }

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void lerPreferencias(){
        SharedPreferences shared = getSharedPreferences(ListarPartidasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        shared.getBoolean(KEY_SUGERIR_COMPETICAO, sugerirCompeticao);
        shared.getInt(KEY_ULTIMA_COMPETICAO, ultimaCompeticao);
    }

    private void salvarSugerirCompeticao(boolean novoValor){
        SharedPreferences shared = getSharedPreferences(ListarPartidasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_SUGERIR_COMPETICAO, novoValor);
        editor.commit();
        sugerirCompeticao = novoValor;
    }

    private void salvarUltimaCompeticao(int novoValor){
        SharedPreferences shared = getSharedPreferences(ListarPartidasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(KEY_ULTIMA_COMPETICAO, novoValor);
        editor.commit();
        ultimaCompeticao = novoValor;
    }


}