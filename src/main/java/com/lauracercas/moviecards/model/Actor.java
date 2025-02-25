package com.lauracercas.moviecards.model;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String country;

    @DateTimeFormat(pattern = "yyyy-MM-dd")  // 确保日期格式正确
    private Date deadDate;  // 添加死日期字段

    @ManyToMany(mappedBy = "actors")
    @JsonIgnoreProperties("actors") // 防止序列化循环引用
    private List<Movie> movies;

    public Actor() {}

    public Actor(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDeadDate() {  // 添加 getter 方法
        return deadDate;
    }

    public void setDeadDate(Date deadDate) {  // 添加 setter 方法
        this.deadDate = deadDate;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id) &&
               Objects.equals(name, actor.name) &&
               Objects.equals(birthDate, actor.birthDate) &&
               Objects.equals(country, actor.country) &&
               Objects.equals(deadDate, actor.deadDate); // 比较死日期
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, country, deadDate); // 包含死日期
    }
}
