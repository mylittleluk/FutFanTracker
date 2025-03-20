package br.edu.utfpr.futfantracker;

import android.content.Intent;
import android.os.Bundle;
//import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarPartidasActivity extends AppCompatActivity {

    private ListView listViewPartidas;
    private List<Partida> listaPartidas;
    private PartidaAdapter partidaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_partidas);
        setTitle(getString(R.string.partidas));

        listViewPartidas = findViewById(R.id.listViewPartidas);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Partida partida = (Partida) listViewPartidas.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                               getString(R.string.a_partida_com_o)+partida.getAdversario()+getString(R.string.foi_selecionada),
                               Toast.LENGTH_LONG).show();
            }
        });

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
        listViewPartidas.setAdapter(partidaAdapter);
    }

    public void abrirSobre(View view){
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

                            // Criar o objeto partida com os parametros passados na outra activity
                            Partida partida = new Partida(data, horario, adversario,
                                    Local.valueOf(localTexto), competicao, resultadoCasa,
                                    resultadoFora, jaOcorreu);

                            // Adicionar o objeto partida no ArrayList de Partidas
                            listaPartidas.add(partida);

                            // Notificar o adapter customizado que os dados foram atualizados
                            // pra que possa ser atualizado
                            partidaAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );

    public void abrirCadastroPartida(View view){
        Intent intentAbertura = new Intent(this, CadastrarPartidaActivity.class);

        // Chamar a abertura da outra activity via Intent criada (com o destino delimitado)
        launcherCadastrarPartida.launch(intentAbertura);

        // O Launcher vai amarrar o meu contrato de envio/recepção de extras via bundle/intent.
    }
}