import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.FileVisitResult.CONTINUE;

public class PrintFiles extends SimpleFileVisitor<Path> {

  private Set<String> seenNotificationIds = new HashSet<>();

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
    if (attr.isRegularFile()) {
      Path fileName = file.getFileName();
      String asString = fileName.toString();
      if (asString.startsWith("cas360-uat-notifications-firehose-stream-to-s3-3")) {
        byte[] allBytes = Files.readAllBytes(file);
        String contentsAsString = new String(allBytes);
        Files.lines(file).forEach(line -> {
          JSONObject awsResponseJSON = new JSONObject(line);
          JSONObject notificationDTO = new JSONObject(new String(Base64.getDecoder().decode(awsResponseJSON.getString("rawData"))));
          String notificationId = notificationDTO.getString("id");
          seenNotificationIds.add(notificationId);
        });

        JSONObject jsonObject = new JSONObject(contentsAsString);
        System.out.format("Regular file: %s ", file);
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

  public Set<String> getSeenNotificationIds() {
    return seenNotificationIds;
  }
}