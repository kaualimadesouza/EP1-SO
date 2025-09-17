import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeitorPrograma {
    private static final String DIR_PATH = "programas";
    private static final Set<String> EXCLUDED_PATHS = Set.of("quantum.txt");

    private static Set<String> listarArquivos() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(LeitorPrograma.DIR_PATH))) {
            return stream
                    .filter(file -> !Files.isDirectory(file) && !EXCLUDED_PATHS.contains(file.getFileName().toString()))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    private static List<String> lerArquivo(String path) throws IOException {
        try (Stream<String> linhas = Files.lines(Path.of(path))) {
            return linhas.toList();
        }
    }

    public static List<List<String>> carregarProgramas() {
        try {
            return listarArquivos().stream().sorted().map(nomeArquivo -> {
                try {
                    return lerArquivo(DIR_PATH + '/' + nomeArquivo);
                } catch (IOException e) {
                    return Collections.<String>emptyList();
                }
            }).toList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static int carregarQuantum() {
        try {
            return Integer.parseInt(lerArquivo(DIR_PATH + "/quantum.txt").getFirst());
        } catch (IOException e) {
            return 0;
        }
    }
}
