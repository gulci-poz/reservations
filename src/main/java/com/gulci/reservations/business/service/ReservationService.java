package com.gulci.reservations.business.service;

import com.gulci.reservations.business.domain.RoomReservation;
import com.gulci.reservations.data.model.Guest;
import com.gulci.reservations.data.model.Reservation;
import com.gulci.reservations.data.model.Room;
import com.gulci.reservations.data.repository.GuestRepository;
import com.gulci.reservations.data.repository.ReservationRepository;
import com.gulci.reservations.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private RoomRepository roomRepository;
    private GuestRepository guestRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    // shitty version
    //public List<RoomReservation> getRoomReservationsForDate(Date date) {
    //    Iterable<Room> rooms = this.roomRepository.findAll();
    //
    //    Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
    //    rooms.forEach(room -> {
    //        RoomReservation roomReservation = new RoomReservation();
    //        roomReservation.setRoomId(room.getId());
    //        roomReservation.setRoomName(room.getName());
    //        roomReservation.setRoomNumber(room.getNumber());
    //        roomReservationMap.put(room.getId(), roomReservation);
    //    });
    //
    //    Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
    //    if (null != reservations) {
    //        reservations.forEach(reservation -> {
    //            Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());
    //            if (guestResponse.isPresent()) {
    //                Guest guest = guestResponse.get();
    //                RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
    //                roomReservation.setDate(date);
    //                roomReservation.setFirstName(guest.getFirstName());
    //                roomReservation.setFirstName(guest.getLastName());
    //                roomReservation.setGuestId(guest.getId());
    //            }
    //        });
    //    }
    //
    //    List<RoomReservation> roomReservations = new ArrayList<>();
    //    for (Long roomId : roomReservationMap.keySet()) {
    //        roomReservations.add(roomReservationMap.get(roomId));
    //    }
    //
    //    return roomReservations;
    //}

    public List<RoomReservation> getRoomReservationsForDate(Date date) {

        Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
        List<RoomReservation> roomReservations = new ArrayList<>();

        if (null != reservations) {
            reservations.forEach(reservation -> {
                Optional<Room> roomResponse = this.roomRepository.findById(reservation.getRoomId());
                Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());

                if (guestResponse.isPresent() && roomResponse.isPresent()) {
                    Guest guest = guestResponse.get();
                    Room room = roomResponse.get();
                    RoomReservation roomReservation = new RoomReservation();
                    roomReservation.setDate(reservation.getDate());
                    roomReservation.setRoomId(room.getId());
                    roomReservation.setRoomName(room.getName());
                    roomReservation.setRoomNumber(room.getNumber());
                    roomReservation.setGuestId(guest.getId());
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                    roomReservations.add(roomReservation);
                }
            });
        }

        return roomReservations;
    }
}
