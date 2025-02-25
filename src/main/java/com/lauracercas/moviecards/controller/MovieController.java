package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    ActorService actorService;

    /**
     * 显示电影列表
     */
    @GetMapping("/movies")
    public String getMoviesList(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies"; // 确保有 movies.html 文件
    }

    /**
     * 显示添加新电影表单
     */
    @GetMapping("/movies/new")
    public String newMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie_form"; // 确保有 movie_form.html 文件
    }

    /**
     * 保存电影（新建或更新）
     */
    @PostMapping("/movies")
    public String saveMovie(@ModelAttribute("movie") Movie movie) {
        movieService.save(movie);
        return "redirect:/movies";
    }

    /**
     * 编辑电影
     */
    @GetMapping("/movies/{movieId}")
    public String editMovie(@PathVariable Integer movieId, Model model) {
        try {
            Movie movie = movieService.getMovieById(movieId);
            if (movie == null) {
                throw new RuntimeException("未找到对应的电影 ID: " + movieId);
            }
            model.addAttribute("movie", movie);
            return "movie_form"; // 使用同一个表单进行编辑
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // 确保有 error.html 页面
        }
    }

    /**
     * 电影详情页，包括已关联的演员和可添加的演员
     */
    @GetMapping("/movies/detail/{movieId}")
    public String movieDetail(@PathVariable Integer movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("allActors", actorService.getAllActors());
        return "movie_detail"; // 确保 movie_detail.html 存在
    }

    /**
     * 为电影添加演员
     */
    @PostMapping("/movies/addActor/{movieId}")
    public String addActorToMovie(@PathVariable Integer movieId, @RequestParam Integer actorId) {
        movieService.registerActorInMovie(actorId, movieId);
        return "redirect:/movies/detail/" + movieId;
    }

    /**
     * 从电影中移除演员
     */
    @PostMapping("/movies/removeActor/{movieId}/{actorId}")
    public String removeActorFromMovie(@PathVariable Integer movieId, @PathVariable Integer actorId) {
        movieService.removeActorFromMovie(actorId, movieId);
        return "redirect:/movies/detail/" + movieId;
    }
}
