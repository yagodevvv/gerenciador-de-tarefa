package br.com.tarefas;

public class Tarefa {
    private String descricao;
    private String categoria;
    private boolean concluida;

    public Tarefa(String descricao, String categoria) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.concluida = false;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    @Override
    public String toString() {
        return descricao + " [" + categoria + "] " + (concluida ? "(Concluída)" : "(Pendente)");
    }
}
