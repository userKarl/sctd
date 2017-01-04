package com.sc.td.business.base;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseDao<T> extends JpaRepository<T, String> {

}
