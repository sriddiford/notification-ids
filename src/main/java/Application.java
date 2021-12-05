import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("Must provide directory");
      System.exit(1);
    }
    String directory = args[0];

    Path path = Path.of(directory);
    PrintFiles printFiles = new PrintFiles();
    Files.walkFileTree(path, printFiles);
    ArrayList<String> asList = new ArrayList<>(printFiles.getSeenNotificationIds());
    Collections.shuffle(asList);
    List<String> sample = asList.subList(0, 501);
    String ids = sample.stream().collect(Collectors.joining(","));
    System.out.println("(" + ids + ")");
  }
}
