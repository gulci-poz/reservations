package com.gulci.reservations.data.repository;

import com.gulci.reservations.data.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Room findByNumber(String number);
}
