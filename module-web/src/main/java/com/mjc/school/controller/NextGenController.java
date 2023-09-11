package com.mjc.school.controller;

import java.util.List;

public interface NextGenController<T, R, K> {

    List<R> readAll();

    R readById(K id);

    R create(R createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
}
