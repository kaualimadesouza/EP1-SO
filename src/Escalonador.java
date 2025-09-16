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

    public Escalonador(int quantum) {
        this.quantum = quantum;

        // Linked List funciona melhor em filas
        this.filaProntos = new LinkedList<>();

        // Vamos precisar ser array para facilitar nos decrementos
        this.filaBloqueados = new ArrayList<>();
        this.tabelaProcessos = new TabelaProcessos();
        this.processoExecutando = null;
    }

    public void inicializar() {
        List<List<String>> programas = LeitorPrograma.carregarProgramas();
        System.out.println("Iniciando o Escalonador de Processos...");
        for (List<String> programa : programas) {
            System.out.println(programa + "|");
            BCP bcp = new BCP(programa.getFirst(), programa.toArray(new String[0]));
            this.tabelaProcessos.add(bcp);
            this.filaProntos.add(bcp);
            this.totalProcessosCarregados++;
        }
    }

    public void run() {
        // TODO
    }

    public static void main(String[] args) {
        int quantum = LeitorPrograma.carregarQuantum();

        // 1. Cria a instância
        Escalonador escalonador = new Escalonador(quantum);

        // 2. Iniciar os programas na memória
        escalonador.inicializar();

        // 3. Executa a simulação
        escalonador.run();

        System.out.println("Todos os processos foram finalizados. Verifique o arquivo de log para detalhes.");
    }
}
