package decorator;

public class Consulta implements ServicoVeterinario {

    private static final double VALOR_BASE = 100.0;

    @Override
    public double getValor() {
        return VALOR_BASE;
    }

    @Override
    public String getDescricao() {
        return "Consulta Veterinária";
    }
}
