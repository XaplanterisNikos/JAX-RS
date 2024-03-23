package com.example.jaxschoolpro.rest;

import com.example.jaxschoolpro.dto.TeacherInsertDTO;
import com.example.jaxschoolpro.dto.TeacherReadOnlyDTO;
import com.example.jaxschoolpro.model.Teacher;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;


import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Path("/teachers")
public class TeacherRestController {

    private final Validator validator;

    public TeacherRestController(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachers(){

        // Assume we call service layer and get back
        // a teacher's list
        List<Teacher> teachers = Arrays.asList(
                new Teacher(1L,"A1SSN1","Nikos","Xaplanteris"),
                new Teacher(2L,"B1SSN2","Giannis","Antoniadis"),
                new Teacher(3L,"C1SSN3","Petros","Papadopoulos"),
                new Teacher(4L,"D1SSN4","Loukas","Papas")

        );

        // status NOT Found
        if(teachers.size()==0){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<TeacherReadOnlyDTO> teachersDto = new ArrayList<>();
        for(Teacher teacher : teachers){
            teachersDto.add(mapFrom(teacher));
        }
        // status OK
        return Response.status(Response.Status.OK).entity(teachersDto).build();
    }



    @GET
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("teacherId") Long teacherId){

        // Assume tha we call service.getTeacherById(teacherId)
        // and we get back a Teacher with id == {teacherId} or null
        // for instance:
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setSsn("A1");
        teacher.setFirstname("Nik");
        teacher.setLastname("Xaplanteris");

        if(teacher == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request").build();
        }

        TeacherReadOnlyDTO dto = mapFrom(teacher);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    // mapper
    private TeacherReadOnlyDTO mapFrom(Teacher teacher){
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getSsn(), teacher.getFirstname(), teacher.getLastname());
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(MultivaluedMap<String, String>formParams,@Context UriInfo uriInfo){
        TeacherInsertDTO teacherInsertDTO = mapFromMultiV(formParams);

        Set<ConstraintViolation<TeacherInsertDTO>> violations = validator.validate(teacherInsertDTO);
        if(!violations.isEmpty()){
            List<String>errors = new ArrayList<>();
            for(ConstraintViolation<TeacherInsertDTO> violation : violations){
                errors.add(violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        // insert Teacher
        Teacher teacher = new Teacher(1L,teacherInsertDTO.getSsn(),teacherInsertDTO.getFirstname(),teacherInsertDTO.getLastname());
        TeacherReadOnlyDTO dto = mapFromTeacher(teacher);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(teacher.getId().toString()).build();
        return Response.status(Response.Status.CREATED).location(uri).entity(dto).build();
    }


    // Mappers
    private TeacherInsertDTO mapFromMultiV(MultivaluedMap<String,String> params){
        TeacherInsertDTO dto = new TeacherInsertDTO();
        dto.setSsn(params.getFirst("ssn"));
        dto.setFirstname(params.getFirst("firstname"));
        dto.setLastname(params.getFirst("lastname"));
        return dto;
    }

    private TeacherReadOnlyDTO mapFromTeacher(Teacher teacher){
        TeacherReadOnlyDTO dto = new TeacherReadOnlyDTO();
        dto.setId(teacher.getId());
        dto.setSsn(teacher.getSsn());
        dto.setFirstname(teacher.getFirstname());
        dto.setLastname(teacher.getLastname());
        return dto;
    }

}
