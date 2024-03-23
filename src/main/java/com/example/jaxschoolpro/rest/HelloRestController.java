package com.example.jaxschoolpro.rest;

import com.example.jaxschoolpro.dto.TeacherInsertDTO;
import com.example.jaxschoolpro.dto.UserDTO;
import com.example.jaxschoolpro.model.Teacher;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;


import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Path("/hello")
public class HelloRestController {

    // create a validator factory instance and validator
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator =  factory.getValidator();

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


    // Data Binding - Validation

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveForm(MultivaluedMap<String ,String> params, @Context UriInfo uriInfo){
        UserDTO  dto = new UserDTO();
        // multivalueMap bind form values
        mapToDto(params,dto);

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        if(!violations.isEmpty()){
            List<String> errors = new ArrayList<>();
            for(ConstraintViolation<UserDTO> violation: violations){
                errors.add(violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path("1").build();
        return Response.status(Response.Status.CREATED).location(uri).build();
    }

    // mapper
    private void mapToDto(MultivaluedMap<String,String> params,UserDTO dto){
        dto.setUsername(params.getFirst("username"));
        dto.setPassword(params.getFirst("password"));
    }



}
