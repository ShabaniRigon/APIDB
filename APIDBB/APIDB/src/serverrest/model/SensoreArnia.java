package serverrest.model;

/**
 * MODEL / POJO
 * Rappresenta una riga della tabella SensoreArnia.
 * Qui ci sono solo dati, getter e setter: niente SQL.
 */
public class SensoreArnia {
    private Integer sea_id;
    private Integer sea_arn_id;
    private Integer sea_tip_id;
    private Boolean sea_stato;
    private Boolean sea_attivo;
    private Boolean sea_on;
    private Double sea_min;
    private Double sea_max;
    private Integer sea_intervallo_ms;
    private Double sea_delta;
    private Double sea_cal_factor;
    private Long sea_tare_offset;
    private String sea_note;
    private String sea_aggiornato_at;

    // Costruttore vuoto: serve a Gson quando legge il JSON.
    public SensoreArnia() {
    }


    public Integer getSea_id() {
        return sea_id;
    }

    public void setSea_id(Integer sea_id) {
        this.sea_id = sea_id;
    }
    public Integer getSea_arn_id() {
        return sea_arn_id;
    }

    public void setSea_arn_id(Integer sea_arn_id) {
        this.sea_arn_id = sea_arn_id;
    }
    public Integer getSea_tip_id() {
        return sea_tip_id;
    }

    public void setSea_tip_id(Integer sea_tip_id) {
        this.sea_tip_id = sea_tip_id;
    }
    public Boolean isSea_stato() {
        return sea_stato;
    }

    public void setSea_stato(Boolean sea_stato) {
        this.sea_stato = sea_stato;
    }
    public Boolean isSea_attivo() {
        return sea_attivo;
    }

    public void setSea_attivo(Boolean sea_attivo) {
        this.sea_attivo = sea_attivo;
    }
    public Boolean isSea_on() {
        return sea_on;
    }

    public void setSea_on(Boolean sea_on) {
        this.sea_on = sea_on;
    }
    public Double getSea_min() {
        return sea_min;
    }

    public void setSea_min(Double sea_min) {
        this.sea_min = sea_min;
    }
    public Double getSea_max() {
        return sea_max;
    }

    public void setSea_max(Double sea_max) {
        this.sea_max = sea_max;
    }
    public Integer getSea_intervallo_ms() {
        return sea_intervallo_ms;
    }

    public void setSea_intervallo_ms(Integer sea_intervallo_ms) {
        this.sea_intervallo_ms = sea_intervallo_ms;
    }
    public Double getSea_delta() {
        return sea_delta;
    }

    public void setSea_delta(Double sea_delta) {
        this.sea_delta = sea_delta;
    }
    public Double getSea_cal_factor() {
        return sea_cal_factor;
    }

    public void setSea_cal_factor(Double sea_cal_factor) {
        this.sea_cal_factor = sea_cal_factor;
    }
    public Long getSea_tare_offset() {
        return sea_tare_offset;
    }

    public void setSea_tare_offset(Long sea_tare_offset) {
        this.sea_tare_offset = sea_tare_offset;
    }
    public String getSea_note() {
        return sea_note;
    }

    public void setSea_note(String sea_note) {
        this.sea_note = sea_note;
    }
    public String getSea_aggiornato_at() {
        return sea_aggiornato_at;
    }

    public void setSea_aggiornato_at(String sea_aggiornato_at) {
        this.sea_aggiornato_at = sea_aggiornato_at;
    }

    @Override
    public String toString() {
        return "SensoreArnia{" +
                "sea_id=" + sea_id +
                ", sea_arn_id=" + sea_arn_id +
                ", sea_tip_id=" + sea_tip_id +
                ", sea_stato=" + sea_stato +
                ", sea_attivo=" + sea_attivo +
                ", sea_on=" + sea_on +
                ", sea_min=" + sea_min +
                ", sea_max=" + sea_max +
                ", sea_intervallo_ms=" + sea_intervallo_ms +
                ", sea_delta=" + sea_delta +
                ", sea_cal_factor=" + sea_cal_factor +
                ", sea_tare_offset=" + sea_tare_offset +
                ", sea_note=" + sea_note +
                ", sea_aggiornato_at=" + sea_aggiornato_at +
                '}';
    }
}
