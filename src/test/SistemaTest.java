import decorator.*;
import model.*;
import observer.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SistemaTest {

    @Test
    @DisplayName("Deve permitir transição: Agendado → EmAtendimento → Finalizado")
    void testeMudancaValidaAgendadoParaFinalizado() {
        Tutor tutor = new Tutor("Carlos");
        Animal animal = new Animal("Rex", "Cachorro", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());

        assertEquals("Agendado", atendimento.getEstadoAtual());

        atendimento.iniciar();
        assertEquals("EmAtendimento", atendimento.getEstadoAtual());

        atendimento.finalizar();
        assertEquals("Finalizado", atendimento.getEstadoAtual());
    }

    @Test
    @DisplayName("Deve permitir transição: Agendado → Cancelado")
    void testeMudancaValidaAgendadoParaCancelado() {
        Tutor tutor = new Tutor("Marina");
        Animal animal = new Animal("Luna", "Gato", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());

        assertEquals("Agendado", atendimento.getEstadoAtual());

        atendimento.cancelar();
        assertEquals("Cancelado", atendimento.getEstadoAtual());
    }

    @Test
    @DisplayName("Não deve permitir cancelar atendimento já Finalizado")
    void testeMudancaInvalidaCancelarFinalizado() {
        Tutor tutor = new Tutor("Ana");
        Animal animal = new Animal("Thor", "Cachorro", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());

        atendimento.iniciar();
        atendimento.finalizar();

        IllegalStateException excecao = assertThrows(
                IllegalStateException.class,
                atendimento::cancelar
        );

        assertTrue(excecao.getMessage().contains("finalizado"));
    }

    @Test
    @DisplayName("Não deve permitir finalizar atendimento Agendado diretamente")
    void testeMudancaInvalidaFinalizarAgendado() {
        Tutor tutor = new Tutor("Pedro");
        Animal animal = new Animal("Bolt", "Cachorro", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());

        assertThrows(IllegalStateException.class, atendimento::finalizar);
    }

    @Test
    @DisplayName("Não deve permitir iniciar atendimento Cancelado")
    void testeMudancaInvalidaIniciarCancelado() {
        Tutor tutor = new Tutor("Joana");
        Animal animal = new Animal("Mia", "Gato", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());

        atendimento.cancelar();

        assertThrows(IllegalStateException.class, atendimento::iniciar);
    }

    @Test
    @DisplayName("Tutor deve ser avisado apenas quando atendimento for INICIADO")
    void testeAvisoTutorNoInicio() {
        List<String> avisosRecebidos = new ArrayList<>();

        Observador tutorEspia = (evento, mensagem) -> {
            if (evento == EventoAtendimento.INICIADO) {
                avisosRecebidos.add("INICIADO:" + mensagem);
            }
        };

        Tutor tutor = new Tutor("Fernanda");
        Animal animal = new Animal("Bob", "Cachorro", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());
        atendimento.adicionarObservador(tutorEspia);

        atendimento.iniciar();

        assertEquals(1, avisosRecebidos.size());
        assertTrue(avisosRecebidos.get(0).startsWith("INICIADO:"));
    }

    @Test
    @DisplayName("Veterinário deve ser avisado apenas quando atendimento for CANCELADO")
    void testeAvisoVeterinarioNoCancelamento() {
        List<EventoAtendimento> eventosRecebidos = new ArrayList<>();

        Observador vetEspia = (evento, mensagem) -> eventosRecebidos.add(evento);

        Tutor tutor = new Tutor("Lucas");
        Animal animal = new Animal("Pipoca", "Coelho", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());
        atendimento.adicionarObservador(vetEspia);

        atendimento.cancelar();

        assertEquals(1, eventosRecebidos.size());
        assertEquals(EventoAtendimento.CANCELADO, eventosRecebidos.get(0));
    }

    @Test
    @DisplayName("Recepção deve ser avisada apenas quando atendimento for FINALIZADO")
    void testeAvisoRecepcaoNaFinalizacao() {
        List<EventoAtendimento> eventosRecebidos = new ArrayList<>();

        Observador recepcaoEspia = (evento, mensagem) -> eventosRecebidos.add(evento);

        Tutor tutor = new Tutor("Camila");
        Animal animal = new Animal("Nemo", "Peixe", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());
        atendimento.adicionarObservador(recepcaoEspia);

        atendimento.iniciar();
        atendimento.finalizar();

        assertTrue(eventosRecebidos.contains(EventoAtendimento.FINALIZADO));
    }

    @Test
    @DisplayName("Observadores do tipo correto devem receber apenas seus eventos")
    void testeFiltragemPorTipoDeObservador() {
        List<String> avisosVet = new ArrayList<>();
        List<String> avisosRecepcao = new ArrayList<>();

        Observador vet = (evento, msg) -> {
            if (evento == EventoAtendimento.CANCELADO) avisosVet.add(msg);
        };
        Observador recepcao = (evento, msg) -> {
            if (evento == EventoAtendimento.FINALIZADO) avisosRecepcao.add(msg);
        };

        Tutor tutor = new Tutor("Ricardo");
        Animal animal = new Animal("Simba", "Gato", false);
        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());
        atendimento.adicionarObservador(vet);
        atendimento.adicionarObservador(recepcao);

        atendimento.iniciar();
        atendimento.finalizar();

        assertTrue(avisosVet.isEmpty());
        assertEquals(1, avisosRecepcao.size());
    }

    @Test
    @DisplayName("Deve calcular valor com desconto adoção + taxa domiciliar + banho")
    void testeValorComTresDecoratorsAnimalAdotado() {
        Animal animal = new Animal("Thor", "Cachorro", true);
        Tutor tutor = new Tutor("Ana");

        ServicoVeterinario servico = new Consulta();
        servico = new DescontoAnimalAdotado(servico, animal);
        servico = new TaxaDomiciliar(servico);
        servico = new BanhoPosConsulta(servico);

        Atendimento atendimento = new Atendimento(tutor, animal, servico);

        assertEquals(160.0, atendimento.getValorFinal(), 0.001);
    }

    @Test
    @DisplayName("Desconto de adoção NÃO deve ser aplicado quando animal não é adotado")
    void testeDescontoNaoAplicadoParaAnimalNaoAdotado() {
        Animal animal = new Animal("Mel", "Cachorro", false);
        Tutor tutor = new Tutor("Bia");

        ServicoVeterinario servico = new Consulta();
        servico = new DescontoAnimalAdotado(servico, animal);
        servico = new BanhoPosConsulta(servico);

        Atendimento atendimento = new Atendimento(tutor, animal, servico);

        assertEquals(130.0, atendimento.getValorFinal(), 0.001);
    }

    @Test
    @DisplayName("Deve calcular valor base sem nenhum decorador")
    void testeValorBaseConsultaSimples() {
        Animal animal = new Animal("Farofa", "Gato", false);
        Tutor tutor = new Tutor("Henrique");

        Atendimento atendimento = new Atendimento(tutor, animal, new Consulta());

        assertEquals(100.0, atendimento.getValorFinal(), 0.001);
    }

    @Test
    @DisplayName("Deve calcular valor com apenas taxa domiciliar")
    void testeValorComTaxaDomiciliarApenas() {
        Animal animal = new Animal("Toto", "Cachorro", false);
        Tutor tutor = new Tutor("Silvia");

        ServicoVeterinario servico = new TaxaDomiciliar(new Consulta());
        Atendimento atendimento = new Atendimento(tutor, animal, servico);

        assertEquals(150.0, atendimento.getValorFinal(), 0.001);
    }
}
