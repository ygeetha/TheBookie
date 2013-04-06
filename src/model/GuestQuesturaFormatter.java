/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
package model;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.String;
import java.lang.Integer;
import javax.xml.bind.annotation.XmlRootElement;



import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class GuestQuesturaFormatter implements Serializable{
	@Field
	private Integer id;
	
	
	
	/*
	 * Dati per la Questura
	 */
	private Integer tipoAllogiato; 	// values in range[16-20] 
	private Date dataArrivo;	 	// gg/mm/aaaa
	private String cognome; 		// 50 chars
	private String nome; 			// 30 chars
	private String sesso;			// 1  chars (M,F)
	private Date dataDiNascita;		// gg/mm/aaaa
	private String comuneDiNascita;   // 9  chars
	private String provinciaDiNascita;// 2 chars sigla
	private String statoDiNascita; 	  // 9 chars
	private String cittadinanza; 	  // 9 chars
	private String comuneResidenza;   // 9 chars
	private String provinciaResidenza; //2 chars
	private String statoResidenza; 	   //9 chars
	private String indirizzo;		   // 50 chars
	private String tipoDocumento;	   // 5 chars
	private String numeroDocumento;	   // 20 chars
	private String luogoRilascioDocumento; // 9 chars 
	
	/*
	 * Dati aggiuntivi per la  RegioneSardegna
	 */
	private Date dataDiPartenza; 		// 10 chars 
	private String tipoTurismo;			// 30 chars
	private String mezzoDiTrasporto; 	// 30 chars
	private int camereOccupate;		 	// 3 chars
	private int camereDisponibili;		// 3 chars
	private int lettiDisponibili;		// 4 chars
	private int tassaSoggiorno;			// 1 char (1=yes, 0=no)
	private String codiceIdPosizione;	// 10 char
	private int modalita;				// 1 char (1=Nuovo, 2=Variazione, 2=Eliminazione)
	
	
	
	
	//return a string wich length is fixedSize and and if s < fixedSize is filled with blank spaces
	private String fillString(String s, int fixedSize){
		String result = "                                                                                                                        ";
		result = s.concat(result);
		return result.substring(0, fixedSize);
		
	}
	
	/**
	 * get the resulting row (236 chars)
	 * you need to append a (CR+LF) characters at the end of the row
	 */
	public String getRowQuestura(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String s = "";
				
		s = s.concat(Integer.toString(tipoAllogiato));
		s = s.concat(formatter.format(dataArrivo));
		s = s.concat(cognome + nome + sesso);
		s = s.concat(formatter.format(dataDiNascita));
		s = s.concat(comuneDiNascita + provinciaDiNascita + statoDiNascita);
		s = s.concat(cittadinanza + comuneResidenza + provinciaResidenza + statoResidenza);
		s = s.concat(indirizzo + tipoDocumento + numeroDocumento + luogoRilascioDocumento);
		
		return s;
	}
	
	public String getRowRegione(){
		String questura = this.getRowQuestura();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String regione = "";
		regione = regione.concat(formatter.format(dataDiPartenza));
		regione = regione.concat(tipoTurismo + mezzoDiTrasporto + Integer.toString(camereOccupate) + Integer.toString(camereDisponibili) +Integer.toString(lettiDisponibili));
		regione = regione.concat(Integer.toString(tassaSoggiorno) + codiceIdPosizione +Integer.toString(modalita));
		return questura + regione;
	}
	
	public void setDataFromHoused(Housed housed){
		Guest guest = null;
		guest = housed.getGuest();
		this.setTipoAllogiato(14);
		this.setDataArrivo(housed.getCheckInDate());
		this.setCognome(guest.getLastName());
		this.setNome(guest.getFirstName());
		this.setSesso(guest.getGender());
		this.setDataDiNascita(guest.getBirthDate());
		this.setComuneDiNascita("Santulussurgiu");
		this.setProvinciaDiNascita("CAGLIARI");
		this.setStatoDiNascita("Italia");
		this.setCittadinanza("Italiana");
		this.setComuneResidenza("Selargius");
		this.setProvinciaResidenza("Cagliari");
		this.setStatoResidenza("Italia");
		this.setIndirizzo("Via delle fresche frasche, n1");
		this.setTipoDocumento("Patente");
		this.setNumeroDocumento("CC123456lkjdf");
		this.setLuogoRilascioDocumento("Villanovaforru");
		this.setDataDiPartenza(housed.getCheckOutDate());
		this.setTipoTurismo("scout");
		this.setMezzoDiTrasporto("auto");
		this.setCamereOccupate(2);
		this.setCamereDisponibili(7);
		this.setLettiDisponibili(14);
		this.setTassaSoggiorno(3);
		this.setCodiceIdPosizione("2gf");
		this.setModalita(1);
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GuestQuesturaFormatter other = (GuestQuesturaFormatter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
				this.id = id;
	}
	
	public Integer getTipoAllogiato(){
		return tipoAllogiato;
	}
	public void setTipoAllogiato(Integer tipoAllogiato){
		this.tipoAllogiato = tipoAllogiato;
	}
	
	public Date getDataArrivo(){
		return dataArrivo;	 	
	}
	public void setDataArrivo(Date dataArrivo){
		this.dataArrivo = dataArrivo;
	}
	
	public String getCognome(){
		return cognome;
	}
	public void setCognome(String cognome){
		this.cognome = fillString(cognome, 50);
	}
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome = fillString(nome, 30);
	}
	public String getSesso(){
		return sesso;
	}
	public void setSesso(String sesso){
		this.sesso = fillString(sesso, 1);
	}
	public Date getDataDiNascita(){
		return dataDiNascita;
	}
	public void setDataDiNascita(Date dataDiNascita){
		this.dataDiNascita = dataDiNascita;
	}
	public String getComuneDiNascita(){
		return comuneDiNascita;
	}
	public void setComuneDiNascita(String comuneDiNascita){
		this.comuneDiNascita = fillString(comuneDiNascita, 9);
	}
	public String getProvinciaDiNascita(){
		return provinciaDiNascita;
	}
	public void setProvinciaDiNascita(String provinciaDiNascita){
		this.provinciaDiNascita = fillString(provinciaDiNascita,2);
	}
	public String getStatoDiNascita(){
		return statoDiNascita;
	}
	public void setStatoDiNascita(String statoDiNascita){
		this.statoDiNascita = fillString(statoDiNascita,9);
	}
	public String getCittadinanza(){
		return cittadinanza;
	}
	public void setCittadinanza(String cittadinanza){
		this.cittadinanza = fillString(cittadinanza,9);
	}
	public String getComuneResidenza(){
		return comuneResidenza;
	}
	public void setComuneResidenza(String comuneResidenza){
		this.comuneResidenza = fillString(comuneResidenza,9);
	}
	public String getProvinciaResidenza(){
		return provinciaResidenza;
	}
	public void setProvinciaResidenza(String provinciaResidenza){
		this.provinciaResidenza = fillString(provinciaResidenza,2);
	}
	public String getStatoResidenza(){
		return statoResidenza;
	}
	public void setStatoResidenza(String statoResidenza){
		this.statoResidenza = fillString(statoResidenza,9);
	}
	public String getIndirizzo(){
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo){
		this.indirizzo = fillString(indirizzo,50);
	}
	public String getTipoDocumento(){
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento){
		this.tipoDocumento = fillString(tipoDocumento,5);
	}
	public String getNumeroDocumento(){
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento){
		this.numeroDocumento = fillString(numeroDocumento,20);
	}
	public String getLuogoRilascioDocumento(){
		return luogoRilascioDocumento;
	}
	public void setLuogoRilascioDocumento(String luogoRilascioDocumento){
		this.luogoRilascioDocumento = fillString(luogoRilascioDocumento,9);
	}

	public Date getDataDiPartenza() {
		return dataDiPartenza;
	}

	public void setDataDiPartenza(Date dataDiPartenza) {
		this.dataDiPartenza = dataDiPartenza;
	}

	public String getTipoTurismo() {
		return tipoTurismo;
	}

	public void setTipoTurismo(String tipoTurismo) {
		this.tipoTurismo = fillString(tipoTurismo,30);
	}

	public String getMezzoDiTrasporto() {
		return mezzoDiTrasporto;
	}

	public void setMezzoDiTrasporto(String mezzoDiTrasporto) {
		this.mezzoDiTrasporto = fillString(mezzoDiTrasporto,30);
	}

	public int getCamereOccupate() {
		return camereOccupate;
	}

	public void setCamereOccupate(int camereOccupate) {
		this.camereOccupate = camereOccupate;
	}

	public int getCamereDisponibili() {
		return camereDisponibili;
	}

	public void setCamereDisponibili(int camereDisponibili) {
		this.camereDisponibili = camereDisponibili;
	}

	public int getLettiDisponibili() {
		return lettiDisponibili;
	}

	public void setLettiDisponibili(int lettiDisponibili) {
		this.lettiDisponibili = lettiDisponibili;
	}

	public int getTassaSoggiorno() {
		return tassaSoggiorno;
	}

	public void setTassaSoggiorno(int tassaSoggiorno) {
		this.tassaSoggiorno = tassaSoggiorno;
	}

	public String getCodiceIdPosizione() {
		return codiceIdPosizione;
	}

	public void setCodiceIdPosizione(String codiceIdPosizione) {
		this.codiceIdPosizione = fillString(codiceIdPosizione,10);
	}

	public int getModalita() {
		return modalita;
	}

	public void setModalita(int modalita) {
		this.modalita = modalita;
	}
	
	
}