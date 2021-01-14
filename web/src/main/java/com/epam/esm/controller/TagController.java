package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception_handling.ErrorHandler;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    private TagService service;

    @PostMapping
    public TagDto createTag(@RequestBody TagDto tagDto) throws ServiceException {
        return service.createTag(tagDto);
    }

    @GetMapping
    public List<TagDto> readAllTags() {
        return service.readAllTags();
    }

    @GetMapping("/{id}")
    public TagDto readTagById(@PathVariable int id) throws ServiceException {
        return service.readTagById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTagById(@PathVariable int id) {
        service.deleteTag(id);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandler handleIncorrectParameterValueException(ServiceException exception) {
        return new ErrorHandler(exception.getMessage(), 40);
    }
}
