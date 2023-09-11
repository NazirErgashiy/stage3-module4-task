package com.mjc.school.controller;

public interface ExtendedController<T, R, K> {

    R createExtended(K id, R createRequest);

    R updateExtended(K id,T updateRequest);
}
