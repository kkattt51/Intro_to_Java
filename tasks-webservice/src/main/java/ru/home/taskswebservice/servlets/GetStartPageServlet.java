package ru.home.taskswebservice.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.home.taskswebservice.model.Task;

@WebServlet("/GetStartPageServlet")
public class GetStartPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<Integer, Task> tasks;

    
	@SuppressWarnings("unchecked")
	@Override
    public void init() throws ServletException {

        final Object task = getServletContext().getAttribute("tasks");

        if (!(task instanceof ConcurrentHashMap)) {
            throw new IllegalStateException("Your repo does not initialize!");
        } else {
            this.tasks = (ConcurrentHashMap<Integer, Task>) task;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("tasks", tasks.values());
        req.getRequestDispatcher("/WEB-INF/view/startPage.jsp").forward(req, resp);
    }
}
