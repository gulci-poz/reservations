package com.gulci.reservations.controller;

import com.gulci.reservations.business.domain.RoomReservation;
import com.gulci.reservations.business.service.ReservationService;
import com.gulci.reservations.data.model.Room;
import com.gulci.reservations.data.repository.ReservationRepository;
import com.gulci.reservations.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class RoomController {
    private final RoomRepository repository;
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public RoomController(RoomRepository repository, ReservationService reservationService, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    List<Room> findAll(@RequestParam(required = false) String roomNumber) {
        List<Room> rooms = new ArrayList<>();
        if (null == roomNumber) {
            Iterable<Room> results = this.repository.findAll();
            results.forEach(rooms::add);
        } else {
            Room room = this.repository.findByNumber(roomNumber);
            if (null != room) {
                rooms.add(room);
            }
        }
        return rooms;
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    List<RoomReservation> findReservationsByDate(@RequestParam() String date) {
        Date dateToPass = null;
        try {
            dateToPass = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateToPass != null) {
            return reservationService.getRoomReservationsForDate(dateToPass);
        } else {
            return new ArrayList<>();
        }
    }
}
