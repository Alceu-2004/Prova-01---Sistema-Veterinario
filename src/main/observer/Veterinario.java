package observer;

public class Veterinario implements Observador {

    private final String nome;

    public Veterinario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void atualizar(EventoAtendimento evento, String mensagem) {
        if (evento == EventoAtendimento.CANCELADO) {
            System.out.println("[Veterinário " + nome + "] Aviso recebido: " + mensagem);
        }
    }

    @Override
    public String toString() {
        return "Veterinario{" + nome + "}";
    }
}
