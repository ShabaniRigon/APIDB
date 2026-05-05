package serverrest.model;

/**
 * MODEL / POJO
 * Rappresenta una riga della tabella Utente.
 * Qui ci sono solo dati, getter e setter: niente SQL.
 */
public class Utente {
    private Integer ute_id;
    private String ute_username;
    private String ute_password;
    private Boolean ute_admin;
    private String ute_token;
    private String ute_scadenzaToken;
    private String ute_creato_at;

    // Costruttore vuoto: serve a Gson quando legge il JSON.
    public Utente() {
    }


    public Integer getUte_id() {
        return ute_id;
    }

    public void setUte_id(Integer ute_id) {
        this.ute_id = ute_id;
    }
    public String getUte_username() {
        return ute_username;
    }

    public void setUte_username(String ute_username) {
        this.ute_username = ute_username;
    }
    public String getUte_password() {
        return ute_password;
    }

    public void setUte_password(String ute_password) {
        this.ute_password = ute_password;
    }
    public Boolean isUte_admin() {
        return ute_admin;
    }

    public void setUte_admin(Boolean ute_admin) {
        this.ute_admin = ute_admin;
    }
    public String getUte_token() {
        return ute_token;
    }

    public void setUte_token(String ute_token) {
        this.ute_token = ute_token;
    }
    public String getUte_scadenzaToken() {
        return ute_scadenzaToken;
    }

    public void setUte_scadenzaToken(String ute_scadenzaToken) {
        this.ute_scadenzaToken = ute_scadenzaToken;
    }
    public String getUte_creato_at() {
        return ute_creato_at;
    }

    public void setUte_creato_at(String ute_creato_at) {
        this.ute_creato_at = ute_creato_at;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "ute_id=" + ute_id +
                ", ute_username=" + ute_username +
                ", ute_password=" + ute_password +
                ", ute_admin=" + ute_admin +
                ", ute_token=" + ute_token +
                ", ute_scadenzaToken=" + ute_scadenzaToken +
                ", ute_creato_at=" + ute_creato_at +
                '}';
    }
}
