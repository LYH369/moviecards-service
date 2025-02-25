/**
 * Autora: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 * Cambios: José R. Hilera (2024) para eliminar la parte cliente de la aplicación original
 *          Modificado para adaptarse a la nueva versión de ActorController que retorna vistas.
 */

package com.lauracercas.moviecards.unittest.controller;

import com.lauracercas.moviecards.controller.ActorController;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.service.actor.ActorService;
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

class ActorControllerTest {

    @Mock
    private ActorService actorService;

    @InjectMocks
    private ActorController controller = new ActorController();

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGoListActorAndGetAllActors() {
        // 准备模拟数据
        List<Actor> actors = new ArrayList<>();
        Actor actor1 = new Actor();
        actor1.setId(1);
        Actor actor2 = new Actor();
        actor2.setId(2);
        actors.add(actor1);
        actors.add(actor2);

        when(actorService.getAllActors()).thenReturn(actors);

        // 使用 ExtendedModelMap 模拟 Model 对象
        Model model = new ExtendedModelMap();
        String viewName = controller.getActorsList(model);

        // 验证返回的视图名称
        assertEquals("actors", viewName);
        // 验证 Model 中是否包含 actors 属性且数量为 2
        List<Actor> modelActors = (List<Actor>) model.asMap().get("actors");
        assertEquals(2, modelActors.size());
    }

    @Test
    public void shouldGoToEditActor() {
        // 准备模拟数据
        Actor actor = new Actor();
        actor.setId(1);
        actor.setName("Sample Actor");

        when(actorService.getActorById(actor.getId())).thenReturn(actor);

        Model model = new ExtendedModelMap();
        String viewName = controller.editActor(actor.getId(), model);

        // 验证返回视图名称
        assertEquals("actor_form", viewName);
        // 验证 Model 中添加的 actor 数据
        Actor modelActor = (Actor) model.asMap().get("actor");
        assertEquals(1, modelActor.getId());
        assertEquals("Sample Actor", modelActor.getName());
    }
}
