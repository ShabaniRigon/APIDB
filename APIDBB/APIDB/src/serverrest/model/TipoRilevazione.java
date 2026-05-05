package serverrest.model;

/**
 * MODEL / POJO
 * Rappresenta una riga della tabella TipoRilevazione.
 * Qui ci sono solo dati, getter e setter: niente SQL.
 */
public class TipoRilevazione {
    private Integer tip_id;
    private String tip_tipologia;
    private String tip_codice;
    private Integer tip_sen_id;
    private String tip_unita;
    private Boolean tip_futuro;

    // Costruttore vuoto: serve a Gson quando legge il JSON.
    public TipoRilevazione() {
    }


    public Integer getTip_id() {
        return tip_id;
    }

    public void setTip_id(Integer tip_id) {
        this.tip_id = tip_id;
    }
    public String getTip_tipologia() {
        return tip_tipologia;
    }

    public void setTip_tipologia(String tip_tipologia) {
        this.tip_tipologia = tip_tipologia;
    }
    public String getTip_codice() {
        return tip_codice;
    }

    public void setTip_codice(String tip_codice) {
        this.tip_codice = tip_codice;
    }
    public Integer getTip_sen_id() {
        return tip_sen_id;
    }

    public void setTip_sen_id(Integer tip_sen_id) {
        this.tip_sen_id = tip_sen_id;
    }
    public String getTip_unita() {
        return tip_unita;
    }

    public void setTip_unita(String tip_unita) {
        this.tip_unita = tip_unita;
    }
    public Boolean isTip_futuro() {
        return tip_futuro;
    }

    public void setTip_futuro(Boolean tip_futuro) {
        this.tip_futuro = tip_futuro;
    }

    @Override
    public String toString() {
        return "TipoRilevazione{" +
                "tip_id=" + tip_id +
                ", tip_tipologia=" + tip_tipologia +
                ", tip_codice=" + tip_codice +
                ", tip_sen_id=" + tip_sen_id +
                ", tip_unita=" + tip_unita +
                ", tip_futuro=" + tip_futuro +
                '}';
    }
}
