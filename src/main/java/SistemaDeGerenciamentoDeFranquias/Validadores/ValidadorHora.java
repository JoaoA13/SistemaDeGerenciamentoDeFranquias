package SistemaDeGerenciamentoDeFranquias.Validadores;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidadorHora {
    public static LocalTime validarHora(String horaTexto) throws EntradaException {
        if (horaTexto == null || horaTexto.isBlank())
            throw new EntradaException("O campo de hora está vazio");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hora;
        try {
            hora = LocalTime.parse(horaTexto, formatter);
        } catch (DateTimeParseException e) {
            throw new EntradaException("Formato de hora inválido");
        }

        LocalTime horaMinima = LocalTime.of(6, 0);   // 06:00
        LocalTime horaMaxima = LocalTime.of(23, 0);  // 23:00

        if (hora.isBefore(horaMinima) || hora.isAfter(horaMaxima)) {
            throw new EntradaException("Hora fora do intervalo válido");
        }
        return hora;
    }
}
