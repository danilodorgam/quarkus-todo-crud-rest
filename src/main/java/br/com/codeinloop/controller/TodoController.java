package br.com.codeinloop.controller;

import br.com.codeinloop.model.Todo;
import br.com.codeinloop.service.TodoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoController {
    @Inject
    TodoService service;
    @GET
    public Response read(){
        return Response.ok("lista").build();
    }
    @POST
    @Transactional
    public Response create(Todo todo){
        return Response.accepted(service.create(todo)).build();
    }
    @DELETE
    public Response delete(){
        return Response.accepted().build();
    }
}
