package com.egg.consultant;

import com.egg.consultant.information.pojo.Reservation;
import com.egg.consultant.information.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-22-16:53
 * @Modified By:
 */
@SpringBootTest
public class ReservationServiceTest
{
    @Autowired
    ReservationService reservationService;

    @Test
    void testInsert()
    {
        Reservation reservation = new Reservation(null, "煎蛋", "男", "13593152708", LocalDateTime.now(), "山西省", 504);
        reservationService.insert(reservation);
    }

    @Test
    void testFindByPhone()
    {
        String phone = "13593152708";
        Reservation reservation = reservationService.findByPhone(phone);
        System.out.println(reservation);
    }
}
