package br.biblioteca.livros.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Role {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 2, max = 150)
	private String name;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@OneToMany(mappedBy = "role")
	private List<User> users = new ArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	

	
}
