package com.mjc.school.service;

import java.util.List;

public interface NextGenService<T, R, K> {

    List<R> readAll();

    R readById(K id);

    R create(R createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
}
