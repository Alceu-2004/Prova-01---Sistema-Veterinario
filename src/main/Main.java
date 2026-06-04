import decorator.*;
import model.*;
import observer.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("   SISTEMA CLÍNICA VETERINÁRIA");
        System.out.println("======================================\n");

        Tutor tutor = new Tutor("Carlos");
        Animal animal = new Animal("Rex", "Cachorro", true);

        System.out.println("Tutor: " + tutor);
        System.out.println("Animal: " + animal + "\n");

        ServicoVeterinario servico = new Consulta();
        servico = new DescontoAnimalAdotado(servico, animal);
        servico = new TaxaDomiciliar(servico);
        servico = new BanhoPosConsulta(servico);

        System.out.println("Serviços: " + servico.getDescricao());
        System.out.println("Valor total: R$" + servico.getValor() + "\n");

        Atendimento atendimento = new Atendimento(tutor, animal, servico);
        atendimento.adicionarObservador(tutor);
        atendimento.adicionarObservador(new Veterinario("Dr. João"));
        atendimento.adicionarObservador(new Recepcao());

        System.out.println("Estado inicial: " + atendimento.getEstadoAtual() + "\n");

        System.out.println("--- Iniciando atendimento ---");
        atendimento.iniciar();
        System.out.println("Estado: " + atendimento.getEstadoAtual() + "\n");

        System.out.println("--- Finalizando atendimento ---");
        atendimento.finalizar();
        System.out.println("Estado: " + atendimento.getEstadoAtual() + "\n");

        System.out.println("--- Tentativa de cancelamento (inválida) ---");
        try {
            atendimento.cancelar();
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        System.out.println("\nValor final: R$" + atendimento.getValorFinal());
        System.out.println("\n======================================");
        System.out.println("            FIM DA EXECUÇÃO");
        System.out.println("======================================");
    }
}
