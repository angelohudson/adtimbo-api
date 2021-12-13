package br.com.adtimbo.api.controller.task.form;

import java.util.List;
import java.util.stream.Collectors;

import br.com.adtimbo.api.model.task.Evento;
import br.com.adtimbo.api.model.task.Task;
import lombok.Data;

@Data
public class EscalaForm {

	List<FuncaoForm> funcao;
	EventoForm task;

	public List<Task> converter() {
		return funcao.stream().map(f -> f.converter()).collect(Collectors.toList());
	}

	public Evento getEvento() {
		Evento evento = this.task.converter();
		evento.setTasks(this.converter());
		return evento;
	}

	public List<FuncaoForm> getFuncao() {
		return funcao;
	}

	public void setFuncao(List<FuncaoForm> funcao) {
		this.funcao = funcao;
	}

	public EventoForm getTask() {
		return task;
	}

	public void setTask(EventoForm task) {
		this.task = task;
	}

}

class FuncaoForm {
	private String funcaoTitulo;
	private Integer membroId;

	public Task converter() {
		Task task = new Task();
		task.setMembroId(this.membroId);
		task.setFuncaoEscala(this.funcaoTitulo);
		return task;
	}

	public Integer getMembroId() {
		return membroId;
	}

	public void setMembroId(Integer membroId) {
		this.membroId = membroId;
	}

	public String getFuncaoTitulo() {
		return funcaoTitulo;
	}

	public void setFuncaoTitulo(String funcaoTitulo) {
		this.funcaoTitulo = funcaoTitulo;
	}
}
