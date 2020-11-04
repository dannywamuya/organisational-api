package dao;

import models.User;

import java.util.List;

public interface UserDao {

    //CREATE
    //Add
    void add(User user);

    //READ
    //Get all
    List<User> getAll();

    //Find by Id
    User findById(int id);

    //Find by Department
    User findByDepartment(int departmentId);
    List <User> findAllByDepartment(int departmentId);

    //UPDATE
    void update(int id, String fullName, String position,  String role, int departmentId);

    //DELETE
    //Delete by Id
    void deleteById(int id);

    //Delete all
    void deleteAll();
    void deleteAllByDepartment(int departmentId);

}
