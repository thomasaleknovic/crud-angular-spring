package com.thomasaleknovic.crudspring.service;

import com.thomasaleknovic.crudspring.model.Course;
import com.thomasaleknovic.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;
import java.util.Optional;

@Validated
@Service
public class CourseService {
    private final CourseRepository courseRepository;


    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(@PathVariable @NotNull @Positive Long id) {

        return courseRepository.findById(id);

    }

    public Course create(Course course) {

        return courseRepository.save(course);
    }

    public Optional<Course> update(@PathVariable @NotNull @Positive Long id, @Valid Course course){

        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setCategory(course.getCategory());
                    recordFound.setName(course.getName());
                    return courseRepository.save(recordFound);
                });

    }

    public boolean delete(@PathVariable @NotNull @Positive Long id){
        return courseRepository.findById(id)
                .map(recordFound -> {
                    courseRepository.deleteById(id);
                    return true;
                }).orElse(false);

    }
}
