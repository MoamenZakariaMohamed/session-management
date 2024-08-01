package com.itida.session.management.tracker.service;

import com.itida.session.management.auth.domain.User;
import com.itida.session.management.auth.service.AuthenticationService;
import com.itida.session.management.infrastructure.exception.GlobalBusinessException;
import com.itida.session.management.tracker.domain.Tracker;
import com.itida.session.management.tracker.domain.TrackerDto;
import com.itida.session.management.tracker.domain.TrackerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.itida.session.management.infrastructure.exception.ErrorMessages.TRACKER_001;

/**
 * The type Tracker service implementation.
 */
@Service
@RequiredArgsConstructor
public class TrackerServiceImplementation implements TrackerService {
    private final TrackerRepository trackerRepository;
    private final AuthenticationService authService;
    @Override
    public void trackSession(TrackerDto dto) {
        trackerRepository.save(mapToTracker(dto, new Tracker()));
    }

    @Override
    public Tracker getTrackerById(Long id) {
        return trackerRepository.findById(id).orElseThrow(()->
                new GlobalBusinessException(HttpStatus.NOT_FOUND, TRACKER_001 ,id)
                );
    }

    @Override
    public Page<Tracker> getAllTrackers(int start, int count) {
        LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(start, count);
        return trackerRepository.findAllByDateEqualsAndAndUser(today,loggedIn(),pageable);
    }

    private User loggedIn(){
        return authService.getCurrentUser();
    }

    private Tracker mapToTracker(TrackerDto dto, Tracker entity) {
        entity.setUser(loggedIn());
        entity.setDate(dto.date());
        entity.setLoginTime(dto.loginTime());
        entity.setLogoutTime(dto.logoutTime());
        return entity;
    }
}
