import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("Must provide directory");
      System.exit(1);
    }
    String directory = args[0];

    Path path = Path.of(directory);
    Files.walkFileTree(path, new PrintFiles());
  }
}
