package model;

import decorator.ServicoVeterinario;
import observer.EventoAtendimento;
import observer.Observador;
import state.Agendado;
import state.EstadoAtendimento;

import java.util.ArrayList;
import java.util.List;

public class Atendimento {

    private final Tutor tutor;
    private final Animal animal;
    private final ServicoVeterinario servico;

    private EstadoAtendimento estado;

    private final List<Observador> observadores = new ArrayList<>();

    public Atendimento(Tutor tutor, Animal animal, ServicoVeterinario servico) {
        this.tutor = tutor;
        this.animal = animal;
        this.servico = servico;
        this.estado = new Agendado();
    }

    public void adicionarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void removerObservador(Observador observador) {
        observadores.remove(observador);
    }

    public void notificar(EventoAtendimento evento, String mensagem) {
        for (Observador observador : observadores) {
            observador.atualizar(evento, mensagem);
        }
    }

    public void iniciar() {
        estado.iniciar(this);
    }

    public void finalizar() {
        estado.finalizar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void setEstado(EstadoAtendimento estado) {
        this.estado = estado;
    }

    public double getValorFinal() {
        return servico.getValor();
    }

    public String getDescricaoServico() {
        return servico.getDescricao();
    }

    public String getEstadoAtual() {
        return estado.getNome();
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Animal getAnimal() {
        return animal;
    }

    @Override
    public String toString() {
        return "Atendimento{tutor=" + tutor.getNome()
                + ", animal=" + animal.getNome()
                + ", estado=" + estado.getNome()
                + ", valor=R$" + getValorFinal() + "}";
    }
}
