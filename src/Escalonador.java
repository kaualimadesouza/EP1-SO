import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Escalonador {
    // Estruturas de dados
    private Queue<BCP> filaProntos;
    private List<BCP> filaBloqueados;
    private TabelaProcessos tabelaProcessos;
    private BCP processoExecutando;

    // Unidade de quantum
    private int quantum;

    // Atributos para Estatísticas Finais
    private int totalTrocasDeProcesso = 0;
    private int totalInstrucoesExecutadas = 0;
    private int totalProcessosCarregados = 0;

    public Escalonador() {
        // Linked List funciona melhor em filas
        this.filaProntos = new LinkedList<>();

        // Vamos precisar ser array para facilitar nos decrementos
        this.filaBloqueados = new ArrayList<>();
        this.tabelaProcessos = new TabelaProcessos();
        this.processoExecutando = null;
    }

    public void run() {
        // TODO
    }

    public static void main(String[] args) {
        System.out.println("Iniciando o Escalonador de Processos...");

        // 1. Cria a instância
        Escalonador escalonador = new Escalonador();

        // 2. Executa a simulação
        escalonador.run();

        System.out.println("Todos os processos foram finalizados. Verifique o arquivo de log para detalhes.");
    }
}
