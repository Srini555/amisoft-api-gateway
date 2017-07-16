package com.amisoft.dao;

import com.amisoft.entity.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by amitdatta on 16/07/17.
 */
public interface EmployeeDao  extends CrudRepository<Employee,Long>{

    Employee findEmployeeByName(String name);
}
