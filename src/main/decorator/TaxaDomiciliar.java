package decorator;

public class TaxaDomiciliar extends DecoradorServico {

    private static final double VALOR_TAXA = 50.0;

    public TaxaDomiciliar(ServicoVeterinario servico) {
        super(servico);
    }

    @Override
    public double getValor() {
        return servico.getValor() + VALOR_TAXA;
    }

    @Override
    public String getDescricao() {
        return servico.getDescricao() + " + atendimento domiciliar";
    }
}
