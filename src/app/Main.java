package app;

import java.util.Scanner;
import service.UsuarioService;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {

        int opcion;

        do {

            System.out.println("\n MENU PRINCIPAL ");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:
                    usuarioService.registrarUsuario();
                    break;

                case 2:
                    usuarioService.iniciarSesion();
                    break;

                case 0:
                    System.out.println("Hasta la próxima.");
                    break;

                default:
                    System.out.println("Opción no válida. Seleccione una:");
            }

        } while (opcion != 0);
    }
}