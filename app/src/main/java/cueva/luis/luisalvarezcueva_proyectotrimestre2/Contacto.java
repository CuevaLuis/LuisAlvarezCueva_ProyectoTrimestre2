package cueva.luis.luisalvarezcueva_proyectotrimestre2;

public class Contacto {

    public String nombre;
    public String telefono;

    public Contacto(){

    }

    public Contacto(String nombre, String telefono){
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return  nombre + ": " + telefono;
    }
}
