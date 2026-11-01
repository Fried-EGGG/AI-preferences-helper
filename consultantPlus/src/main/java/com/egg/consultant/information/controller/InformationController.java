package com.egg.consultant.information.controller;

import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-17:04
 * @Modified By:
 */
public class InformationController
{

    public void getInfoJson()
    {
        ClassPathDocumentLoader.loadDocument("prompt/admissionExtractPrompt.txt",new ApacheTikaDocumentParser());





    }


}
