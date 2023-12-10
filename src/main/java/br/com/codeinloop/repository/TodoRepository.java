package br.com.codeinloop.repository;

import br.com.codeinloop.model.Todo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TodoRepository implements PanacheRepository<Todo> {
}
