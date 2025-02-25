package com.lauracercas.moviecards.service.movie;

import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.repositories.MovieJPA;
import com.lauracercas.moviecards.repositories.ActorJPA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieJPA movieJPA;

    @Autowired
    private ActorJPA actorJPA;

    @Override
    public List<Movie> getAllMovies() {
        logger.info("获取所有电影列表。");
        return movieJPA.findAll();
    }

    @Override
    public Movie save(Movie movie) {
        logger.info("保存电影: {}", movie.getTitle());
        return movieJPA.save(movie);
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        logger.info("根据 ID 获取电影: {}", movieId);
        return movieJPA.findById(movieId)
                       .orElseThrow(() -> new RuntimeException("未找到 ID 为 " + movieId + " 的电影。"));
    }

    @Override
    public void registerActorInMovie(Integer idActor, Integer idMovie) {
        logger.info("将演员 {} 注册到电影 {}。", idActor, idMovie);
        Movie movie = movieJPA.findById(idMovie)
                              .orElseThrow(() -> new RuntimeException("未找到电影 ID: " + idMovie));
        Actor actor = actorJPA.findById(idActor)
                              .orElseThrow(() -> new RuntimeException("未找到演员 ID: " + idActor));

        movie.addActor(actor);
        movieJPA.save(movie);
    }

    @Override
    public void removeActorFromMovie(Integer idActor, Integer idMovie) {
        logger.info("从电影 {} 中移除演员 {}。", idMovie, idActor);
        Movie movie = movieJPA.findById(idMovie)
                              .orElseThrow(() -> new RuntimeException("未找到电影 ID: " + idMovie));
        Actor actor = actorJPA.findById(idActor)
                              .orElseThrow(() -> new RuntimeException("未找到演员 ID: " + idActor));

        if (movie.getActors().contains(actor)) {
            movie.getActors().remove(actor);
            movieJPA.save(movie);
            logger.info("成功从电影 {} 中移除演员 {}。", idMovie, idActor);
        } else {
            logger.warn("演员 {} 不存在于电影 {} 中。", idActor, idMovie);
        }
    }
}
