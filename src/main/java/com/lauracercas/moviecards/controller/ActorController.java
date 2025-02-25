/**
 * Autora: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 * Cambios: José R. Hilera (2024) para eliminar la parte cliente de la aplicación original
 * Modificado: 2025-02-25 添加演员编辑和删除功能
 */

package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.service.actor.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ActorController {

    @Autowired
    private ActorService actorService;

    // Logger 初始化
    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    /**
     * GET /actors
     * 显示所有演员
     */
    @GetMapping("/actors")
    public String getActorsList(Model model) {
        model.addAttribute("actors", actorService.getAllActors());
        return "actors"; // 确保有 actors.html 文件
    }

    /**
     * GET /actors/new
     * 显示添加新演员表单
     */
    @GetMapping("/actors/new")
    public String newActorForm(Model model) {
        model.addAttribute("actor", new Actor());
        return "actor_form"; // 确保有 actor_form.html 文件
    }

    /**
     * POST /actors
     * 保存新演员或更新演员
     */
    @PostMapping("/actors")
    public String saveActor(@ModelAttribute("actor") Actor actor) {
        // 打印日志，查看传递的演员对象
        logger.info("Saving actor: {}", actor);
        
        actorService.save(actor);
        
        // 打印成功保存后的消息
        logger.info("Actor saved successfully: {}", actor);
        
        return "redirect:/actors";
    }

    /**
     * GET /actors/edit/{id}
     * 根据演员 ID 加载编辑页面
     */
    @GetMapping("/actors/edit/{id}")
    public String editActor(@PathVariable Integer id, Model model) {
        try {
            Actor actor = actorService.getActorById(id);
            if (actor == null) {
                throw new RuntimeException("未找到对应的演员 ID: " + id);
            }
            model.addAttribute("actor", actor);
            return "actor_form"; // 使用相同的表单进行编辑
        } catch (Exception e) {
            e.printStackTrace(); // 控制台打印错误日志
            return "error"; // 确保有 error.html 页面
        }
    }

    /**
     * GET /actors/delete/{id}
     * 删除演员并重定向到演员列表
     */
    @GetMapping("/actors/delete/{id}")
    public String deleteActor(@PathVariable Integer id) {
        try {
            actorService.deleteActorById(id);
            return "redirect:/actors"; // 删除成功后返回演员列表
        } catch (Exception e) {
            e.printStackTrace(); // 错误处理
            return "error"; // 如果删除过程中出现错误，跳转到 error 页面
        }
    }
}
