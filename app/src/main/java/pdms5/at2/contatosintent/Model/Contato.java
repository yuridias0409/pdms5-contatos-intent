package pdms5.at2.contatosintent.Model;

import java.io.Serializable;

public class Contato implements Serializable {
    private String nome;
    private String telefone;
    private String celular;
    private String email;
    private String sitePessoal;
    private Boolean isTefoneComercial;

    public Contato(){}

    public Contato(String nome, String telefone, String celular, String email, String sitePessoal, Boolean isTefoneComercial) {
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.sitePessoal = sitePessoal;
        this.isTefoneComercial = isTefoneComercial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSitePessoal() {
        return sitePessoal;
    }

    public void setSitePessoal(String sitePessoal) {
        this.sitePessoal = sitePessoal;
    }

    public Boolean getTefoneComercial() {
        return isTefoneComercial;
    }

    public void setTefoneComercial(Boolean tefoneComercial) {
        isTefoneComercial = tefoneComercial;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", sitePessoal='" + sitePessoal + '\'' +
                ", isTefoneComercial=" + isTefoneComercial +
                '}';
    }
}
