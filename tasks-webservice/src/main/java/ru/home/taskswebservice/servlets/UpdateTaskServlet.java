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
import ru.home.taskswebservice.util.TaskUtils;

@WebServlet("/UpdateTaskServlet")
public class UpdateTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<Integer, Task> tasks;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {

		final Object tasks = getServletContext().getAttribute("tasks");

		if (!(tasks instanceof ConcurrentHashMap)) {

			throw new IllegalStateException("You're repo does not initialize!");
		} else {

			this.tasks = (ConcurrentHashMap<Integer, Task>) tasks;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		final String id = req.getParameter("id");
		final String title = req.getParameter("title");
		final String description = req.getParameter("description");

		final Task task = tasks.get(Integer.parseInt(id));
		task.setTitle(title);
		task.setDescription(description);

		resp.sendRedirect(req.getContextPath() + "/");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		final String id = req.getParameter("id");

		if (TaskUtils.idIsInvalid(id, tasks)) {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}

		final Task task = tasks.get(Integer.parseInt(id));
		req.setAttribute("task", task);

		req.getRequestDispatcher("/WEB-INF/view/update.jsp").forward(req, resp);
	}

}
