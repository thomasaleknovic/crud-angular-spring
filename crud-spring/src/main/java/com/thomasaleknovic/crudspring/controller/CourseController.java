package com.thomasaleknovic.crudspring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thomasaleknovic.crudspring.model.Course;
import com.thomasaleknovic.crudspring.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
    
    private CourseRepository courseRepository;
    
 

    @GetMapping
    public @ResponseBody List<Course> list() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable long id) {

        return courseRepository.findById(id)
                .map(recordFound -> ResponseEntity.ok().body(recordFound))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course) {

               return courseRepository.save(course);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update( @PathVariable long id, @RequestBody Course course){
       // verify if course exists by id
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setCategory(course.getCategory());
                    recordFound.setName(course.getName());
                    Course updated = courseRepository.save(recordFound);
                    return ResponseEntity.ok().body(updated);

                })
                .orElse(ResponseEntity.notFound().build());
        }



    }

