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

    public static int getCantidadDeMozosEnEstado(ArrayList<Mozo> mozos, EstadoMozos estado){
        int cont = 0;
        for(int i = 0 ; i < mozos.size() ; i++)
            cont += mozos.get(i).getEstado() == estado ? 1 : 0;
        return cont;
    }

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

    public static void checkNombreYApellido(String name) throws ErrorAlAgregarMozoException {
        if(name == null || name.isBlank() || name.isEmpty())
            throw new ErrorAlAgregarMozoException("El nombre del mozo no es correcto");
    }

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

    public static void checkCantHijos(int cantHijos) throws ErrorAlAgregarMozoException {
        if (cantHijos < 0 || cantHijos > 16)
            throw new ErrorAlAgregarMozoException("La cantidad de hijos no es valida");
    }
}
