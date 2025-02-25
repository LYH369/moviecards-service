package com.lauracercas.moviecards.service.actor;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.repositories.ActorJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List; // 已添加的 import
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorJPA actorJPA;

    @Override
    public List<Actor> getAllActors() {
        return actorJPA.findAll();  // 获取所有演员
    }

    @Override
    public Actor getActorById(Integer id) {
        return actorJPA.findById(id).orElse(null);  // 根据 ID 获取演员，如果没有找到则返回 null
    }

    @Override
    public Actor save(Actor actor) {
        return actorJPA.save(actor);  // 保存演员
    }

    @Override
    public void deleteActorById(Integer id) {
        actorJPA.deleteById(id);  // 根据 ID 删除演员
    }
}
