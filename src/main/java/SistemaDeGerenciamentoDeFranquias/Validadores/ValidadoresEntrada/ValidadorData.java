package SistemaDeGerenciamentoDeFranquias.Validadores.ValidadoresEntrada;

import SistemaDeGerenciamentoDeFranquias.Exceptions.EntradaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidadorData {
    public static LocalDate validarData(String dataTexto) throws EntradaException {
        if (dataTexto == null || dataTexto.isBlank())
            throw new EntradaException("O campo de data está vazio");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data;

        try {
            data = LocalDate.parse(dataTexto, formatter);
        } catch (DateTimeParseException e) {
            throw new EntradaException("Formato de data inválido");
        }

        LocalDate dataMinima = LocalDate.of(2010, 1, 1);
        LocalDate dataMaxima = LocalDate.of(2025, 9, 1);

        if (data.isBefore(dataMinima) || data.isAfter(dataMaxima))
            throw new EntradaException("Data fora do intervalo válido");
        return data;
    }
}
