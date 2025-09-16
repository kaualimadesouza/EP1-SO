import java.util.HashMap;
import java.util.Map;

public class TabelaProcessos {
    private final Map<String, BCP> tabela = new HashMap<>();

    public TabelaProcessos() {}

    public boolean isEmpty() {
        return tabela.isEmpty();
    }

    public Boolean existe(BCP processo) {
        return tabela.containsKey(processo.getNome());
    }

    public void adicionar(BCP processo) {
        tabela.put(processo.getNome(), processo);
    }

    public void remover(BCP processo) {
        tabela.remove(processo.getNome());
    }
}
