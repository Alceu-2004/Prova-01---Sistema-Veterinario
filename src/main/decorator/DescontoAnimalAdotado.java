package decorator;

import model.Animal;

public class DescontoAnimalAdotado extends DecoradorServico {

    private static final double VALOR_DESCONTO = 20.0;

    private final Animal animal;

    public DescontoAnimalAdotado(ServicoVeterinario servico, Animal animal) {
        super(servico);
        this.animal = animal;
    }

    @Override
    public double getValor() {
        if (animal.isAdotado()) {
            return servico.getValor() - VALOR_DESCONTO;
        }
        return servico.getValor();
    }

    @Override
    public String getDescricao() {
        if (animal.isAdotado()) {
            return servico.getDescricao() + " + desconto adoção";
        }
        return servico.getDescricao();
    }
}
