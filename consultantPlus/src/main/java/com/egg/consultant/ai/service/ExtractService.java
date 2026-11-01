package com.egg.consultant.ai.service;

import com.egg.consultant.information.pojo.AdmissionList;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-28-10:38
 * @Modified By:
 */
public interface ExtractService
{
    @SystemMessage(fromResource = "/prompt/admissionExtractPrompt.txt")
//    public List<Admission> admissionExtract(@UserMessage String text);
    public AdmissionList admissionExtract(@UserMessage String text);
}
