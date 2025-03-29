package br.edu.utfpr.futfantracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.futfantracker.modelo.Partida;

public class PartidaAdapter extends BaseAdapter {
    private Context context;
    private List<Partida> listaPartidas;
    private String[] competicoes;

    // Holder interno
    private static class PartidaHolder{
        // listar todos os textView que serão atualizados e receberão valores
        // baseado no layout criado (linha...)
        public TextView textViewValorData;
        public TextView textViewValorHorario;
        public TextView textViewValorAdversario;
        public TextView textViewValorCompeticao;
        public TextView textViewValorLocal;
        public TextView textViewValorJaOcorreu;
        public TextView textViewAcompanheiPartida;
        public TextView textViewValorResultadoCasa;
        public TextView textViewValorResultadoFora;
    }

    public PartidaAdapter(Context context, List<Partida> listaPartidas) {
        this.context = context;
        this.listaPartidas = listaPartidas;
        competicoes = context.getResources().getStringArray(R.array.competicoes);
    }

    @Override
    public int getCount() {
        return listaPartidas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPartidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PartidaHolder holder;

        // Se a view for nula, não tiver sido criada:
        if(convertView == null){

            // Convoca o inflater pra criação com base no arquivo de layout criado pro adapter
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // A View usa o inflater pra "inflar", de acordo com o layout criado, associado ao ViewGrou parent
            convertView = inflater.inflate(R.layout.linha_lista_partidas, parent, false);

            holder = new PartidaHolder();

            // Agora usando o holder como "forma" pra reuso mapeando os elementos
            holder.textViewValorData = convertView.findViewById(R.id.textViewValorData);
            holder.textViewValorHorario = convertView.findViewById(R.id.textViewValorHorario);
            holder.textViewValorAdversario = convertView.findViewById(R.id.textViewValorAdversario);
            holder.textViewValorCompeticao = convertView.findViewById(R.id.textViewValorCompeticao);
            holder.textViewValorLocal = convertView.findViewById(R.id.textViewValorLocal);
            holder.textViewValorJaOcorreu = convertView.findViewById(R.id.textViewValorJaOcorreu);
            holder.textViewAcompanheiPartida = convertView.findViewById(R.id.textViewAcompanheiPartida);
            holder.textViewValorResultadoCasa = convertView.findViewById(R.id.textViewValorResultadoCasa);
            holder.textViewValorResultadoFora = convertView.findViewById(R.id.textViewValorResultadoFora);

            // Com os valores mapeados, agora é associar o holder a View convertView
            convertView.setTag(holder);

        // Caso contrário, o holder vai apenas atualizar por meio dos valores na view
        } else { // Caso contrário
            holder = (PartidaHolder) convertView.getTag();
        }

        Partida partida = listaPartidas.get(position);
        holder.textViewValorData.setText(partida.getData());
        holder.textViewValorHorario.setText(partida.getHorario());
        holder.textViewValorAdversario.setText(partida.getAdversario());

        // Trazendo os valores do array competicoes para a classe pra facilitar a exibição
        // dos valores na view
        holder.textViewValorCompeticao.setText(competicoes[partida.getCompeticao()]);

        // Tratamento valores do Enum priorizando a flexbilidade do strings.xml
        switch(partida.getLocal()){
            case Casa:
                holder.textViewValorLocal.setText(R.string.casa);
                break;
            case Fora:
                holder.textViewValorLocal.setText(R.string.fora);
        }

        // Tratamento Booleano Já ocorreu
        if(partida.isJaOcorreu()){
            holder.textViewValorJaOcorreu.setText(R.string.partida_ja_ocorreu);
        } else {
            holder.textViewValorJaOcorreu.setText(R.string.partida_ainda_nao_ocorreu);
        }

        // Tratamento Booleano Acompanhei Partida
        if(partida.isAcompanheiPartida()){
            holder.textViewAcompanheiPartida.setText(R.string.acompanhei_a_partida);
        } else {
            holder.textViewAcompanheiPartida.setText(R.string.nao_acompanhei_a_partida);
        }

        holder.textViewValorResultadoCasa.setText(String.valueOf(partida.getResultadoCasa()));
        holder.textViewValorResultadoFora.setText(String.valueOf(partida.getResultadoFora()));

        return convertView;
    }
}
