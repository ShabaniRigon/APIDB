package serverrest.model;

/**
 * MODEL / POJO
 * Rappresenta una riga della tabella Sensore.
 * Qui ci sono solo dati, getter e setter: niente SQL.
 */
public class Sensore {
    private Integer sen_id;
    private String sen_modello;
    private String sen_codice;
    private String sen_produttore;

    // Costruttore vuoto: serve a Gson quando legge il JSON.
    public Sensore() {
    }


    public Integer getSen_id() {
        return sen_id;
    }

    public void setSen_id(Integer sen_id) {
        this.sen_id = sen_id;
    }
    public String getSen_modello() {
        return sen_modello;
    }

    public void setSen_modello(String sen_modello) {
        this.sen_modello = sen_modello;
    }
    public String getSen_codice() {
        return sen_codice;
    }

    public void setSen_codice(String sen_codice) {
        this.sen_codice = sen_codice;
    }
    public String getSen_produttore() {
        return sen_produttore;
    }

    public void setSen_produttore(String sen_produttore) {
        this.sen_produttore = sen_produttore;
    }

    @Override
    public String toString() {
        return "Sensore{" +
                "sen_id=" + sen_id +
                ", sen_modello=" + sen_modello +
                ", sen_codice=" + sen_codice +
                ", sen_produttore=" + sen_produttore +
                '}';
    }
}
