/**
 * Autora: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 * Cambios: José R. Hilera (2024) para eliminar la parte cliente de la aplicación original
 * Modificado: 2025-02-25 - 添加 removeActorFromMovie 方法以支持演员移除功能
 */

package com.lauracercas.moviecards.service.movie;

import com.lauracercas.moviecards.model.Movie;

import java.util.List;

public interface MovieService {

    // 获取所有电影
    List<Movie> getAllMovies();

    // 保存电影
    Movie save(Movie movie);

    // 根据电影 ID 获取电影
    Movie getMovieById(Integer movieId);

    // 添加演员到电影
    void registerActorInMovie(Integer idActor, Integer idMovie);

    // 从电影中移除演员
    void removeActorFromMovie(Integer idActor, Integer idMovie);
}
