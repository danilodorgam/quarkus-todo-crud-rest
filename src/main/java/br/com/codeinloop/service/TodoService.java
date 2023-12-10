package br.com.codeinloop.service;

import br.com.codeinloop.model.Todo;
import br.com.codeinloop.repository.TodoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class TodoService {
    @Inject
    TodoRepository repository;
    public Todo create(Todo todo) {
        repository.persist(todo);
        return todo;
    }
}
