package com.egg.consultant.config;

import com.egg.consultant.ai.service.ExtractService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-30-21:30
 * @Modified By:
 */
@Configuration
public class ExtractModelConfig
{
    @Bean
    public ExtractService initExtractService()
    {
        //创建一个低温模型
        OpenAiChatModel extractModel = OpenAiChatModel.builder()
                .baseUrl("https://ws-qtcjf3o4vkn6aovw.cn-beijing.maas.aliyuncs.com/compatible-mode/v1")
                .modelName("qwen-plus")
                .apiKey(System.getenv("LANGCHAIN4J_API_KEY"))
                .temperature(0.0)
                .logResponses(true)
                .logRequests(true)
                .build();

        //配置低温模型
        ExtractService extractService = AiServices.builder(ExtractService.class)
                .chatModel(extractModel)
                .build();
        return extractService;
    }
}
