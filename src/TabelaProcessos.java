import java.util.HashMap;
import java.util.Map;

public class TabelaProcessos {
    private final Map<String, BCP> tabela = new HashMap<>();

    public TabelaProcessos() {}

    public void add(BCP processo) {
        tabela.put(processo.getNome(), processo);
    }

    @Override
    public String toString() {
        return "TabelaProcessos{" +
                "tabela=" + tabela +
                '}';
    }
}
