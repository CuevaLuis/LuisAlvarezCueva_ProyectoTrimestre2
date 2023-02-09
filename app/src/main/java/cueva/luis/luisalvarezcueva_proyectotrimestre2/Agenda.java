package cueva.luis.luisalvarezcueva_proyectotrimestre2;

import java.util.List;

public class Agenda {

    // Email del usuario de la agenda
    private String usuario;
    private List<Contacto> contactos;

    public Agenda(String usuario){
        this.usuario = usuario;
    }

    public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }
}
