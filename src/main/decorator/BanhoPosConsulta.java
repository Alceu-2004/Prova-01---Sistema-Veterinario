package decorator;

public class BanhoPosConsulta extends DecoradorServico {

    private static final double VALOR_BANHO = 30.0;

    public BanhoPosConsulta(ServicoVeterinario servico) {
        super(servico);
    }

    @Override
    public double getValor() {
        return servico.getValor() + VALOR_BANHO;
    }

    @Override
    public String getDescricao() {
        return servico.getDescricao() + " + banho pós-consulta";
    }
}
