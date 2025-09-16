public class BCP {
    private int pc = 0;
    private EstadoProcesso estado = EstadoProcesso.PRONTO;
    private Registrador[] registradores;
    private String[] comandos;
    private final String nome;

    public BCP(String nome, String[] comandos, Registrador[] registradores) {
        this.nome = nome;
        this.comandos = comandos;
        this.registradores = registradores;
    }

    public String getNome() {
        return this.nome;
    }
}
