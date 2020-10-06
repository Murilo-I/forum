package br.com.alura.forum.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.models.Topico;
import br.com.alura.forum.repository.TopicoRepository;

public class AtualizaForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Topico update(Long id, TopicoRepository repository) {
		Topico t = repository.getOne(id);
		t.setTitulo(this.titulo);
		t.setMensagem(this.mensagem);
		
		return t;
	}
}
