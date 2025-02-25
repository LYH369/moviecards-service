package com.lauracercas.moviecards.service.actor;

import com.lauracercas.moviecards.model.Actor;
import java.util.List;

public interface ActorService {
    // 获取所有演员
    List<Actor> getAllActors();

    // 根据演员 ID 获取演员信息
    Actor getActorById(Integer id);

    // 保存或更新演员
    Actor save(Actor actor);

    // 删除演员
    void deleteActorById(Integer id);  // 新增删除方法
}
