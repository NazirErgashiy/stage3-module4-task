package com.mjc.school.controller;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface NextGenController<T, R, K> {

    List<R> getAll(Integer pageNumber, Integer pageSize, String sortBy);

    R getById(K id);

    R create(R createRequest);

    R update(K id, T updateRequest);

    void deleteById(K id);
}
