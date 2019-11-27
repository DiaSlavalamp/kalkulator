package com.lebedev.site;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Expression, Integer> {
}
