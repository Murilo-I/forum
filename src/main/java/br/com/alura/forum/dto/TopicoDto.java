package br.com.alura.forum.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.alura.forum.models.Topico;

public class TopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriaçao;

	public TopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriaçao = topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataCriaçao() {
		return dataCriaçao;
	}
	
	public static Page<TopicoDto> convert(Page<Topico> topicos) {
		return topicos.map(TopicoDto::new);
	}
}
