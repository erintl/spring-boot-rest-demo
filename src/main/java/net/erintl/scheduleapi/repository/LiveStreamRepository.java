package net.erintl.scheduleapi.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import net.erintl.scheduleapi.exception.LiveStreamNotFoundException;
import net.erintl.scheduleapi.model.LiveStream;

@Repository
public class LiveStreamRepository {
  List<LiveStream> streams = new ArrayList<>();

  public LiveStreamRepository() {
    streams.add(new LiveStream(UUID.randomUUID().toString(),
    "Building REST APIs with Spring Boot", "Stream Description", "https://erintl.net",
    LocalDateTime.of(2022, 2, 16, 11, 0), LocalDateTime.of(2022, 2, 16, 12, 0)));
  }

  public List<LiveStream> findAll() {
    return streams;
  }

  public LiveStream findById(String id) throws LiveStreamNotFoundException {
    return streams.stream().filter(stream -> stream.id().equals(id)).findFirst()
      .orElseThrow(LiveStreamNotFoundException::new);
  }

  public void update(LiveStream stream, String id) {
    LiveStream existing = streams.stream().filter(s -> s.id().equals(id))
      .findFirst().orElseThrow(() -> new IllegalArgumentException("Stream not found"));
    int i = streams.indexOf(existing);
    streams.set(i, stream);
  }

  public LiveStream create(LiveStream stream) {
    streams.add(stream);
    return stream;
  }

  public void delete(String id) {
    streams.removeIf(s -> s.id().equals(id));
  }
}
