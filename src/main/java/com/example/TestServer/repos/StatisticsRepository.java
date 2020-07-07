package com.example.TestServer.repos;

import com.example.TestServer.entities.Statistics;
import com.example.TestServer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    List<Statistics> findAllByChangeTimeAfterAndStatusId(long timestamp, int statusId);
}
