package com.scrumit.retro.repository;

import com.scrumit.retro.bean.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
