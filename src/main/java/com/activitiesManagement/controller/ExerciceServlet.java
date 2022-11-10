package com.activitiesManagement.controller;

import com.activitiesManagement.entity.Exercise;
import com.activitiesManagement.service.ExerciceService;
import com.activitiesManagement.service.implementation.ExerciceServiceImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "ExerciceServlet", urlPatterns ={ "/exercices", "/addExercice", "/deleteExercice"})
public class ExerciceServlet extends HttpServlet {
    ExerciceService exerciceService = new ExerciceServiceImp ( );
    Exercise exercice;
    List< Exercise> exerciceList;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath ();
        switch (path){
            case "/exercices":
                exerciceList = exerciceService.getAll();
                request.setAttribute ( "exerciceList", exerciceList );
                request.getRequestDispatcher("/exercice/exercices.jsp").forward(request, response);
                break;
            case  "/addExercice":
                request.getRequestDispatcher("/exercice/addExercice.jsp").forward(request, response);
                break;
            case  "/deleteExercice" :
                int id = Integer.parseInt ( request.getParameter ( "id" ));
                exerciceService.delete(id);
                exerciceList = exerciceService.getAll();
                request.setAttribute ( "exerciceList", exerciceList );
                request.getRequestDispatcher("/exercice/exercices.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath ();

        switch (path){
            case "/addExercice":
                String year = request.getParameter ( "year" );
                LocalDate dateDebut = LocalDate.parse (request.getParameter ( "dateDebut" ));
                LocalDate dateFin = LocalDate.parse ( request.getParameter ( "dateFin" ));
                Boolean status = Boolean.parseBoolean ( request.getParameter ( "status" ));
                String description = request.getParameter ( "description" );

                //System.out.println ("year: " + year + " date debut : " + dateDebut + "" +
                //        "date fin: " + dateFin + "" +
                //        "status : " + status );
                exercice = new Exercise ( year, dateDebut, dateFin, status, description );

                exerciceService.add(exercice);
                break;
        }
    }
}
