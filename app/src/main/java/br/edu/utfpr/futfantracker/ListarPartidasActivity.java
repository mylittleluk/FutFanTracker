package br.edu.utfpr.futfantracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarPartidasActivity extends AppCompatActivity {

    private ListView listViewPartidas;
    private List<Partida> listaPartidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_partidas);

        listViewPartidas = findViewById(R.id.listViewPartidas);

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

        ArrayAdapter<Partida> adapter = new ArrayAdapter<>(this,
                                                            android.R.layout.simple_list_item_1,
                                                            listaPartidas);
        listViewPartidas.setAdapter(adapter);
    }
}