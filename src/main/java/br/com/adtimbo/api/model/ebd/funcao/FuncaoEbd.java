package br.com.adtimbo.api.model.ebd.funcao;

public enum FuncaoEbd {
	LIDERANCA(1), ALUNO(2), PROFESSOR(3), SECRETARIO(4);

	private Integer cod;

	FuncaoEbd(Integer cod) {
		this.cod = cod;
	}

	public Integer getCod() {
		return this.cod;
	}
}
