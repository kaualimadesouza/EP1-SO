import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.stream.Collectors;

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
    private int totalInstrucoesExecutadasPorQuantum = 0;
    private int totalProcessosCarregados = 0;
    private int totalQuantumRodados = 0;

    private final Logger logger = Logger.getInstance();

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
            BCP bcp = new BCP(programa.getFirst(), programa.subList(1, programa.size()).toArray(new String[0]));
            this.tabelaProcessos.adicionar(bcp);
            this.filaProntos.add(bcp);
            this.totalProcessosCarregados++;

            logger.log("Carregando " + bcp.getNome());
        }
    }

    public void run() {
        while (!tabelaProcessos.isEmpty()) {
            if (filaProntos.isEmpty() && !listaBloqueados.isEmpty()) esperaProcessosBloqueados();

            while (!filaProntos.isEmpty()) {
                BCP processo = filaProntos.remove();
                processo.setEstado(EstadoProcesso.EXECUTANDO);
                processoExecutando = processo;
                logger.log("Executando " + processo.getNome());

                esperaProcessosBloqueados();

                int i;
                quantum_loop: for (i = 0; i < quantum; i++) {
                    String comando = processo.proximoComando();

                    switch (comando.split("=")[0]) {
                        case "SAIDA":
                            logger.log(processo.getNome() + " terminado. " + Arrays.stream(processo.getRegistradores()).map(r -> r.getChave() + "=" + r.getValor()).collect(Collectors.joining(". ")));
                            tabelaProcessos.remover(processo);
                            break quantum_loop;
                        case "E/S":
                            logger.log("E/S iniciada em " + processo.getNome());
                            processo.setEstado(EstadoProcesso.BLOQUEADO);
                            listaBloqueados.add(processo);
                            break quantum_loop;
                        case "X":
                        case "Y":
                            for (Registrador r : processo.getRegistradores())
                                r.setValor(Integer.parseInt(comando.split("=")[1]));
                            break;
                        default:
                            break;
                    }
                }
                logger.log("Interrompendo " + processo.getNome() + " após " + i + (i == 1 ? " instrução" : " instruções"));
                totalTrocasDeProcesso++;
                totalInstrucoesExecutadasPorQuantum+=i;
                totalQuantumRodados++;

                if (tabelaProcessos.existe(processo) && processo.getEstado() == EstadoProcesso.EXECUTANDO) {
                    processo.setEstado(EstadoProcesso.PRONTO);
                    filaProntos.add(processo);
                }
            }
        }

        logger.log("MEDIA DE TROCAS: %.1f".formatted((double) totalTrocasDeProcesso / totalProcessosCarregados));
        logger.log("MEDIA DE INSTRUCOES: %.1f".formatted((double) totalInstrucoesExecutadasPorQuantum / totalQuantumRodados));
        logger.log("QUANTUM: " + quantum);
        logger.exportar("log" + String.format("%02d", quantum) + ".txt");
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
        int quantum = Math.max(1, Math.min(21, LeitorPrograma.carregarQuantum()));

        // 1. Cria a instância
        Escalonador escalonador = new Escalonador(quantum);

        // 2. Iniciar os programas na memória
        escalonador.inicializar();

        // 3. Executa a simulação
        escalonador.run();

        System.out.println("Todos os processos foram finalizados. Verifique o arquivo de log para detalhes.");
    }
}
