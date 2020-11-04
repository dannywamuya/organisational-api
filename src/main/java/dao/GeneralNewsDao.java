package dao;

import models.DepartmentNews;
import models.GeneralNews;

import java.util.List;

public interface GeneralNewsDao {

    //CREATE
    //Add
    void add(GeneralNews generalNews);

    //READ
    //Get all
    List<GeneralNews> getAll();

    //Find by Id
    GeneralNews findById(int id);
    List<GeneralNews> findByUser(int userId);

    //UPDATE
    void update(int id, String title, String content, int userId);

    //DELETE
    //Delete by Id
    void deleteById(int id, int userId);

    //Delete all
    void deleteAll();

}
