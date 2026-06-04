package state;

import model.Atendimento;

public class Finalizado implements EstadoAtendimento {

    @Override
    public void iniciar(Atendimento atendimento) {
        throw new IllegalStateException("Atendimento já finalizado.");
    }

    @Override
    public void finalizar(Atendimento atendimento) {
        throw new IllegalStateException("Atendimento já finalizado.");
    }

    @Override
    public void cancelar(Atendimento atendimento) {
        throw new IllegalStateException("Atendimento finalizado não pode ser cancelado.");
    }

    @Override
    public String getNome() {
        return "Finalizado";
    }
}
