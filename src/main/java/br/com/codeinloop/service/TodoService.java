package br.com.codeinloop.service;

import br.com.codeinloop.model.Todo;
import br.com.codeinloop.repository.TodoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class TodoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoService.class);
    @Inject
    TodoRepository repository;
    @Inject
    UriInfo uriInfo;

    public Todo create(Todo todo) throws MalformedURLException {
        LOGGER.info("Creating new Todo item: {}", todo);
        String scheme = uriInfo.getRequestUri().getScheme();
        URL uri = this.uriInfo.getAbsolutePathBuilder().scheme(scheme).build().toURL();
        todo.setUrl(uri);
        repository.persist(todo);
        LOGGER.info("Todo item created successfully: {}", todo);
        return todo;
    }
    public List<Todo> readAll() {
        LOGGER.info("Fetching all Todo items");
        List<Todo> todoList = repository.listAll(Sort.by("order"));
        LOGGER.info("Found {} Todo items", todoList.size());
        return todoList;
    }
    public Todo readOne(Long id) {
        try {
            LOGGER.info("Fetching Todo item with id: {}", id);
            return this.validateIfExistsAtDatabase(id);
        } catch (NotFoundException e) {
            LOGGER.error("Todo item not found for id: {}", id, e);
            throw e;
        }
    }
    public Todo Update(Long id, Todo todoForUpdate) {
        Todo todoStored = this.validateIfExistsAtDatabase(id);
        return update(todoStored, todoForUpdate);
    }

    public void deleteOne(Long id) {
       this.validateIfExistsAtDatabase(id);
       repository.deleteById(id);
    }
    public void deleteAll() {
            repository.deleteAll();
    }
    private Todo update( Todo todoStored, Todo todoForUpdate) {
        merge(todoStored, todoForUpdate);
        repository.persistAndFlush(todoStored);
        return todoStored;
    }
    private Todo validateIfExistsAtDatabase(Long id){
        Optional<Todo> todoExist = repository.findByIdOptional(id);
        return todoExist.orElseThrow(() -> new NotFoundException("Item not found"));
    }
    private void merge(Todo current, Todo todoItem) {
        if (todoItem.getTitle() != null) {
            current.setTitle(todoItem.getTitle());
        }
        if (todoItem.getOrder() != null) {
            current.setOrder(todoItem.getOrder());
        }
        current.setCompleted(todoItem.getCompleted());
    }

}
