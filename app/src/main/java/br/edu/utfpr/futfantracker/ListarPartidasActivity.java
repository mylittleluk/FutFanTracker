package br.edu.utfpr.futfantracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.widget.ArrayAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.utfpr.futfantracker.utils.UtilsAlert;

public class ListarPartidasActivity extends AppCompatActivity {

    private ListView listViewPartidas;
    private List<Partida> listaPartidas;
    private PartidaAdapter partidaAdapter;
    private int posicaoSelecionada = -1;
    private ActionMode actionMode;
    private View viewSelecionada;
    private Drawable backgroundDrawable;
    public static final String ARQUIVO_PREFERENCIAS = "br.edu.utfpr.futfantracker.PREFERENCIAS";
    public static final String KEY_ORDENACAO_CRESCENTE = "ORDENACAO_CRESCENTE";

    // Util porque só um lugar é alterado e todos os lugares que dependem
    // desse valor já são alterados automaticamente
    public static final boolean PADRAO_INICIAL_ORDENACAO_CRESCENTE = true;
    private boolean ordenacaoDataCrescente = PADRAO_INICIAL_ORDENACAO_CRESCENTE;
    private MenuItem menuItemOrdenacao;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.partidas_item_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int idMenuItem = item.getItemId();

            if(idMenuItem == R.id.menuItemEditar){
                editarPartida();
                return true;
            } else if(idMenuItem == R.id.menuItemExcluir){
                excluirPartida();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if(viewSelecionada!=null){
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode = null;
            viewSelecionada = null;
            backgroundDrawable = null;
            listViewPartidas.setEnabled(true);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_partidas);
        setTitle(getString(R.string.partidas));
        listViewPartidas = findViewById(R.id.listViewPartidas);

        // Registra o Contexto do Menu de Contexto (o listview criado pra exibir os objetos)
        // registerForContextMenu(listViewPartidas);

//        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                editarPartida();
//            }
//        });
        lerPreferencias();

        popularListaPartidas();
    }

    private void popularListaPartidas(){
//        String[] partidas_datas = getResources().getStringArray(R.array.partida_datas);
//        String[] partidas_horarios = getResources().getStringArray(R.array.partida_horarios);
//        String[] partidas_adversarios = getResources().getStringArray(R.array.partida_adversarios);
//        int[] partidas_competicoes = getResources().getIntArray(R.array.partida_competicoes);
//        int[] partidas_locais = getResources().getIntArray(R.array.partida_locais);
//        int[] partidas_resultado_casa = getResources().getIntArray(R.array.partida_resultado_casa);
//        int[] partidas_resultado_fora = getResources().getIntArray(R.array.partida_resultado_fora);
//        int[] partidas_ocorreu = getResources().getIntArray(R.array.partida_ocorreu);

//        Partida partida;
//        Local local;
//        boolean partidaOcorreu;

        // "Transforma" os valores do Enum em um "array". "locais" tem os valores e "partidas_locais"
        // vai ter as "chaves" pra esses valores, representados no enum.
//        Local[] locais = Local.values();

        listaPartidas = new ArrayList<>();
//        for(int cont=0; cont<partidas_datas.length; cont++){
//            partidaOcorreu = (partidas_ocorreu[cont]==1 ? true : false);
//            local = locais[partidas_locais[cont]];
//
//            partida = new Partida(
//                            partidas_datas[cont],
//                            partidas_horarios[cont],
//                            partidas_adversarios[cont],
//                            local,
//                            partidas_competicoes[cont],
//                            partidas_resultado_casa[cont],
//                            partidas_resultado_fora[cont],
//                            partidaOcorreu
//            );
//
//            listaPartidas.add(partida);
//        }

// Comentando Trecho com adapter antigo
//        ArrayAdapter<Partida> adapter = new ArrayAdapter<>(this,
//                                                            android.R.layout.simple_list_item_1,
//                                                            listaPartidas);
//        listViewPartidas.setAdapter(adapter);


        // Usando o adapter customizado para exibir os dados formatados como desejado
        partidaAdapter = new PartidaAdapter(this, listaPartidas);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicaoSelecionada = position;
                editarPartida();
            }
        });

        listViewPartidas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(actionMode != null){
                    return false;
                }

                posicaoSelecionada = position;
                viewSelecionada = view;
                backgroundDrawable = view.getBackground();
                view.setBackgroundColor(getColor(R.color.corSelecionado));
                listViewPartidas.setEnabled(false);
                actionMode = startSupportActionMode(actionModeCallback);
                return true;
            }
        });

        listViewPartidas.setAdapter(partidaAdapter);
    }

    public void abrirSobre(){
        // Intent Explícita
        Intent intentSobre = new Intent(this, SobreActivity.class);

        // Activity Destino aberta mas sem passagem de parametros
        startActivity(intentSobre);
    }

    ActivityResultLauncher<Intent> launcherCadastrarPartida = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    // Adicionar checagem sobre o resultado da activity de cadastro
                    if(result.getResultCode() == ListarPartidasActivity.RESULT_OK){

                        // Se o resultado foi ok, trataremos a intent que recebemos
                        Intent intent = result.getData();

                        // Extrairemos os dados dessa intent recebida via Bundle (getExtras)
                        Bundle bundle = intent.getExtras();

                        // Se o bundle não é nulo, extrair os parametros com os tipos adequados
                        if(bundle != null){
                            String data = bundle.getString(CadastrarPartidaActivity.KEY_DATA);
                            String horario = bundle.getString(CadastrarPartidaActivity.KEY_HORARIO);
                            String adversario = bundle.getString(CadastrarPartidaActivity.KEY_ADVERSARIO);
                            String localTexto = bundle.getString(CadastrarPartidaActivity.KEY_LOCAL);
                            int competicao = bundle.getInt(CadastrarPartidaActivity.KEY_COMPETICAO);
                            int resultadoCasa = bundle.getInt(CadastrarPartidaActivity.KEY_RESULTADO_CASA);
                            int resultadoFora = bundle.getInt(CadastrarPartidaActivity.KEY_RESULTADO_FORA);
                            boolean jaOcorreu = bundle.getBoolean(CadastrarPartidaActivity.KEY_PARTIDA_OCORREU);
                            boolean acompanheiPartida = bundle.getBoolean(CadastrarPartidaActivity.KEY_ACOMPANHEI_PARTIDA);

                            // Criar o objeto partida com os parametros passados na outra activity
                            Partida partida = new Partida(data, horario, adversario,
                                    Local.valueOf(localTexto), competicao, resultadoCasa,
                                    resultadoFora, jaOcorreu, acompanheiPartida);

                            // Adicionar o objeto partida no ArrayList de Partidas
                            listaPartidas.add(partida);

                            ordenarLista();
                        }
                    }
                }
            }
    );

    public void abrirCadastroPartida(){
        Intent intentAbertura = new Intent(this, CadastrarPartidaActivity.class);

        // Passando o modo na abertura da activity de cadastro
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_MODO, CadastrarPartidaActivity.MODO_NOVO);

        // Chamar a abertura da outra activity via Intent criada (com o destino delimitado)
        launcherCadastrarPartida.launch(intentAbertura);

        // O Launcher vai amarrar o meu contrato de envio/recepção de extras via bundle/intent.
    }

    // Novo metodo para o menu de contexto
    private void excluirPartida(){
        Partida partida = listaPartidas.get(posicaoSelecionada);
        String mensagem = getString(R.string.do_you_want_to_delete_the_match_with)+ partida.getAdversario() +"\" ?";
        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaPartidas.remove(posicaoSelecionada);
                partidaAdapter.notifyDataSetChanged();
                actionMode.finish();
            }
        };
        UtilsAlert.confirmarAcao(this, mensagem, listenerSim, null);

    }

    ActivityResultLauncher<Intent> launcherEditarPartida = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    // Adicionar checagem sobre o resultado da activity de cadastro
                    if(result.getResultCode() == ListarPartidasActivity.RESULT_OK){

                        // Se o resultado foi ok, trataremos a intent que recebemos
                        Intent intent = result.getData();

                        // Extrairemos os dados dessa intent recebida via Bundle (getExtras)
                        Bundle bundle = intent.getExtras();

                        // Se o bundle não é nulo, extrair os parametros com os tipos adequados
                        if(bundle != null){
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

                            final Partida partida = listaPartidas.get(posicaoSelecionada);
                            final Partida clonePartida;
                            try {
                                clonePartida = (Partida) partida.clone();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                                UtilsAlert.mostrarAviso(ListarPartidasActivity.this,
                                                        R.string.type_conversion_error);
                                return;
                            }

                            partida.setData(data);
                            partida.setHorario(horario);
                            partida.setAdversario(adversario);
                            partida.setLocal(local);
                            partida.setCompeticao(competicao);
                            partida.setResultadoCasa(resultadoCasa);
                            partida.setResultadoFora(resultadoFora);
                            partida.setJaOcorreu(jaOcorreu);
                            partida.setAcompanheiPartida(acompanheiPartida);

                            ordenarLista();

                            final ConstraintLayout constraintLayout = findViewById(R.id.main);
                            Snackbar snackbar = Snackbar.make(constraintLayout,
                                                              R.string.data_update_done,
                                                              Snackbar.LENGTH_LONG);
                            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    listaPartidas.remove(partida);
                                    listaPartidas.add(clonePartida);
                                    ordenarLista();
                                }
                            });

                            snackbar.show();
                        }
                    }

                    posicaoSelecionada = -1;

                    if(actionMode != null){
                        actionMode.finish();
                    }
                }
            }
    );

    private void editarPartida(){
        // Um objeto para receber o objeto selecionado da lista
        Partida partida = listaPartidas.get(posicaoSelecionada);

        // intent de abertura da activity de cadastro
        Intent intentAbertura = new Intent(this, CadastrarPartidaActivity.class);

        // Passando o modo e os parametros do objeto selecionado na abertura da activity de cadastro
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_MODO, CadastrarPartidaActivity.MODO_EDITAR); // Const Int
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_DATA, partida.getData()); // String
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_HORARIO, partida.getHorario()); // String
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_ADVERSARIO, partida.getAdversario()); // String
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_LOCAL, partida.getLocal().toString()); // Local
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_COMPETICAO, partida.getCompeticao()); // int
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_PARTIDA_OCORREU, partida.isJaOcorreu()); // boolean
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_ACOMPANHEI_PARTIDA, partida.isAcompanheiPartida()); // boolean
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_RESULTADO_CASA, partida.getResultadoCasa()); // int
        intentAbertura.putExtra(CadastrarPartidaActivity.KEY_RESULTADO_FORA, partida.getResultadoFora()); // int

        // Chamar a abertura da outra activity via Intent criada (com o destino delimitado)
        launcherEditarPartida.launch(intentAbertura);
    }

    // Chamado 1x, quando o menu é criado
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listar_partidas_menu, menu);

        // Recuperar o ID do menu selecionado pra salvar num atributo
        menuItemOrdenacao = menu.findItem(R.id.menuItemOrdenacao);
        return true;
    }

    // Antes do menu ser exibido, eu preciso garantir a exibição do icone correto
    // referente a ordenação escolhida e salva no sharedpreferences, pra evitar de
    // mostrar o icone numa ordenacao e a ordenacao salva/aplicada ser a contraria
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        atualizarIconeDeOrdenacao();
        return true;
    }

    // Criando o menu do contexto (item selecionado)
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        // Uso um inflater e inflo o menu indicando o layout dele
//        getMenuInflater().inflate(R.menu.partidas_item_selecionado, menu);
//    }

    // Tratamento de evento para o menu de opções (barra superior)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();
        if(idMenuItem == R.id.menuItemAdicionar){
            abrirCadastroPartida();
            return true;
        } else if (idMenuItem == R.id.menuItemSobre){
            abrirSobre();
            return true;
        } else if (idMenuItem == R.id.menuItemOrdenacao){
            salvarPreferenciaDeOrdenacaoCrescente(!ordenacaoDataCrescente);
            atualizarIconeDeOrdenacao();
            // Pra que a opção selecionada ja tenha sua ordenação aplicada imediatamente
            // e não só fique salva na preferencia pra ser aplicada posteriormente
            ordenarLista();
            return true;
        } else if (idMenuItem == R.id.menuItemRestaurar){
            confirmarRestaurarPadroes();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Tratamento de evento para o menu de contexto (item selecionado)
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        int idMenuItem = item.getItemId();
//
//        // como foi usado ListView, isso resolve. Se fosse RecyclerView, precisava de outra
//        // Abordagem pra ter conhecimento da posição selecionada pelo usuário
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//        // Checagem do menu selecionado e de qual metodo é disparado
//        if(idMenuItem == R.id.menuItemEditar){
//            editarPartida(info.position);
//            return true;
//        } else if(idMenuItem == R.id.menuItemExcluir){
//            excluirPessoa(info.position);
//            return true;
//        } else {
//            return super.onContextItemSelected(item);
//        }
//    }

    private void ordenarLista(){
        if(ordenacaoDataCrescente){
            Collections.sort(listaPartidas, Partida.ordenacaoPorData);
        } else {
            Collections.sort(listaPartidas, Partida.ordenacaoPorDataDecrescente);
        }
        partidaAdapter.notifyDataSetChanged();
    }

    private void atualizarIconeDeOrdenacao(){
        if(ordenacaoDataCrescente){
            menuItemOrdenacao.setIcon(R.drawable.ic_action_ascending_sort);
        } else {
            menuItemOrdenacao.setIcon(R.drawable.ic_action_descending_sort);
        }
    }

    private void lerPreferencias(){
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        shared.getBoolean(KEY_ORDENACAO_CRESCENTE, ordenacaoDataCrescente);
    }

    private void salvarPreferenciaDeOrdenacaoCrescente(boolean novoValor){
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_ORDENACAO_CRESCENTE, novoValor);
        editor.commit();
        ordenacaoDataCrescente = novoValor;
    }

    private void confirmarRestaurarPadroes(){
        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                restaurarPreferencias();
                atualizarIconeDeOrdenacao();
                ordenarLista();
                Toast.makeText(ListarPartidasActivity.this,
                        R.string.as_configuracoes_foram_restauradas_ao_padrao,
                        Toast.LENGTH_LONG).show();
            }
        };

        UtilsAlert.confirmarAcao(this, R.string.do_you_want_to_restore_default_settings, listenerSim, null);
    }

    private void restaurarPreferencias(){
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
        ordenacaoDataCrescente = PADRAO_INICIAL_ORDENACAO_CRESCENTE;
    }
}