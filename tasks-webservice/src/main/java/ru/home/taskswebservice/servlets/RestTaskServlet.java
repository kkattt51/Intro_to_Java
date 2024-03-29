package ru.home.taskswebservice.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ru.home.taskswebservice.model.Task;
import ru.home.taskswebservice.util.TaskUtils;
@Slf4j
@WebServlet("/tasks/*")
public class RestTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<Integer, Task> tasks;
	private AtomicInteger idCounter;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {

		final Object tasks = getServletContext().getAttribute("tasks");

		if (!(tasks instanceof ConcurrentHashMap)) {
			throw new IllegalStateException("You're repo does not initialize!");
		} else {

			this.tasks = (ConcurrentHashMap<Integer, Task>) tasks;
		}

		idCounter = (AtomicInteger) getServletContext().getAttribute("idCounter");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
		String pathInfo = req.getPathInfo();
		String[] parts = pathInfo.split("/");
		String param1 = parts[1];

		int taskId = Integer.parseInt(param1);

		req.setCharacterEncoding("UTF-8");

		final Task task = tasks.get(taskId);
		final String jsonTask = new ObjectMapper().writeValueAsString(task);

		resp.setContentType("application/json; charset=UTF-8");
		resp.setStatus(200);
		PrintWriter out = resp.getWriter();
		out.write(jsonTask);
	}

	// CREATE
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());

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

		resp.setStatus(201);

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
		String pathInfo = req.getPathInfo();
		String[] parts = pathInfo.split("/");
		String param1 = parts[1];

		tasks.remove(Integer.parseInt(param1));

		resp.setStatus(202);

	}

	// UPDATE
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
