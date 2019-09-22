package com.gulci.reservations.data.repository;

import com.gulci.reservations.data.model.Guest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {

}