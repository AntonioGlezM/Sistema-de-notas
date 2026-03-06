package service;

import utils.EmailUtils;
import utils.Validaciones;

import java.util.Scanner;
import java.nio.file.*;
import java.io.IOException;

public class UsuarioService {

    private NotaService notaService = new NotaService();
    private Scanner scanner = new Scanner(System.in);

    public void registrarUsuario() {

        System.out.println("\n--- REGISTRO ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (Validaciones.estaVacio(email) || Validaciones.estaVacio(password)) {
            System.out.println("Error: campos vacíos.");
            return;
        }

        try {

            Path usersFile = Paths.get("data", "users.txt");

            String linea = email + ";" + password + "\n";

            Files.write(usersFile,
                    linea.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            String emailSanitizado = EmailUtils.sanitizar(email);

            Path carpetaUsuario = Paths.get("data", "usuarios", emailSanitizado);

            Files.createDirectories(carpetaUsuario);

            Path notasFile = carpetaUsuario.resolve("notas.txt");

            if (!Files.exists(notasFile)) {
                Files.createFile(notasFile);
            }

            System.out.println("Usuario registrado correctamente.");

        } catch (IOException e) {

            System.out.println("Error al registrar usuario.");
        }
    }

    public void iniciarSesion() {

        System.out.println("\n--- LOGIN ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        Path usersFile = Paths.get("data", "users.txt");

        if (!Files.exists(usersFile)) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        try {

            var lineas = Files.readAllLines(usersFile);

            boolean loginCorrecto = false;

            for (String linea : lineas) {

                String[] datos = linea.split(";");

                if (datos.length < 2)
                    continue;

                String emailGuardado = datos[0];
                String passwordGuardado = datos[1];

                if (email.equals(emailGuardado) &&
                        password.equals(passwordGuardado)) {

                    loginCorrecto = true;
                    break;
                }
            }

            if (loginCorrecto) {

                System.out.println("Login correcto.");
                menuUsuario(email);

            } else {

                System.out.println("Email o contraseña incorrectos.");
            }

        } catch (IOException e) {

            System.out.println("Error al leer usuarios.");
        }
    }

    public void menuUsuario(String email) {

        int opcion;

        do {

            System.out.println("\n===== MENÚ USUARIO =====");
            System.out.println("1. Crear nota");
            System.out.println("2. Listar notas");
            System.out.println("3. Ver nota");
            System.out.println("4. Eliminar nota");
            System.out.println("5. Buscar nota");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcion = -1;
            }

            switch (opcion) {

                case 1:
                    notaService.crearNota(email);
                    break;

                case 2:
                    notaService.listarNotas(email);
                    break;

                case 3:
                    notaService.verNota(email);
                    break;

                case 4:
                    notaService.eliminarNota(email);
                    break;

                case 5:
                    notaService.buscarNota(email);
                    break;

                case 0:
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    System.out.println("Opción incorrecta.");
            }

        } while (opcion != 0);
    }
}