package dao;

import models.Department;

import java.util.List;

public interface DepartmentDao {

    //CREATE
    //Add
    void add(Department department);

    //READ
    //Get all
    List<Department> getAll();

    //Find by Id
    Department findById(int id);

    //UPDATE
    void update(int id, String departmentName, String description);

    //DELETE
    //Delete by Id
    void deleteById(int id);

    //Delete all
    void deleteAll();

}
