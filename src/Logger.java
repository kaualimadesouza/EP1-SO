import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Logger {

    private final List<String> logs = new LinkedList<>();
    private static Logger instance;

    public Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String log) {
        logs.add(log);
    }

    public String coletar() {
        return String.join("\n", logs);
    }

    public void exportar(String fileName) {
        try {
            Path path = Paths.get(fileName);
            byte[] strToBytes = coletar().getBytes();
            Files.write(path, strToBytes);
        } catch (IOException e) {
            System.out.println("Falha ao exportar logs...");
            e.printStackTrace();
            System.out.println(logs);
            System.out.println("-------------------------");
        }
    }
}
