package state;

import model.Atendimento;
import observer.EventoAtendimento;

public class EmAtendimento implements EstadoAtendimento {

    @Override
    public void iniciar(Atendimento atendimento) {
        throw new IllegalStateException("Atendimento já está em andamento.");
    }

    @Override
    public void finalizar(Atendimento atendimento) {
        atendimento.setEstado(new Finalizado());
        atendimento.notificar(EventoAtendimento.FINALIZADO, "Atendimento de " + atendimento.getAnimal() + " foi finalizado.");
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        throw new IllegalStateException("Atendimento em andamento não pode ser cancelado.");
    }

    @Override
    public String getNome() {
        return "EmAtendimento";
    }
}
