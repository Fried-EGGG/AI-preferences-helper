package com.egg.consultant.repository;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-20-16:34
 * @Modified By:
 */
@RestController
public class RedisChatMemoryStore implements ChatMemoryStore
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId)
    {
        //更新会话消息
        //从redis中获取json
        String json = stringRedisTemplate.opsForValue().get(memoryId);
        //将json反序列化，
        List<ChatMessage> messages = ChatMessageDeserializer.messagesFromJson(json);
        return messages;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages)
    {
        //将消息序列化
        String json = ChatMessageSerializer.messagesToJson(messages);
        //将序列化的消息（json）存入redis，保存1天
        stringRedisTemplate.opsForValue().set(memoryId.toString(), json, Duration.ofDays(1));
    }

    @Override
    public void deleteMessages(Object memoryId)
    {
        stringRedisTemplate.delete(memoryId.toString());
    }
}
