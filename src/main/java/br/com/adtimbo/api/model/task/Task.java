package br.com.adtimbo.api.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.adtimbo.api.model.core.Membro;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_task")
public class Task {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "justificativa")
	private String justificativa;

	@Column(name = "funcao_escala")
	private String funcaoEscala;

	@ManyToOne
	@JoinColumn(name = "membro_id", referencedColumnName = "id")
	private Membro membro = new Membro();

	@ManyToOne
	@JoinColumn(name = "evento_id", referencedColumnName = "id")
	private Evento evento = new Evento();

	@Transient
	private Integer funcaoId;

	private TaskStatus status;

	public void setMembroId(Integer membroId) {
		this.membro.setId(membroId);
	}

	public Integer getMembroId() {
		return this.membro.getId();
	}

	public String getSubtitulo() {
		return this.funcaoEscala == null ? evento.getSubtitulo()
				: evento.getSubtitulo() + ". Escalado(a) como " + this.getFuncaoEscala();
	}
}
