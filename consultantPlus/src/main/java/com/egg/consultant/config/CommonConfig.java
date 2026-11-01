package com.egg.consultant.config;

import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-18-15:52
 * @Modified By:
 */

@Configuration
public class CommonConfig
{
    @Autowired
    private OpenAiChatModel model;
    @Autowired
    private ChatMemoryStore redisChatMemoryStore;
    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private RedisEmbeddingStore redisEmbeddingStore;


    //构建向量数据库操作对象
    /**
     * 暂时将@Bean注释
     * 否则spring每次启动都会将方法执行一次，向量将会重新计算并存储一次
     * 造成对内存和token的浪费
     * 此处可以添加对文档是否向量化的检查，以防止反复向量化
     * */
    //@Bean
    public EmbeddingStore store()//langchain4j自动构建了embeddingStore，此处不能再起名embeddingStore，否则在IOC容器中重名
    {

        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content", new ApachePdfBoxDocumentParser());

        //2.构建向量数据库操作对象
        //InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();

        //构建文档分割器，指定每个片段最大容量字符数量为500，两个片段之间的重叠字符数量为100
        DocumentSplitter ds= DocumentSplitters.recursive(500,100);

        //3.构建一个EmbeddingStoreIngestor对象，完成文本数据切割，向量化，存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                //改用redisearch存储向量数据
                //.embeddingStore(store)
                .embeddingStore(redisEmbeddingStore)
                .documentSplitter(ds)
                .embeddingModel(embeddingModel)
                .build();
        ingestor.ingest(documents);

        //改用redisearch存储向量数据
        //return store;
        return redisEmbeddingStore;
    }

    @Bean
    public ContentRetriever contentRetriever()
    {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(redisEmbeddingStore)
                .embeddingModel(embeddingModel)
                .minScore(0.5)
                .maxResults(3)
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider()
    {
        ChatMemoryProvider chatMemoryProvider = new ChatMemoryProvider()
        {
            @Override
            public MessageWindowChatMemory get(Object messageId)
            {
                return MessageWindowChatMemory.builder()
                        .maxMessages(20)
                        .id(messageId)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();
            }
        };
        return chatMemoryProvider;
    }


    @Bean     //此处的@Bean的含义是这个方法的返回值需要注入springIOC容器
    public ChatMemory chatMemory()
    {
        MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
        return memory;
    }

}
