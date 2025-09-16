import java.util.Arrays;

public class BCP {
    private int pc = 0;
    private EstadoProcesso estado = EstadoProcesso.PRONTO;
    private Registrador[] registradores;
    private String[] comandos;
    private final String nome;

    public BCP(String nome, String[] comandos) {
        this.nome = nome;
        this.comandos = comandos;
    }

    public String getNome() {
        return this.nome;
    }
    @Override
    public String toString() {
        return "BCP{" +
                "pc=" + pc +
                ", estado=" + estado +
                ", registradores=" + Arrays.toString(registradores) +
                ", comandos=" + Arrays.toString(comandos) +
                ", nome='" + nome + '\'' +
                '}';
    }
}
