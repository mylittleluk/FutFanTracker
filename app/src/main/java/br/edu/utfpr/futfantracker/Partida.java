package br.edu.utfpr.futfantracker;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Partida implements Cloneable{

    public static Comparator<Partida> ordenacaoPorData = new Comparator<Partida>() {
        @Override
        public int compare(Partida p1, Partida p2) {
            int result = 0;
            SimpleDateFormat padraoEntradaData = new SimpleDateFormat("dd/MM/yyyy");
            try {
                // String source -> Date Target
                Date datap1 = padraoEntradaData.parse(p1.getData());
                Date datap2 = padraoEntradaData.parse(p2.getData());
                result = datap1.compareTo(datap2);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return result;
        }
    };

    public static Comparator<Partida> ordenacaoPorDataDecrescente = new Comparator<Partida>() {
        @Override
        public int compare(Partida p1, Partida p2) {
            int result = 0;
            SimpleDateFormat padraoEntradaData = new SimpleDateFormat("dd/MM/yyyy");
            try {
                // String source -> Date Target
                Date datap1 = padraoEntradaData.parse(p1.getData());
                Date datap2 = padraoEntradaData.parse(p2.getData());
                result = -1 * (datap1.compareTo(datap2));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return result;
        }
    };

    private String data, horario, adversario;
    private Local local;
    private int competicao, resultadoCasa, resultadoFora;
    private boolean jaOcorreu, acompanheiPartida;

    public Partida(String data, String horario, String adversario, Local local, int competicao, int resultadoCasa, int resultadoFora, boolean jaOcorreu, boolean acompanheiPartida) {
        this.data = data;
        this.horario = horario;
        this.adversario = adversario;
        this.local = local;
        this.competicao = competicao;
        this.resultadoCasa = resultadoCasa;
        this.resultadoFora = resultadoFora;
        this.jaOcorreu = jaOcorreu;
        this.acompanheiPartida = acompanheiPartida;
    }

    public boolean isJaOcorreu() {
        return jaOcorreu;
    }

    public void setJaOcorreu(boolean jaOcorreu) {
        this.jaOcorreu = jaOcorreu;
    }

    public int getResultadoFora() {
        return resultadoFora;
    }

    public void setResultadoFora(int resultadoFora) {
        this.resultadoFora = resultadoFora;
    }

    public int getResultadoCasa() {
        return resultadoCasa;
    }

    public void setResultadoCasa(int resultadoCasa) {
        this.resultadoCasa = resultadoCasa;
    }

    public int getCompeticao() {
        return competicao;
    }

    public void setCompeticao(int competicao) {
        this.competicao = competicao;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getAdversario() {
        return adversario;
    }

    public void setAdversario(String adversario) {
        this.adversario = adversario;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isAcompanheiPartida() {
        return acompanheiPartida;
    }

    public void setAcompanheiPartida(boolean acompanheiPartida) {
        this.acompanheiPartida = acompanheiPartida;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Clone raso pq a classe só tem atr primitivos ou imutáveis
        return super.clone();
    }

    @Override
    public String toString() {
        return  data + "\n" +
                horario + "\n" +
                adversario + "\n" +
                competicao + "\n" +
                local + "\n" +
                jaOcorreu + "\n" +
                acompanheiPartida + "\n" +
                resultadoCasa + "\n" +
                resultadoFora;
    }
}
