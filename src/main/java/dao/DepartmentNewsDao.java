package dao;

import models.Department;
import models.DepartmentNews;

import java.util.List;

public interface DepartmentNewsDao {

    //CREATE
    //Add
    void add(DepartmentNews departmentNews);

    //READ
    //Get all
    List<DepartmentNews> getAll();

    //Find by Id
    DepartmentNews findById(int id);
    List<DepartmentNews> findByUser(int userId);
    List<DepartmentNews> findByDepartment(int departmentId);

    //UPDATE
    void update(int id, String title, String content, int userId, int departmentId);

    //DELETE
    //Delete by Id
    void deleteById(int id, int userId, int departmentId);

    //Delete all
    void deleteAllDepartmentNews(int departmentId);

}
