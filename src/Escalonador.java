import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

public class Escalonador {
    // Estruturas de dados
    private Queue<BCP> filaProntos;
    private List<BCP> listaBloqueados;
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
        // Precisamos decrementar o tempo de espera em todos os elementos da lista de bloqueados
        // LinkedList cria uma estrutura dinâmica, fácil de percorrer e de remover um elemento na n-ésima posição
        this.listaBloqueados = new LinkedList<>();
        this.tabelaProcessos = new TabelaProcessos();
        this.processoExecutando = null;
    }

    public void inicializar() {
        List<List<String>> programas = LeitorPrograma.carregarProgramas();
        System.out.println("Iniciando o Escalonador de Processos...");
        for (List<String> programa : programas) {
            System.out.println(programa + "|");
            BCP bcp = new BCP(programa.getFirst(), programa.toArray(new String[0]));
            this.tabelaProcessos.adicionar(bcp);
            this.filaProntos.add(bcp);
            this.totalProcessosCarregados++;
        }
    }

    public void run() {
        while (!tabelaProcessos.isEmpty()) {
            if (filaProntos.isEmpty() && !listaBloqueados.isEmpty()) esperaProcessosBloqueados();

            while (!filaProntos.isEmpty()) {
                BCP processo = filaProntos.remove();
                processo.setEstado(EstadoProcesso.EXECUTANDO);
                processoExecutando = processo;

                esperaProcessosBloqueados();

                for (int i = 0; i < quantum; i++) {
                    String comando = processo.proximoComando();

                    switch (comando) {
                        case "SAIDA":
                            tabelaProcessos.remover(processo);
                            i = quantum;
                            break;
                        case "E/S":
                            processo.setEstado(EstadoProcesso.BLOQUEADO);
                            listaBloqueados.add(processo);
                            i = quantum;
                            break;
                        default:
                            break;
                    }
                }

                if (tabelaProcessos.existe(processo) && processo.getEstado() == EstadoProcesso.EXECUTANDO) {
                    processo.setEstado(EstadoProcesso.PRONTO);
                    filaProntos.add(processo);
                }
            }
        }
        processoExecutando = null;
    }

    private void esperaProcessosBloqueados() {
        ListIterator<BCP> iterBloqueado = listaBloqueados.listIterator();
        while(iterBloqueado.hasNext()){
            BCP processoBloqueado = iterBloqueado.next();
            processoBloqueado.esperar();

            if (processoBloqueado.getEstado() == EstadoProcesso.PRONTO) {
                iterBloqueado.remove();
                filaProntos.add(processoBloqueado);
            }
        }
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
