package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping("/tags")
    public List<TagDto> readAll() {
        return service.readAll();
    }

    @GetMapping("/tag/{id}")
    public TagDto read(@PathVariable int id) {
        return service.read(id);
    }

    @PostMapping("/tag")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return service.create(tagDto);
    }

    @DeleteMapping("/tag/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

}
