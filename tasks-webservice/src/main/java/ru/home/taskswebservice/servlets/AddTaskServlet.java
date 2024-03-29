package ru.home.taskswebservice.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.home.taskswebservice.model.Task;
import ru.home.taskswebservice.util.TaskUtils;

@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<Integer, Task> tasks;

	private AtomicInteger idCounter;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		final Object tasks = getServletContext().getAttribute("tasks");

		if (!(tasks instanceof ConcurrentHashMap)) {
			throw new IllegalStateException("Your repo does not initialize!");
		} else {
			this.tasks = (ConcurrentHashMap<Integer, Task>) tasks;
		}

		idCounter = (AtomicInteger) getServletContext().getAttribute("idCounter");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		if (TaskUtils.requestIsValid(req)) {

			final String title = req.getParameter("title");
			final String description = req.getParameter("description");

			final Task task = new Task();
			final int id = this.idCounter.getAndIncrement();
			task.setId(id);
			task.setTitle(title);
			task.setDescription(description);

			tasks.put(id, task);

		}

		resp.sendRedirect(req.getContextPath() + "/");
	}

}
