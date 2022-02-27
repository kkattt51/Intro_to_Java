package ru.home.taskswebservice.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Задачи
 *
 * @author Iryna kislitskaya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "id", "title", "description" })
public class Task {
	private int id;
	private String title;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Task(int id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Task() {}
}
