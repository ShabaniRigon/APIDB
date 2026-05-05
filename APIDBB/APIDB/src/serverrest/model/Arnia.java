package serverrest.model;

/**
 * MODEL / POJO
 * Rappresenta una riga della tabella Arnia.
 * Qui ci sono solo dati, getter e setter: niente SQL.
 */
public class Arnia {
    private Integer arn_id;
    private Integer arn_api_id;
    private String arn_nome;
    private String arn_dataInst;
    private Boolean arn_piena;
    private String arn_MacAddress;
    private Boolean arn_attiva;
    private String arn_creato_at;

    // Costruttore vuoto: serve a Gson quando legge il JSON.
    public Arnia() {
    }


    public Integer getArn_id() {
        return arn_id;
    }

    public void setArn_id(Integer arn_id) {
        this.arn_id = arn_id;
    }
    public Integer getArn_api_id() {
        return arn_api_id;
    }

    public void setArn_api_id(Integer arn_api_id) {
        this.arn_api_id = arn_api_id;
    }
    public String getArn_nome() {
        return arn_nome;
    }

    public void setArn_nome(String arn_nome) {
        this.arn_nome = arn_nome;
    }
    public String getArn_dataInst() {
        return arn_dataInst;
    }

    public void setArn_dataInst(String arn_dataInst) {
        this.arn_dataInst = arn_dataInst;
    }
    public Boolean isArn_piena() {
        return arn_piena;
    }

    public void setArn_piena(Boolean arn_piena) {
        this.arn_piena = arn_piena;
    }
    public String getArn_MacAddress() {
        return arn_MacAddress;
    }

    public void setArn_MacAddress(String arn_MacAddress) {
        this.arn_MacAddress = arn_MacAddress;
    }
    public Boolean isArn_attiva() {
        return arn_attiva;
    }

    public void setArn_attiva(Boolean arn_attiva) {
        this.arn_attiva = arn_attiva;
    }
    public String getArn_creato_at() {
        return arn_creato_at;
    }

    public void setArn_creato_at(String arn_creato_at) {
        this.arn_creato_at = arn_creato_at;
    }

    @Override
    public String toString() {
        return "Arnia{" +
                "arn_id=" + arn_id +
                ", arn_api_id=" + arn_api_id +
                ", arn_nome=" + arn_nome +
                ", arn_dataInst=" + arn_dataInst +
                ", arn_piena=" + arn_piena +
                ", arn_MacAddress=" + arn_MacAddress +
                ", arn_attiva=" + arn_attiva +
                ", arn_creato_at=" + arn_creato_at +
                '}';
    }
}
