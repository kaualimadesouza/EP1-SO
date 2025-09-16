public class BCP {
    private static final int TEMPO_ESPERA_BLOQUEADO = 2;

    private int pc = 0;
    private int espera = 0;
    private EstadoProcesso estado = EstadoProcesso.PRONTO;
    private Registrador[] registradores;
    private final String[] comandos;
    private final String nome;

    public BCP(String nome, String[] comandos) {
        this.nome = nome;
        this.comandos = comandos;
    }

    public String getNome() {
        return nome;
    }

    public EstadoProcesso getEstado() {
        return estado;
    }

    public void setEstado(EstadoProcesso estado) {
        if (estado == EstadoProcesso.BLOQUEADO) {
            this.espera = TEMPO_ESPERA_BLOQUEADO;
        }

        this.estado = estado;
    }

    public void esperar() {
        espera--;

        if (espera == 0) {
            estado = EstadoProcesso.PRONTO;
        }
    }

    public String proximoComando() {
        return comandos[pc++];
    }
}
