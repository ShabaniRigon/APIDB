package serverrest;

public class Apiario {
    private int api_id;
    private String api_nome;
    private String api_luogo;

    // Costruttore vuoto (FONDAMENTALE per Gson)
    public Apiario() {
    }

    // Costruttore per le POST (quando l'ID non c'è ancora perché lo crea il DB)
    public Apiario(String api_nome, String api_luogo) {
        this.api_nome = api_nome;
        this.api_luogo = api_luogo;
    }

    // --- GETTER E SETTER ---

    public int getApi_id() {
        return api_id;
    }

    public void setApi_id(int api_id) {
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
}