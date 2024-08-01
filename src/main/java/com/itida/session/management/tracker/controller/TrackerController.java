package com.itida.session.management.tracker.controller;

import com.itida.session.management.tracker.domain.Tracker;
import com.itida.session.management.tracker.domain.TrackerDto;
import com.itida.session.management.tracker.service.TrackerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Tracker controller.
 */
@RestController
@RequestMapping("/api/v1/trackers")
@RequiredArgsConstructor
@Validated
@Slf4j
public class TrackerController {
    private final TrackerService trackerService;

    /**
     * Track.
     *
     * @param trackerDto the tracker dto
     */
    /*

     */
    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public void track(@Valid @RequestBody TrackerDto  trackerDto) {
        trackerService.trackSession(trackerDto);
    }

    /**
     * Gets tracker by id.
     *
     * @param id the id
     * @return the tracker by id
     */
    @GetMapping("/{id}")
    public Tracker getTrackerById(@PathVariable Long id) {
        return trackerService.getTrackerById(id);
    }

    /**
     * Gets all mapped pageable.
     *
     * @param start the start
     * @param count the count
     * @return the all mapped pageable
     */
    @GetMapping
    Page<Tracker> getAllMappedPageable(
            @RequestParam(required = false, defaultValue = "0") Integer start,
            @RequestParam(required = false, defaultValue = "10") Integer count){

        return trackerService.getAllTrackers(start,count);
    }


}
