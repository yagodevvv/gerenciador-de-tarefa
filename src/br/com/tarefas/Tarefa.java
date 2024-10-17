package br.com.tarefas;

import java.time.LocalDate;

public class Tarefa {
    private String descricao;
    private String categoria;
    private boolean concluida;
    private LocalDate prazo;
    private String prioridade;

    public Tarefa(String descricao, String categoria, LocalDate prazo, String prioridade) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.concluida = false;
        this.prazo = prazo;
        this.prioridade = prioridade;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
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

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    @Override
    public String toString() {
        return descricao + " (" + categoria + ") [" + prioridade + "]" + 
               (concluida ? " - Concluída" : "") + " | Prazo: " + prazo;
    }
}
