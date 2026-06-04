package state;

import model.Atendimento;

public interface EstadoAtendimento {

    void iniciar(Atendimento atendimento);

    void finalizar(Atendimento atendimento);

    void cancelar(Atendimento atendimento);

    String getNome();
}
