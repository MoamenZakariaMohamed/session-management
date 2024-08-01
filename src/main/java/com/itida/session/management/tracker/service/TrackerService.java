package com.itida.session.management.tracker.service;

import com.itida.session.management.tracker.domain.Tracker;
import com.itida.session.management.tracker.domain.TrackerDto;
import org.springframework.data.domain.Page;

/**
 * The interface Tracker service.
 */
public interface TrackerService {
    /**
     * Track session.
     *
     * @param dto the dto
     */
    void trackSession(TrackerDto  dto);

    /**
     * Gets tracker by id.
     *
     * @param id the id
     * @return the tracker by id
     */
    Tracker getTrackerById(Long id);

    /**
     * Gets all trackers.
     *
     * @param start the start
     * @param count the count
     * @return the all trackers
     */
    Page<Tracker> getAllTrackers(int start, int count);

}
