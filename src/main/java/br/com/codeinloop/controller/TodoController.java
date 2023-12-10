package br.com.codeinloop.controller;

import br.com.codeinloop.model.Todo;
import br.com.codeinloop.service.TodoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.MalformedURLException;
import java.net.URI;

@Path("/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoController {
    @Inject
    TodoService service;
    @GET
    public Response readAll(){
        return Response.ok(service.readAll()).build();
    }
    @GET
    @Path("/{id}")
    public Response readOne(@PathParam("id") Long id){
        return Response.ok(service.readOne(id)).build();
    }
    @POST
    @Transactional
    public Response create(Todo todo) throws MalformedURLException {
        Todo newTodo = service.create(todo);
        URI location = UriBuilder.fromResource(TodoController.class)
                .path("{id}")
                .resolveTemplate("id", newTodo.getId())
                .build();
        return Response.created(location).entity(newTodo).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteOne(@PathParam("id") Long id){
        service.deleteOne(id);
        return Response.noContent().build();
    }
    @DELETE
    @Transactional
    public Response deleteAll(){
        service.deleteAll();
        return Response.noContent().build();
    }
    @PUT
    @Path("{id}")
    @Transactional
    public Response fullUpdate(@PathParam("id") Long id, Todo todo){
        Todo updatedTodo = service.Update(id, todo);
        return Response.ok(updatedTodo).build();
    }

    @PATCH
    @Path("{id}")
    @Transactional
    public Response partialUpdate(@PathParam("id") Long id, Todo todo){
        Todo updatedTodo = service.Update(id, todo);
        return Response.ok(updatedTodo).build();
    }

}
