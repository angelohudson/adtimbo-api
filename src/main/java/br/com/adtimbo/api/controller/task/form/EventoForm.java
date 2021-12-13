package br.com.adtimbo.api.controller.task.form;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import br.com.adtimbo.api.model.task.Evento;
import br.com.adtimbo.api.model.task.TipoEvento;
import lombok.Data;

@Data
public class EventoForm {

	private String titulo;
	private String subtitulo;
	private String descricao;
	private String dataInicio;
	private String dataFim;
	private String tipo;

	public Evento converter() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss").withZone(ZoneOffset.UTC);
		Evento evento = new Evento();
		evento.setTitulo(this.titulo);
		evento.setSubtitulo(this.subtitulo);
		evento.setDescricao(this.descricao);
		evento.setDataInicio(LocalDateTime.parse(this.dataInicio, formatter));
		evento.setDataFim(LocalDateTime.parse(this.dataFim, formatter));
		evento.setDataCriacao(LocalDateTime.now());
		evento.setTipo(TipoEvento.valueOf(this.tipo));
		return evento;
	}
}
