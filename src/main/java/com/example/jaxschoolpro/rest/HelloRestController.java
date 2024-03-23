package com.example.jaxschoolpro.rest;

import com.example.jaxschoolpro.dto.TeacherInsertDTO;
import com.example.jaxschoolpro.model.Teacher;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/hello")
public class HelloRestController {

    @Path("/say-hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello(){
        return "Hello Coding Factory";
    }

    @GET
    @Path("/get-hello-res")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloRes(){
       // return Response.ok("Hello Xaplannteris Nick").build();

        return Response
                .status(Response.Status.OK)
                .entity("Hello Xaplanteris Nick")
                .build();
    }

    @GET
    @Path("/get-teacher")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(){
        Teacher teacher = new Teacher(1l,"SSN123","Xaplanteris","Nikos");
        TeacherInsertDTO teacherDTO = teacher.toDTO();
        return Response.status(Response.Status.OK).entity(teacherDTO).build();
    }

    @GET
    @Path("/teachers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachers(@QueryParam("lastname") String lastname){

        // dao.getTeachersByLastname(lastname);
        // Asume it returns a list with results

        List<Teacher> teachers = Arrays.asList(
                new Teacher(1l,"SSN1","Irini","Xaplanteri"),
                new Teacher(2L,"SSN2","Eui","Xaplanteri")
        );

        List<TeacherInsertDTO> teachersDTO = new ArrayList<>();
        for (Teacher teacher : teachers){
            if(teacher.getLastname().equals(lastname) ){
                teachersDTO.add(teacher.toDTO());
            }
        }

        // Asume it returns an empty list
        if(teachersDTO.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("Teachers Not Found").build();
        }

        return Response.status(Response.Status.OK).entity(teachersDTO).build();
    }



}
