package helpers;

import enums.EstadoMozos;
import exceptions.controlador.ErrorAlAgregarMozoException;
import modelo.configEmpresa.Mozo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MozoHelpers {

    /**
     * Retorna la cantidad de mozos que poseen el estado ingresado
     * @param mozos lista de mozos
     * @param estado estado buscado
     * @return cantidad de mozos que poseen el estado ingresado
     */
    public static int getCantidadDeMozosEnEstado(ArrayList<Mozo> mozos, EstadoMozos estado){
        int cont = 0;
        for(int i = 0 ; i < mozos.size() ; i++)
            cont += mozos.get(i).getEstado().equals(estado) ? 1 : 0;
        return cont;
    }

    /**
     * Retorna si hay algun mozo dentro de la lista que su estado sea null
     * @param mozos lista de mozos
     * @return hay algun mozo dentro de la lista que su estado sea null
     */
    public static boolean thereIsMozoWithoutState(ArrayList<Mozo> mozos){
        boolean thereIs = false;
        int i = 0;
        while(i < mozos.size() && !thereIs){
            if(mozos.get(i).getEstado() == null)
                thereIs = true;
            i++;
        }
        return thereIs;
    }

    /**
     * Chequea el nombre y apellido de un mozo
     * @param name Nombre a chequear
     * @throws ErrorAlAgregarMozoException Si el nombre es invalido
     */
    public static void checkNombreYApellido(String name) throws ErrorAlAgregarMozoException {
        if(name == null || name.isBlank() || name.isEmpty())
            throw new ErrorAlAgregarMozoException("El nombre del mozo no es correcto");
    }

    /**
     * Retorna una fecha de nacimiento a partir de un string con formato dd/MM/yyyy
     * @param nacimiento fecha que se quiere convertir
     * @return fecha correspondiente
     * @throws ErrorAlAgregarMozoException si el formato de la fecha es invalida
     */
    public static GregorianCalendar getFechaDeNacimiento(String nacimiento) throws ErrorAlAgregarMozoException {
        if(nacimiento == null || nacimiento.isEmpty() || nacimiento.isBlank())
            throw new ErrorAlAgregarMozoException("La fecha de nacimiento no es correcta");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            GregorianCalendar res = new GregorianCalendar();
            res.setTime(sdf.parse(nacimiento));
            if(FechasHelpers.diferenceYears(res) >= 110 || FechasHelpers.diferenceYears(res) <= 18)
                throw new ErrorAlAgregarMozoException("La fecha de nacimiento es invalida");
            return res;
        } catch (ParseException e) {
            throw new ErrorAlAgregarMozoException("El formato de la fecha de nacimiento no es el indicado");
        }

    }

    /**
     * retorna si una cantidad de hijos es valida
     * @param cantHijos cantidad de hijos
     * @throws ErrorAlAgregarMozoException Error si la cantidad de hijos es invalida
     */
    public static void checkCantHijos(int cantHijos) throws ErrorAlAgregarMozoException {
        if (cantHijos < 0 || cantHijos > 16)
            throw new ErrorAlAgregarMozoException("La cantidad de hijos no es valida");
    }
}
