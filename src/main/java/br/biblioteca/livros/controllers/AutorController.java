package br.biblioteca.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.entidades.Autor;
import br.biblioteca.livros.services.AutorService;

@Controller
@RequestMapping("/autores")
public class AutorController {

	private static final String TEMPLATE = "autores";

	@Autowired
	private AutorService serviceAutor;

	@GetMapping("/list")
	public ModelAndView index() {

		List<Autor> autores = this.serviceAutor.listaAutores();

		return new ModelAndView(TEMPLATE + "/index", "listaAutores", autores);
	}

	@GetMapping("/novo")
	public ModelAndView create(@ModelAttribute Autor autor) {
		return new ModelAndView(TEMPLATE + "/form");
	}

	@PostMapping("/gravar")
	public ModelAndView storage(Autor autor) {
		this.serviceAutor.salvaAutor(autor);
		System.out.println("Autor gravado: " + autor.getNome());
		return new ModelAndView("redirect:/autores/list");
	}

	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Autor autor = this.serviceAutor.buscarAutor(id);
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/form");
		modelAndView.addObject("autor", autor);
		return modelAndView;
	}

	@GetMapping("/excluir/{id}")
	public ModelAndView destroy(@PathVariable("id") Long id) {
		serviceAutor.apagaAutor(id);
		return new ModelAndView("redirect:/" + TEMPLATE + "/list");
	}

}
