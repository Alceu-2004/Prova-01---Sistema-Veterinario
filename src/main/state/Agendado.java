package state;

import model.Atendimento;
import observer.EventoAtendimento;

public class Agendado implements EstadoAtendimento {

    @Override
    public void iniciar(Atendimento atendimento) {
        atendimento.setEstado(new EmAtendimento());
        atendimento.notificar(EventoAtendimento.INICIADO, "Atendimento iniciado para " + atendimento.getAnimal());
    }

    @Override
    public void finalizar(Atendimento atendimento) {
        throw new IllegalStateException("Atendimento agendado não pode ser finalizado diretamente.");
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        atendimento.setEstado(new Cancelado());
        atendimento.notificar(EventoAtendimento.CANCELADO, "Atendimento de " + atendimento.getAnimal() + " foi cancelado.");
    }

    @Override
    public String getNome() {
        return "Agendado";
    }
}
