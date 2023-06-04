import java.util.ArrayList;

/**
 * La clase Cliente representa lo que puede hacer el cliente. Hereda de la clase Usuario.
 */
public class Cliente extends Usuario{

    /**
     * Atributos:
     */
    private ArrayList<Cliente> clientesRegistrados = new ArrayList<>();
    private String email;
    private String password;
    private float saldo;
    private boolean logged;
    Programacion programacion;

    /**
     * Constructor 1: Cliente
     * @param nombre
     * @param apellidos
     * @param edad
     */
    Cliente(String nombre, String apellidos, int edad){
        super(nombre, apellidos, edad);
    }

    /**
     * Constructor 2: Cliente
     * @param nombre
     * @param apellidos
     * @param edad
     * @param email
     * @param password
     * @param saldo
     */

    Cliente(String nombre, String apellidos, int edad, String email, String password, float saldo) {
        super(nombre, apellidos, edad);
        this.email=email;
        this.password=password;
        this.saldo=saldo;
        this.logged=false;
    }

    /**
     * Constructor 3: Cliente
     * @param nombre
     * @param apellidos
     * @param edad
     * @param email
     * @param password
     */
    Cliente(String nombre, String apellidos, int edad, String email, String password) {
        super(nombre, apellidos, edad);
        this.email=email;
        this.password=password;
        this.saldo=0.00f;
        this.logged=false;
    }

    /**
     * Constructor copia: Cliente
     * @param cliente
     */

    Cliente(Cliente cliente){
        super(cliente.nombre, cliente.apellidos, cliente.edad);
        this.email=cliente.email;
        this.password=cliente.password;
        this.saldo=cliente.saldo;
        this.logged=cliente.logged;
    }


    /**
     * Setter&Getter: saldo
     */
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    public float getSaldo() {
        return saldo;
    }

    /**
     * Setter&Getter: password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    /**
     * Getter: clientesRegistrados
     */
    public ArrayList<Cliente> getClientesRegistrados() {
        return clientesRegistrados;
    }


    // MÉTODOS

    /**
     * registrarse: Registra un nuevo cliente (añade un nuevo objeto Cliente a la lista de clientesRegistrados)
     * @param nombre
     * @param apellidos
     * @param edad
     * @param email
     * @param password
     */
    public void registrarse(String nombre, String apellidos, int edad, String email, String password){
        clientesRegistrados.add(new Cliente(nombre,apellidos,edad, email, password, saldo));
    }


    /**
     * iniciarSesion: Comprueba si un usuario está registrado o no teniendo en cuenta su correo y contraseña
     * @param correo
     * @param password
     * @return boolean (está en la lista clientesRegistrados o no)
     */
    public boolean iniciarSesion(String correo, String password){
        int position = -1;
        for (int i = 0; i < clientesRegistrados.size(); i++) {
            if (clientesRegistrados.get(i).email.equals(correo) && clientesRegistrados.get(i).password.equals(password)){
                position=i;
                clientesRegistrados.get(i).logged = true;
                break;
            }
        }
        if (position<0){
            throw new ArrayIndexOutOfBoundsException("Error: No se ha encontrado al usuario");
        }
        return clientesRegistrados.get(position).logged;
    }

    /**
     * tipoCategoría: El usuario pasa un String como parámetro para seleccionar una categoría
     * @param categoria
     * @return tipoCategoria (devuelve la categoría que coincide con el parámetro tipo String)
     */

    public tipoCategoria filtroCategoria(String categoria){
        tipoCategoria option = null;
        switch (categoria){
            case "orquesta sinfonica" -> option = tipoCategoria.ORQUESTA_SINFONICA;
            case "orquesta filarmonica" -> option = tipoCategoria.ORQUESTA_FILARMONICA;
            case "concierto jazz" -> option = tipoCategoria.CONCIERTO_JAZZ;
            case "concierto solista" -> option = tipoCategoria.CONCIERTO_SOLISTA;
            case "opera" -> option = tipoCategoria.OPERA;
            case "ballet" -> option = tipoCategoria.BALLET;
            case "cantantes" -> option = tipoCategoria.CANTANTES;
            case "musical" -> option = tipoCategoria.MUSICAL;
            case "danza" -> option = tipoCategoria.DANZA;
            case "otros" -> option = tipoCategoria.OTROS;
        }
        return option;
    }

    /**
     * mostrarEvento: Muestra el evento que se halla en la posición indicada por parámetro
     * @param position
     */
    public void mostrarEvento(int position) {
        System.out.println("\nEvento " + position + ": " + programacion.getListaEventos().get(position).eventos);
        System.out.println("Fecha: " + programacion.getListaEventos().get(position).fechaFormat);
        System.out.println("Descripción: " + programacion.getListaEventos().get(position).descripcion);
        System.out.println("Artistas: " + programacion.getListaEventos().get(position).artistas);
        System.out.println("Precio: " + programacion.getListaEventos().get(position).precio);
        System.out.println("Categoría: " + programacion.getListaEventos().get(position).categoria);
        System.out.println("Estado: " + programacion.getListaEventos().get(position).estado + "\n");
    }

    /**
     * mostrarEventos: Se muestran todos los eventos
     */
    public void mostrarEventos() {
        try {
            if (programacion.getListaEventos().isEmpty()){
                throw new Exception("No existen eventos actualmente");
            }
        }
        catch (Exception e) {
                System.out.println(e.getMessage());
        }

        System.out.println("\nEVENTOS:");
            for (int i = 0; i < programacion.getListaEventos().size(); i++) {
                mostrarEvento(i);
            }
    }
    /**
     * mostrarEventos (parámetro): Se aplica el filtro por categoría y se muestran todos los eventos de la misma
     * @param filtro
     */
    public void mostrarEventos(String filtro) {
        int count = 0;
        for (int i = 0; i < programacion.getListaEventos().size() ; i++) {
            if (programacion.getListaEventos().get(i).categoria.equals(filtroCategoria(filtro))){
                mostrarEvento(i);
                count++;
            }
        }
        if (count==0){
            System.out.println("No se ha encontrado eventos de esta categoría");
        }
    }

    /**
     * pagar: El usuario pasa por parámetros un String indicando el nombre de un evento, si este existe, se comprueba si
     * el cliente dispone del saldo suficiente para pagar el evento
     * @param evento
     * @return boolean: Se confirma el pago o se rechaza
     */
    public boolean pagar(String evento){
        try {
            if (programacion.getListaEventos().isEmpty()) {
                throw new Exception("Error: No existen eventos");
            }
            else if (!this.logged) {
                throw new Exception("Error: No has iniciado sesión");
            }

            else {
                Programacion pro = null;
                int count = 0;
                for (int i = 0; i < programacion.getListaEventos().size(); i++) {
                    if (programacion.getListaEventos().get(i).eventos.equals(evento)){
                        pro = programacion.getListaEventos().get(i);
                        count++;
                        break;
                    }
                }
                if(count<1){
                    throw new Exception("Error: No se ha encontrado el evento");
                }
                 else if (pro.precio > getSaldo()) {
                    throw new Exception("Error: No dispones del saldo suficiente");
                } else if (!pro.estado) {
                    throw new Exception("Error: El evento no está disponible");
                } else {
                    setSaldo(getSaldo() - pro.precio);
                    return true;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * toString: Retorna una cadena con todos los datos del cliente (incluyendo el saldo)
     * @return String
     */
    public String toString() {
        return "Mis datos:\nNombre: " + this.nombre + "\nApellidos: " + this.apellidos + "\nEdad: " + this.edad + "\nEmail: " + this.email +
                "\nContraseña: " + this.password + "\nSaldo: " + this.saldo;
    }
}
