package com.egg.consultant.information.service;

import com.egg.consultant.information.mapper.ReservationMapper;
import com.egg.consultant.information.pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-22-16:37
 * @Modified By:
 */
@Service
public class ReservationService
{
    @Autowired
    ReservationMapper reservationMapper;

    public void insert(Reservation reservation)
    {
        reservationMapper.addReservation(reservation);
    }

    public Reservation findByPhone(String phone)
    {
        return reservationMapper.findByPhone(phone);
    }
}
