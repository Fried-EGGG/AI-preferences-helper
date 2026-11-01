package com.egg.consultant.information.mapper;

import com.egg.consultant.information.pojo.MyDocument;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-29-00:08
 * @Modified By:
 */
@Mapper
public interface MyDocumentMapper
{
    @Select("select * from my_document where name = #{name}")
    public MyDocument selectByName(String name);

    @Insert("insert into my_document(id,name,status,created_at) values(#{id},#{name},#{status},#{createdAt}) ")
    public void insertMyDocument(MyDocument myDocument);
}
