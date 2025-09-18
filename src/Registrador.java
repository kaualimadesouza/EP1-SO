public class Registrador {
    private final ChaveRegistrador chave;
    private int valor;

    public Registrador(ChaveRegistrador chave, int valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public ChaveRegistrador getChave() {
        return chave;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
