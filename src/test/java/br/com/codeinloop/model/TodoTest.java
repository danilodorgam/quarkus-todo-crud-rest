package br.com.codeinloop.model;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TodoTest {
    @Test
    void testGetUrl() throws Exception {
        Todo todo = new Todo();
        todo.setUrl(new URI("http://localhost:8080/todo").toURL());

        URL resultUrl = todo.getUrl();
        assertNotNull(resultUrl);
        assertEquals("http://localhost:8080/todo", resultUrl.toString());
    }
    @Test
    void testsetId()  {
        Todo todo = new Todo();
        todo.setId(2L);

        Long id = todo.getId();
        assertNotNull(id);
        assertEquals(2L, id);
    }

}
