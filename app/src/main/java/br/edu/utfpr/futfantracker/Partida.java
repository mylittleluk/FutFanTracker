package br.edu.utfpr.futfantracker;

public class Partida {

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
