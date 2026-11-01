package com.egg.consultant.ai.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-18-15:51
 * @Modified By:
 */

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,//手动装配
        chatModel = "openAiChatModel", //指定模型（阻塞式调用）
        streamingChatModel = "openAiStreamingChatModel",//指定模型（流式调用）
        //chatMemory = "chatMemory",//设置会话记忆对象，只有一个会话记忆对象，此对象没有用户隔离，不同用户公用一份记忆
        chatMemoryProvider = "chatMemoryProvider",//设置会话记忆提供者对象，此对象实现了用户隔离，不同用户使用不同的会话记忆
        contentRetriever = "contentRetriever",//配置向量数据库检索对象
        tools = {"reservationTools", "informationTools"}//配置Tools工具
)
public interface ConsultantService
{
    //添加了会话id，实现会话隔离
    @SystemMessage(fromResource = "system.txt")
    //当参数只有用户消息时不需要加注解，有多条参数时需要加注解来标识
    public Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
}
