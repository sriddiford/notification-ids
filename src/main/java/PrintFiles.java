import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class PrintFiles extends SimpleFileVisitor<Path> {

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
    if (attr.isRegularFile()) {
      System.out.format("Regular file: %s ", file);
      Path fileName = file.getFileName();
      if (fileName.startsWith("cas360-uat-notifications-firehose-stream-to-s3-3")) {
        byte[] allBytes = Files.readAllBytes(file);
        new JSONArray
      }
    }
    System.out.println("(" + attr.size() + "bytes)");
    return CONTINUE;
  }

  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    System.out.format("Directory: %s%n", dir);
    return CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file,
                                         IOException exc) {
    System.err.println(exc);
    return CONTINUE;
  }
}