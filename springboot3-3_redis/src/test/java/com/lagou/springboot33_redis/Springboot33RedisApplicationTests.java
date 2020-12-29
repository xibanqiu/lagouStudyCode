package com.lagou.springboot33_redis;

import com.lagou.mapper.PersonRepository;
import com.lagou.pojo.Address;
import com.lagou.pojo.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
 public class Springboot33RedisApplicationTests {

    @Autowired
    private PersonRepository repository;

    @Test
    public void savePerson() {
        Person person = new Person();
        person.setFirstname("张");
        person.setLastname("三");
        Address address = new Address();
        address.setCity("北京");
        address.setCountry("中国");
        person.setAddress(address);
        // 向Redis数据库添加数据
     Person save = repository.save(person);
    }



       @Test
        public void selectPerson() {
           List<Person> list = (List<Person>) repository.findByAddress_City("北京");

           System.out.println(list.size());
           for (Person person : list) {
              System.out.println(person);
          }


    }

}
