package com.bk.progresstracker;

import java.time.LocalDateTime;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProgressRepo extends PagingAndSortingRepository<ProgressData, Long> {

	Iterable<ProgressData> findByTimestampBetweenOrderByTimestampDesc(LocalDateTime startDate, LocalDateTime endDate);

}
