import com.google.gson.Gson;
import dao.*;
import exceptions.ApiException;
import models.*;
import org.sql2o.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class App {

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        staticFileLocation("/public");

        Sql2oDepartmentNewsDao departmentNewsDao;
        Sql2oDepartmentDao departmentDao;
        Sql2oGeneralNewsDao generalNewsDao;
        Sql2oUserDao userDao;

        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/organisational-api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"danny","password");
//        String connectionString = "jdbc:postgresql://localhost:5432/orgapi";
//        Sql2o sql2o = new Sql2o(connectionString, "danny","password");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
        conn = sql2o.open();

        //get: home
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //CREATE

        //post: create a new user
        post("/users/new", "application/json", (req, res)->{
            res.type("application/json");
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            return gson.toJson(user);
        });

        //post: create a new department
        post("/departments/new", "application/json", (req, res)->{
            res.type("application/json");
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            return gson.toJson(department);
        });

        //post: create a new general news article
        post("/generalnews/new", "application/json", (req, res)->{
            res.type("application/json");
            GeneralNews generalNews = gson.fromJson(req.body(), GeneralNews.class);
            generalNewsDao.add(generalNews);
            return gson.toJson(generalNews);
        });

        //post: create a new department news article
        post("/departmentnews/new", "application/json", (req, res)->{
            res.type("application/json");
            DepartmentNews departmentNews = gson.fromJson(req.body(), DepartmentNews.class);
            departmentNewsDao.add(departmentNews);
            return gson.toJson(departmentNews);
        });

        //READ

        //users
        //get: get all users
        get("/users", "application/json", (req, res)->{
            res.type("application/json");
            if(userDao.getAll().size() > 0) {
                return gson.toJson(userDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, there are no users currently listed in the database.\"}";
            }
        });

        //get: get user by Id
        get("/users/:id", "application/json", (req, res)->{
            int userId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            if (userDao.findById(userId) == null){
                throw new ApiException(404, String.format("No user of ID: %s exists in the database", userId));
            } else {
                return gson.toJson(userDao.findById(userId));
            }
        });

        //get: get all users in a department
        get("/departments/:id/users", "application/json", (req, res)->{
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            res.type("application/json");
            if(departmentToFind == null){
                throw new ApiException(404, String.format("I'm sorry, there is no department with the ID : %s", departmentId));
            } else if (userDao.findAllByDepartment(departmentId).size() <= 0) {
                return "{\"message\":\"I'm sorry, there are no users in this department.\"}";
            } else {
                return gson.toJson(userDao.findAllByDepartment(departmentId));
            }
        });

        //get: get a user by Id in a department
        get("/departments/:departmentId/users/:id", "application/json", (req, res)->{
            int userId = Integer.parseInt(req.params("id"));
            int departmentId = Integer.parseInt(req.params("departmentId"));
            Department departmentToFind = departmentDao.findById(departmentId);
            res.type("application/json");
            if(departmentToFind == null){
                throw new ApiException(404, String.format("I'm sorry, there is no department with the ID : %s", departmentId));
            } else if (userDao.findAllByDepartment(departmentId).size() <= 0) {
                return "{\"message\":\"I'm sorry, there are no users in this department.\"}";
            } else if(userDao.findById(userId) == null){
                throw new ApiException(404, String.format("No user with ID: %s exists", userId));
            } else if (userDao.findById(userId).getDepartmentId() != departmentId) {
                throw new ApiException(404, String.format("I'm sorry, there is no user with the ID : %s in this department.}", userId));
            } else {
                return gson.toJson(userDao.findById(userId));
            }
        });

        //departments
        //get: get all departments
        get("/departments", "application/json", (req, res)->{
            res.type("application/json");
            if(departmentDao.getAll().size() > 0) {
                return gson.toJson(departmentDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, there are no departments currently listed in the database.\"}";
            }
        });

        //get: get departments by Id
        get("/departments/:id", "application/json", (req, res)->{
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            if (departmentDao.findById(departmentId) == null){
                throw new ApiException(404, String.format("No department of ID: %s exists in the database", departmentId));
            } else {
                return gson.toJson(departmentDao.findById(departmentId));
            }
        });

        //department news
        //get: get all department news
        get("/departmentnews", "application/json", (req, res)->{
            res.type("application/json");
            if (departmentNewsDao.getAll().size() > 0){
                return gson.toJson(departmentNewsDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, there is no department news currently listed in the database.\"}";
            }
        });

        //get: get department news by ID
        get("/departmentnews/:id", "application/json", (req, res)->{
            res.type("application/json");
            int newsId = Integer.parseInt(req.params("id"));
            if (departmentNewsDao.findById(newsId) == null){
                throw new ApiException(404, String.format("No department news of ID: %s exists in the database", newsId));
            } else {
                return gson.toJson(departmentNewsDao.findById(newsId));
            }
        });

        //get: get department news by department
        get("/departments/:id/news", "application/json", (req, res)->{
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            if (departmentDao.findById(departmentId) == null){
                throw new ApiException(404, String.format("No department of ID: %s exists in the database", departmentId));
            } else if (departmentNewsDao.findByDepartment(departmentId).size() <= 0) {
                return "{\"message\":\"I'm sorry, there are is no news in this department.\"}";
            } else {
                return gson.toJson(departmentNewsDao.findByDepartment(departmentId));
            }
        });

        //general news
        //get: get all general news
        get("/generalnews", "application/json", (req, res)->{
            res.type("application/json");
            if (generalNewsDao.getAll().size() > 0){
                return gson.toJson(generalNewsDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, there is no general news currently listed in the database.\"}";
            }
        });

        //get: get general news by ID
        get("/generalnews/:id", "application/json", (req, res)->{
            res.type("application/json");
            int newsId = Integer.parseInt(req.params("id"));
            if (generalNewsDao.findById(newsId) == null){
                throw new ApiException(404, String.format("No general news of ID: %s exists in the database", newsId));
            } else {
                return gson.toJson(generalNewsDao.findById(newsId));
            }
        });

        //news
        //get: get all news
        get("/news", "application/json", (req, res)->{
            res.type("application/json");
            if (generalNewsDao.getAll().size() > 0 || departmentNewsDao.getAll().size() > 0){
                return gson.toJson(generalNewsDao.getAll()) + gson.toJson(departmentNewsDao.getAll());
            } else {
                return "{\"message\":\"I'm sorry, there is no news currently listed in the database.\"}";
            }
        });

        //get: get all news by a user
        get("/users/:id/news", "application/json", (req, res)->{
            int userId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            if ( userDao.findById(userId) == null){
                throw new ApiException(404, String.format("No user by ID: %s exists in the database", userId));
            } else if (generalNewsDao.findByUser(userId).size() != 0 && departmentNewsDao.findByUser(userId).size() != 0) {
                return gson.toJson(generalNewsDao.findByUser(userId)) + gson.toJson(departmentNewsDao.findByUser(userId));
            } else if (generalNewsDao.findByUser(userId).size() > 0){
                return gson.toJson(generalNewsDao.findById(userId));
            } else if(departmentNewsDao.findByUser(userId).size() > 0){
                return gson.toJson(departmentNewsDao.findByUser(userId));
            } else {
                return  String.format("No news by user ID: %s exists in the database", userId);
            }
        });


        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", exception.getStatusCode());
            jsonMap.put("errorMessage", exception.getMessage());
            res.type("application/json");
            res.status(exception.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

//        after((req, res) ->{
//            res.type("application/json");
//        });
    }
}
