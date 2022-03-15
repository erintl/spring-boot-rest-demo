package net.erintl.scheduleapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.erintl.scheduleapi.exception.LiveStreamNotFoundException;
import net.erintl.scheduleapi.model.LiveStream;
import net.erintl.scheduleapi.repository.LiveStreamRepository;

@RestController
@RequestMapping("/streams")
public class LiveStreamController {
  private final LiveStreamRepository repository;

  @Autowired
  public LiveStreamController(LiveStreamRepository repository) {
    this.repository = repository;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<LiveStream> findAll() {
    return repository.findAll();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  public LiveStream findById(@PathVariable String id) throws LiveStreamNotFoundException {
    return repository.findById(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public LiveStream create(@Valid @RequestBody LiveStream stream) {
    return repository.create(stream);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public void update(@Valid @RequestBody LiveStream stream, @PathVariable String id) {
    repository.update(stream, id);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    repository.delete(id);
  }
}
