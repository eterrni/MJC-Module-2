package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles requests related to the tag
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService service;

    /**
     * Get all tags
     *
     * @return List of found tags
     */
    @GetMapping("/tags")
    public List<TagDto> readAll() {
        return service.readAll();
    }

    /**
     * Get tag by id
     *
     * @param id of the tag we want to get
     * @return the tag on this id
     */
    @GetMapping("/tag/{id}")
    public TagDto read(@PathVariable int id) {
        return service.read(id);
    }

    /**
     * Create a new tag
     *
     * @param tagDto we want to create
     * @return created tag with its id
     */
    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return service.create(tagDto);
    }

    /**
     * Delete a tag by ID
     *
     * @param id of the tag we want to delete
     */
    @DeleteMapping("/tag/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

}
