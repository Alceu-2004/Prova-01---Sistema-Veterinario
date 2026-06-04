package model;

import observer.Observador;
import observer.EventoAtendimento;

public class Tutor implements Observador {

    private final String nome;

    public Tutor(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void atualizar(EventoAtendimento evento, String mensagem) {
        if (evento == EventoAtendimento.INICIADO) {
            System.out.println("[Tutor " + nome + "] Aviso recebido: " + mensagem);
        }
    }

    @Override
    public String toString() {
        return "Tutor{" + nome + "}";
    }
}
