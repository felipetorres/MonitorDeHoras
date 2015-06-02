package com.example.monitordehoras.model;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

public class DiaDeTrabalho {

    private Long id;
	private long entrada;
	private long saida;
	private String almoco;
	
	public DiaDeTrabalho(long entrada, long saida, String almoco) {
		this.entrada = entrada;
		this.saida = saida;
		this.almoco = almoco;
	}

	public long getEntrada() {
		return entrada;
	}
	
	public long getSaida() {
		return saida;
	}
	
	public String getAlmoco() {
		return almoco;
	}

    public Long getId() {
        return id;
    }

    public String formataData() {
		return formata(this.entrada, "dd/MM/yyyy");
	}
	
	public String formataEntrada() {
		return formataHoraDe(entrada);
	}
	
	public String formataSaida() {
		return formataHoraDe(saida);
	}
	
	private String formata(long campo, String pattern) {
		SimpleDateFormat hourFormat = new SimpleDateFormat(pattern);
		return hourFormat.format(new DateTime(campo).toDate());
	}
	
	private String formataHoraDe(long hora) {
		return formata(hora, "HH:mm:ss");
	}

    public void setId(long id) {
        this.id = id;
    }
}
