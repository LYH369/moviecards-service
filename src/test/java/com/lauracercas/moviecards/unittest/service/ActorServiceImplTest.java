/**
 * Autora: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 * Cambios: José R. Hilera (2024) para eliminar la parte cliente de la aplicación original
 * Modificado: 2025-02-25 修复 save() 测试返回类型错误并优化测试覆盖率
 */

package com.lauracercas.moviecards.unittest.service;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.repositories.ActorJPA;
import com.lauracercas.moviecards.service.actor.ActorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.ArgumentMatchers.any;   
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;

class ActorServiceImplTest {

    @Mock
    private ActorJPA actorJPA;

    @InjectMocks
    private ActorServiceImpl sut;  // ✅ 由 Mockito 自动注入并管理

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
     * ✅ 测试获取所有演员功能
     */
    @Test
    public void shouldGetAllActors() {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "Actor One"));
        actors.add(new Actor(2, "Actor Two"));

        when(actorJPA.findAll()).thenReturn(actors);

        List<Actor> result = sut.getAllActors();

        assertEquals(2, result.size());
        assertEquals("Actor One", result.get(0).getName());
        assertEquals("Actor Two", result.get(1).getName());
    }

    /**
     * ✅ 测试根据 ID 获取演员功能
     */
    @Test
    public void shouldGetActorById() {
        Actor actor = new Actor(1, "Sample Actor");

        when(actorJPA.findById(anyInt())).thenReturn(Optional.of(actor));

        Actor result = sut.getActorById(1);

        assertEquals(1, result.getId());
        assertEquals("Sample Actor", result.getName());
    }

    /**
     * ✅ 测试保存演员功能
     */
    @Test
    public void shouldSaveActor() {
        Actor actor = new Actor();
        actor.setName("New Actor");

        // ✅ 通过 any() 匹配器正确匹配并返回类型
        when(actorJPA.save(any(Actor.class))).thenReturn(actor);

        Actor result = sut.save(actor);

        assertEquals("New Actor", result.getName());
    }
}
