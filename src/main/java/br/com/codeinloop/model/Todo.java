package br.com.codeinloop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.ws.rs.core.UriBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private boolean completed;
    @Column(name = "\"order\"")
    private Integer order;
    private URL url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getCompleted() {
        return this.completed;
    }
    public URL getUrl() throws URISyntaxException, MalformedURLException {
        if (this.id != null) {
            return UriBuilder.fromUri(url.toURI()).scheme(url.getProtocol()).path(this.id.toString()).build().toURL();
        }
        return this.url;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }



    public void setUrl(URL url) {
        this.url = url;
    }
}
