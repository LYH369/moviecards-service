/**
 * Autora: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 * Cambios: José R. Hilera (2024) para eliminar la parte cliente de la aplicación original
 * Cambios adicionales: 修复方法参数不匹配和泛型类型安全问题
 */

package com.lauracercas.moviecards.unittest.controller;

import com.lauracercas.moviecards.controller.MovieController;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MovieControllerTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    private MovieController controller = new MovieController();

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * ✅ 测试获取所有电影列表
     */
    @Test
    public void shouldGoListMovieAndGetAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());

        when(movieService.getAllMovies()).thenReturn(movies);

        Model model = new ExtendedModelMap();
        String viewName = controller.getMoviesList(model);  // ✅ 修复：传入 Model 参数
        List<Movie> modelMovies = (List<Movie>) model.getAttribute("movies"); // ✅ 修复：显式类型转换

        assertEquals("movies", viewName);  // 假设返回视图为 "movies"
        assertEquals(2, modelMovies.size());
    }

    /**
     * ✅ 测试编辑电影功能
     */
    @Test
    public void shouldGoToEditMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("Sample Movie");

        when(movieService.getMovieById(movie.getId())).thenReturn(movie);

        Model model = new ExtendedModelMap();
        String viewName = controller.editMovie(movie.getId(), model);  // ✅ 修复：传入 Model 参数
        Movie modelMovie = (Movie) model.getAttribute("movie");       // ✅ 修复：显式类型转换

        assertEquals("movie_form", viewName);  // 假设返回视图为 "movie_form"
        assertEquals(1, modelMovie.getId());
        assertEquals("Sample Movie", modelMovie.getTitle());
    }
}
