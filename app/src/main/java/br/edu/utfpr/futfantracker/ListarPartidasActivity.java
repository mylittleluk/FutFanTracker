package br.edu.utfpr.futfantracker;

import android.content.Intent;
import android.os.Bundle;
//import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        String[] partidas_datas = getResources().getStringArray(R.array.partida_datas);
        String[] partidas_horarios = getResources().getStringArray(R.array.partida_horarios);
        String[] partidas_adversarios = getResources().getStringArray(R.array.partida_adversarios);
        int[] partidas_competicoes = getResources().getIntArray(R.array.partida_competicoes);
        int[] partidas_locais = getResources().getIntArray(R.array.partida_locais);
        int[] partidas_resultado_casa = getResources().getIntArray(R.array.partida_resultado_casa);
        int[] partidas_resultado_fora = getResources().getIntArray(R.array.partida_resultado_fora);
        int[] partidas_ocorreu = getResources().getIntArray(R.array.partida_ocorreu);

        Partida partida;
        Local local;
        boolean partidaOcorreu;

        // "Transforma" os valores do Enum em um "array". "locais" tem os valores e "partidas_locais"
        // vai ter as "chaves" pra esses valores, representados no enum.
        Local[] locais = Local.values();

        listaPartidas = new ArrayList<>();
        for(int cont=0; cont<partidas_datas.length; cont++){
            partidaOcorreu = (partidas_ocorreu[cont]==1 ? true : false);
            local = locais[partidas_locais[cont]];

            partida = new Partida(
                            partidas_datas[cont],
                            partidas_horarios[cont],
                            partidas_adversarios[cont],
                            local,
                            partidas_competicoes[cont],
                            partidas_resultado_casa[cont],
                            partidas_resultado_fora[cont],
                            partidaOcorreu
            );

            listaPartidas.add(partida);
        }

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
}