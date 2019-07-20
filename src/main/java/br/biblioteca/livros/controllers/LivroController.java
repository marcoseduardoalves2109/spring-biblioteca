package br.biblioteca.livros.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.entidades.Autor;
import br.biblioteca.livros.entidades.Livro;
import br.biblioteca.livros.services.AutorService;
import br.biblioteca.livros.services.LivroService;

@Controller
@RequestMapping("/livros")
public class LivroController {

	private static final String TEMPLATE = "/livros";

	@Autowired
	private LivroService serviceLivro;

	@Autowired
	private AutorService serviceAutor;

	@GetMapping("/list")
	public ModelAndView index() {

		List<Livro> livros = this.serviceLivro.listaLivros();

		for (Livro livro : livros) {
			System.out.println(livro.getNome());
		}

		return new ModelAndView(TEMPLATE + "/index", "listaLivros", livros);
	}

	@GetMapping("/novo")
	public ModelAndView create(@ModelAttribute Livro livro) {
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/create");
		Iterable<Autor> autores = this.serviceAutor.listaAutores();
		modelAndView.addObject("listaAutores", autores);
		return modelAndView;

	}

	@PostMapping("/gravar")
	public ModelAndView storage(@ModelAttribute("livro") @Valid Livro livro, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			//return new ModelAndView("livros/create");
			ModelAndView modelAndView = new ModelAndView("livros/create");
			Iterable<Autor> autores = this.serviceAutor.listaAutores();
			modelAndView.addObject("listaAutores", autores);
			return modelAndView;
		}
		this.serviceLivro.salvaLivro(livro);

		return new ModelAndView("redirect:" + TEMPLATE + "/list");
	}

	@GetMapping("/editar/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		Livro livro = this.serviceLivro.buscarLivro(id);
		List<Autor> autores = this.serviceAutor.listaAutores();
		ModelAndView modelAndView = new ModelAndView(TEMPLATE + "/edit");
		modelAndView.addObject("listaAutores", autores);
		modelAndView.addObject("livro", livro);
		return modelAndView;
	}

	@GetMapping("/excluir/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		serviceLivro.apagarLivro(id);
		return new ModelAndView("redirect:" + TEMPLATE + "/list");
		
	}
	@GetMapping("alterar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Livro livro = serviceLivro.buscarLivro(id);
		List<Autor> autores = serviceAutor.listaAutores();
		ModelAndView modelAndView = new ModelAndView("livros/form");
		modelAndView.addObject("autores", autores);
		modelAndView.addObject("livro", livro);
		return modelAndView;
	}


}
