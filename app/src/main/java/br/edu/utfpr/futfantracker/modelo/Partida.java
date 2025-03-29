package br.edu.utfpr.futfantracker.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

@Entity
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

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String data;
    private String horario;
    private String adversario;
    @NonNull
    @ColumnInfo(index = true)
    private Local local;
    private int competicao;
    private int resultadoCasa;
    private int resultadoFora;
    private boolean jaOcorreu;
    private boolean acompanheiPartida;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public Object clone() throws CloneNotSupportedException {
        // Clone raso pq a classe só tem atr primitivos ou imutáveis
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Partida partida = (Partida) o;

        return competicao == partida.competicao &&
                resultadoCasa == partida.resultadoCasa &&
                resultadoFora == partida.resultadoFora &&
                jaOcorreu == partida.jaOcorreu &&
                acompanheiPartida == partida.acompanheiPartida &&
                data.equals(partida.data) &&
                horario.equals(partida.horario) &&
                adversario.equals(partida.adversario) &&
                local == partida.local;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, horario, adversario, local, competicao, resultadoCasa, resultadoFora, jaOcorreu, acompanheiPartida);
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
