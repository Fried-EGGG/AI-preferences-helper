package com.egg.consultant.information.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-19:54
 * @Modified By:
 */
@Service
public class InformationService
{
    @Autowired
    AdmissionService admissionService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    ScoreRankService scoreRankService;




}
