package net.erintl.scheduleapi.controller;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import net.erintl.scheduleapi.model.LiveStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class LiveStreamControllerTest {
  
  @Autowired
  TestRestTemplate restTemplate;

  @Test
  @Order(1)
  public void findAll() {
    ResponseEntity<List<LiveStream>> entity = findAllStreams();

    assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
    assertThat(entity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON));
    assertThat(entity.getBody().size(), equalTo(1));
  }

  @Test
  @Order(2)
  public void findById() {
    LiveStream existingStream = findAllStreams().getBody().get(0);
    String id = existingStream.id();
    String title = "Building REST APIs with Spring Boot";

    LiveStream stream = restTemplate.getForObject("/streams/" + id, LiveStream.class);
    
    assertThat(stream.id(), equalTo(id));
    assertThat(stream.title(), equalTo(title));
  }

  @Test
  @Order(3)
  public void create() {
    String id = UUID.randomUUID().toString();
    LiveStream stream = new LiveStream(
      id,
      "Test Title",
      "Test Description",
      "https://erintl.net",
      LocalDateTime.of(2022, 2, 16, 11, 0),
      LocalDateTime.of(2022, 2, 16, 12, 0));

      ResponseEntity<LiveStream> entity = restTemplate.postForEntity("/streams", stream,
        LiveStream.class);

      assertThat(entity.getStatusCode(), equalTo(HttpStatus.CREATED));
      assertThat(findAllStreams().getBody().size(), equalTo(2));
      
      LiveStream resultStream = entity.getBody();
      assertThat(resultStream.id(), equalTo(id));
      assertThat(resultStream.title(), equalTo("Test Title"));
      assertThat(resultStream.description(), equalTo("Test Description"));
      assertThat(resultStream.url(), equalTo("https://erintl.net"));
      assertThat(resultStream.startDate(),  equalTo(LocalDateTime.of(2022, 2, 16, 11, 0)));
      assertThat(resultStream.endDate(),  equalTo(LocalDateTime.of(2022, 2, 16, 12, 0)));
  }

  @Test
  @Order(4)
  public void update() {
    LiveStream existing = findAllStreams().getBody().get(0);
    LiveStream stream = new LiveStream(
      existing.id(),
      "New Title",
      existing.description(),
      existing.url(),
      existing.startDate(),existing.endDate()
    );

    ResponseEntity<LiveStream> entity = restTemplate.exchange("/streams/" + existing.id(),
      HttpMethod.PUT, new HttpEntity<>(stream), LiveStream.class);
    LiveStream updatedStream = findAllStreams().getBody().get(0);
    
    
    assertThat(entity.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    assertThat(updatedStream.title(), equalTo("New Title"));
  }

  @Test
  @Order(5)
  public void delete() {
    LiveStream existing = findAllStreams().getBody().get(0);

    ResponseEntity<LiveStream> entity = restTemplate.exchange("/streams/" + existing.id(),
      HttpMethod.DELETE, null, LiveStream.class);

    assertThat(entity.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    assertThat(findAllStreams().getBody().size(), equalTo(1));
  }

  private ResponseEntity<List<LiveStream>> findAllStreams() {
    return restTemplate.exchange("/streams",
    HttpMethod.GET,
    new HttpEntity<>(null),
    new ParameterizedTypeReference<>() {});
  }
}
