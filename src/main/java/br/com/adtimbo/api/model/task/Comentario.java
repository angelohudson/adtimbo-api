package br.com.adtimbo.api.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.adtimbo.api.model.core.Membro;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_comentario")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(name = "comentario")
	private String comentario;

	@ManyToOne
	@JoinColumn(name = "evento_id", referencedColumnName = "id")
	private Evento evento;

	@ManyToOne
	@JoinColumn(name = "membro_id", referencedColumnName = "id")
	private Membro membro;

	public Comentario() {
	}

	public Comentario(String comentario, Evento evento, Membro membro) {
		super();
		this.comentario = comentario;
		this.evento = evento;
		this.membro = membro;
	}

}