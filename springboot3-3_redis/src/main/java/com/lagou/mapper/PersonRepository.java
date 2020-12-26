package com.lagou.mapper;

import com.lagou.pojo.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person,String> {

    List<Person> findByAddress_City(String 北京);

}
