package serverrest.model;

/**
 * MODEL / POJO
 * Rappresenta una riga della tabella Rilevazione.
 * Qui ci sono solo dati, getter e setter: niente SQL.
 */
public class Rilevazione {
    private Long ril_id;
    private Integer ril_sea_id;
    private Double ril_dato;
    private String ril_dataOra;
    private Integer ril_codice_stato;
    private String ril_creata_at;

    // Costruttore vuoto: serve a Gson quando legge il JSON.
    public Rilevazione() {
    }


    public Long getRil_id() {
        return ril_id;
    }

    public void setRil_id(Long ril_id) {
        this.ril_id = ril_id;
    }
    public Integer getRil_sea_id() {
        return ril_sea_id;
    }

    public void setRil_sea_id(Integer ril_sea_id) {
        this.ril_sea_id = ril_sea_id;
    }
    public Double getRil_dato() {
        return ril_dato;
    }

    public void setRil_dato(Double ril_dato) {
        this.ril_dato = ril_dato;
    }
    public String getRil_dataOra() {
        return ril_dataOra;
    }

    public void setRil_dataOra(String ril_dataOra) {
        this.ril_dataOra = ril_dataOra;
    }
    public Integer getRil_codice_stato() {
        return ril_codice_stato;
    }

    public void setRil_codice_stato(Integer ril_codice_stato) {
        this.ril_codice_stato = ril_codice_stato;
    }
    public String getRil_creata_at() {
        return ril_creata_at;
    }

    public void setRil_creata_at(String ril_creata_at) {
        this.ril_creata_at = ril_creata_at;
    }

    @Override
    public String toString() {
        return "Rilevazione{" +
                "ril_id=" + ril_id +
                ", ril_sea_id=" + ril_sea_id +
                ", ril_dato=" + ril_dato +
                ", ril_dataOra=" + ril_dataOra +
                ", ril_codice_stato=" + ril_codice_stato +
                ", ril_creata_at=" + ril_creata_at +
                '}';
    }
}
