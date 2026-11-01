package com.egg.consultant.ai.controller;

import com.egg.consultant.ai.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-17-22:21
 * @Modified By:
 */

@RestController
public class ChatController
{
    @Autowired
    ConsultantService consultantService;

    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(String memoryId, String message)
    {
        Flux<String> flux = consultantService.chat(memoryId, message);
        return flux;
    }

}
