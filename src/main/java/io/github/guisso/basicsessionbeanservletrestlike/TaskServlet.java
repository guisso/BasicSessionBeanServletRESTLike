/*
 * The MIT License
 *
 * Copyright 2025 Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.guisso.basicsessionbeanservletrestlike;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.io.PrintWriter;
import java.util.Set;

/**
 * A simple servlet testing HTTP verbs with persistence operations
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.1
 * @since 0.1, Sep 19, 2025
 */
@WebServlet(name = "TaskServlet", urlPatterns = {"/tasks"})
public class TaskServlet extends HttpServlet {

    @Inject
    Validator validator;

    @EJB
    TaskServiceLocal taskService;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simple test
//        response.setContentType("text/plain");
//        response.getWriter().write("POST OK");
//
        String description = request.getParameter("description");
        Task task = new Task(description);

        try {
            // Validation of the entity by yours annotations
            Set<ConstraintViolation<Task>> violations = validator.validate(task);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            taskService.save(task);
            generateJsonOutput(response, 201, task);

        } catch (ConstraintViolationException cvex) {
            generateJsonError(response, 422, cvex.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simple test
//        response.setContentType("text/plain");
//        response.getWriter().write("GET OK");
//        
        Long id = Long.valueOf(request.getParameter("id"));
        Task task = taskService.findById(id);

        if (task == null) {
            generateJsonError(response, 404, "ID not found");

        } else {
            generateJsonOutput(response, 200, task);
        }
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simple test
//        response.setContentType("text/plain");
//        response.getWriter().write("PUT OK");
//
        Long id = Long.valueOf(request.getParameter("id"));
        Task task = taskService.findById(id);

        if (task == null) {
            generateJsonError(response, 404, "ID not found");

        } else {
            generateJsonOutput(response, 200, task);

        }
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simple test
        response.setContentType("text/plain");
        response.getWriter().write("DELETE OK");
    }

    /**
     * Generate a default JSON response
     *
     * @param response Servlet response
     * @param errorCode HTTP code
     * @param task Task to be represented
     * @throws IOException If an I/O error occurs
     */
    private void generateJsonOutput(
            HttpServletResponse response,
            int code, Task task)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();

            if (task != null) {
                jsonb.toJson(task, out);
            }

        } catch (IOException ioex) {
            generateJsonError(response, code, ioex.getMessage());
        }
    }

    /**
     * Generate a default JSON Error response
     *
     * @param response Servlet response
     * @param errorCode Error code
     * @param errorMessage Error message
     * @throws IOException If an I/O error occurs
     */
    private void generateJsonError(
            HttpServletResponse response,
            int errorCode, String errorMessage)
            throws IOException {

        response.setStatus(errorCode);
        response.setContentType("application/json; charset=UTF-8");

        String jsonError = String.format("""
            {
              "code": %d,
              "error": "%s"
            }
            """, errorCode, errorMessage);
        response.getWriter().write(jsonError);
    }
}
