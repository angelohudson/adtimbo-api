package br.com.adtimbo.api.model.task;

public enum TaskStatus {
	/*
	 * Foi confirmado pelo membro que o mesmo irá cumprir ou já cumpriu a tarefa
	 */
	CONFIRMADO,
	/*
	 * O membro visualizou e afirmou que não irá fazer parte da tarefa
	 */
	DELETADO,
	/*
	 * A tarefa fiu recem criada
	 */
	DEFAULT,
	/*
	 * A tarefa foi expirada na sua vida útil
	 */
	CONCLUIDA;
}
