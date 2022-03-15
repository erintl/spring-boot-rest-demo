package net.erintl.scheduleapi.model;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class LiveStreamTest {
  @Test
  public void createNewMutableLiveStream() {
    MutableLiveStream stream = new MutableLiveStream();
    stream.setId(UUID.randomUUID().toString());
    stream.setTitle("Building REST APIs with Spring Boot");
    stream.setUrl("https://erintl.net");
    stream.setStartDate(LocalDateTime.of(2022, 2, 16, 11, 0));
    stream.setEndDate(LocalDateTime.of(2022, 2, 16, 12, 0));
    stream.setDescription("""
        Multi
        Line
        String
        """);

    assertThat(stream, notNullValue());
    assertThat(stream.getTitle(), equalTo("Building REST APIs with Spring Boot"));
  }

  @Test
  public void createNewImmutableLiveStream() {

    ImmutableLiveStream stream = new ImmutableLiveStream(UUID.randomUUID().toString(),
      "Building REST APIs with Spring Boot", "Stream Description", "https://erintl.net",
      LocalDateTime.of(2022, 2, 16, 11, 0), LocalDateTime.of(2022, 2, 16, 12, 0));

    assertThat(stream, notNullValue());
    assertThat(stream.getTitle(), equalTo("Building REST APIs with Spring Boot"));
  }

  @Test
  public void createNewRecordLiveStream() {
    LiveStream stream = new LiveStream(UUID.randomUUID().toString(),
      "Building REST APIs with Spring Boot", "Stream Description", "https://erintl.net",
      LocalDateTime.of(2022, 2, 16, 11, 0), LocalDateTime.of(2022, 2, 16, 12, 0));

  
    assertThat(stream, notNullValue());
    assertThat(stream.title(), equalTo("Building REST APIs with Spring Boot"));
    assertThat(stream.getClass().isRecord(), equalTo(true));
    assertThat(stream.getClass().getRecordComponents().length, equalTo(6));
  }
}
