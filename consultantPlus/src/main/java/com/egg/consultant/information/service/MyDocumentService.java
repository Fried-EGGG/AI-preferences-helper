package com.egg.consultant.information.service;

import com.egg.consultant.information.mapper.MyDocumentMapper;
import com.egg.consultant.information.pojo.MyDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-29-00:10
 * @Modified By:
 */
@Service
public class MyDocumentService
{
    @Autowired
    MyDocumentMapper myDocumentMapper;

    public MyDocument selectByName(String name)
    {
        return myDocumentMapper.selectByName(name);
    }

    public void insertMyDocument(MyDocument myDocument)
    {
        myDocumentMapper.insertMyDocument(myDocument);
    }
}
