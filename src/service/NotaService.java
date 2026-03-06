package service;

import utils.EmailUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class NotaService {

    private Scanner scanner = new Scanner(System.in);

    private Path obtenerCarpetaUsuario(String email) {
        String emailSanitizado = EmailUtils.sanitizar(email);
        return Paths.get("data", "usuarios", emailSanitizado);
    }

    private List<Path> obtenerNotas(String email) throws IOException {

        Path carpeta = obtenerCarpetaUsuario(email);

        if (!Files.exists(carpeta)) {
            return new ArrayList<>();
        }

        return Files.list(carpeta)
                .filter(p -> p.getFileName().toString().startsWith("nota_"))
                .sorted()
                .collect(Collectors.toList());
    }

    public void crearNota(String email) {

        try {

            List<Path> notas = obtenerNotas(email);

            int numero = notas.size() + 1;

            String nombreArchivo = String.format("nota_%03d.txt", numero);

            Path carpeta = obtenerCarpetaUsuario(email);
            Files.createDirectories(carpeta);

            Path nuevaNota = carpeta.resolve(nombreArchivo);

            System.out.print("Título: ");
            String titulo = scanner.nextLine();

            System.out.println("Contenido (línea vacía para terminar):");

            StringBuilder contenido = new StringBuilder();
            String linea;

            while (!(linea = scanner.nextLine()).isEmpty()) {
                contenido.append(linea).append("\n");
            }

            String textoFinal = "Título: " + titulo + "\n\n" + contenido;

            Files.writeString(nuevaNota, textoFinal,
                    StandardOpenOption.CREATE);

            System.out.println("Nota creada correctamente.");

        } catch (IOException e) {
            System.out.println("Error al crear la nota.");
        }
    }

    public void listarNotas(String email) {

        try {

            List<Path> notas = obtenerNotas(email);

            if (notas.isEmpty()) {
                System.out.println("No hay notas.");
                return;
            }

            for (int i = 0; i < notas.size(); i++) {
                System.out.println((i + 1) + ". "
                        + notas.get(i).getFileName());
            }

        } catch (IOException e) {
            System.out.println("Error al listar notas.");
        }
    }

    public void verNota(String email) {

        try {

            List<Path> notas = obtenerNotas(email);

            System.out.print("Número de nota: ");
            int numero = Integer.parseInt(scanner.nextLine());

            if (numero < 1 || numero > notas.size()) {
                System.out.println("Número inválido.");
                return;
            }

            String contenido = Files.readString(notas.get(numero - 1));
            System.out.println(contenido);

        } catch (Exception e) {
            System.out.println("Error al mostrar la nota.");
        }
    }

    public void eliminarNota(String email) {

        try {

            List<Path> notas = obtenerNotas(email);

            System.out.print("Número de nota a eliminar: ");
            int numero = Integer.parseInt(scanner.nextLine());

            if (numero < 1 || numero > notas.size()) {
                System.out.println("Número inválido.");
                return;
            }

            Files.delete(notas.get(numero - 1));

            System.out.println("Nota eliminada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al eliminar la nota.");
        }
    }

    public void buscarNota(String email) {

        try {

            List<Path> notas = obtenerNotas(email);

            System.out.print("Palabra clave: ");
            String palabra = scanner.nextLine().toLowerCase();

            boolean encontrada = false;

            for (Path nota : notas) {

                String contenido = Files.readString(nota).toLowerCase();

                if (contenido.contains(palabra)) {

                    System.out.println("\nEncontrada en: "
                            + nota.getFileName());

                    System.out.println(contenido);
                    encontrada = true;
                }
            }

            if (!encontrada) {
                System.out.println("No se encontraron coincidencias.");
            }

        } catch (IOException e) {
            System.out.println("Error en la búsqueda.");
        }
    }
}