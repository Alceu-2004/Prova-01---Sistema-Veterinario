package observer;

public class Recepcao implements Observador {

    @Override
    public void atualizar(EventoAtendimento evento, String mensagem) {
        if (evento == EventoAtendimento.FINALIZADO) {
            System.out.println("[Recepção] Aviso recebido: " + mensagem);
        }
    }
}
