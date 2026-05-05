package serverrest.model;

/**
 * MODEL / POJO
 * Rappresenta una riga della tabella Apiario.
 * Qui ci sono solo dati, getter e setter: niente SQL.
 */
public class Apiario {
    private Integer api_id;
    private String api_nome;
    private String api_luogo;
    private Double api_lon;
    private Double api_lat;
    private String api_creato_at;

    // Costruttore vuoto: serve a Gson quando legge il JSON.
    public Apiario() {
    }


    public Integer getApi_id() {
        return api_id;
    }

    public void setApi_id(Integer api_id) {
        this.api_id = api_id;
    }
    public String getApi_nome() {
        return api_nome;
    }

    public void setApi_nome(String api_nome) {
        this.api_nome = api_nome;
    }
    public String getApi_luogo() {
        return api_luogo;
    }

    public void setApi_luogo(String api_luogo) {
        this.api_luogo = api_luogo;
    }
    public Double getApi_lon() {
        return api_lon;
    }

    public void setApi_lon(Double api_lon) {
        this.api_lon = api_lon;
    }
    public Double getApi_lat() {
        return api_lat;
    }

    public void setApi_lat(Double api_lat) {
        this.api_lat = api_lat;
    }
    public String getApi_creato_at() {
        return api_creato_at;
    }

    public void setApi_creato_at(String api_creato_at) {
        this.api_creato_at = api_creato_at;
    }

    @Override
    public String toString() {
        return "Apiario{" +
                "api_id=" + api_id +
                ", api_nome=" + api_nome +
                ", api_luogo=" + api_luogo +
                ", api_lon=" + api_lon +
                ", api_lat=" + api_lat +
                ", api_creato_at=" + api_creato_at +
                '}';
    }
}
