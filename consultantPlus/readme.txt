基于LangChain4j实现的AI志愿填报智能体
项目中涉及到的技术栈和api：
langchain4j，redis，DocumentLoader，Document Parser，Docuemnt Splitter，mysql，tools
主要功能实现了：
1.大模型回复消息流式输出
2.用户会话上下文条数限制
3.用户会话隔离
4.添加redis存储用户请求上下文
5.添加RAG知识库
6.引入向量检索，增强检索能力
7.引入redisearch数据库存储向量内容
8.编写tools工具，能够通过大模型请求预约志愿填报服务，能向数据库中存储和查询预约信息


