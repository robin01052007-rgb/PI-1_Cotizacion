import java.util.Scanner;

public class CotizacionPCB {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dictamen = "FACTIBLE";
        String observaciones = "";
        double recargo = 0.0;

        System.out.println("============================================");
        System.out.println("   SISTEMA DE COTIZACIÓN DE PCB   ");
        System.out.println("============================================\n");
        
        System.out.print("Nombre del Cliente: ");
        String cliente = sc.nextLine();
        System.out.print("ID de Cotizacion: ");
        String idCotizacion = sc.nextLine();
        System.out.print("Cantidad de tarjetas: ");
        int cantidad = sc.nextInt();

        // --- Seleccion de tamaño ---
        double largo = 0, ancho = 0;
        int opcionTamanoSeleccionada = 0;
        boolean tamanoValido = false;
        while (!tamanoValido) {
            System.out.println("\n--- Dimensiones de la PCB ---");
            System.out.println("1. Pequeña (5x5 cm)\n2. Estandar (10x10 cm)\n3. Mediana (15x15 cm)\n4. Grande (20x20 cm)\n5. Personalizado");
            System.out.print("Seleccione opcion (1-5): ");
            opcionTamanoSeleccionada = sc.nextInt();

            switch (opcionTamanoSeleccionada) {
                case 1: largo = 5; ancho = 5; tamanoValido = true; break;
                case 2: largo = 10; ancho = 10; tamanoValido = true; break;
                case 3: largo = 15; ancho = 15; tamanoValido = true; break;
                case 4: largo = 20; ancho = 20; tamanoValido = true; break;
                case 5:
                    System.out.print("Ingrese Largo (cm): "); largo = sc.nextDouble();
                    System.out.print("Ingrese Ancho (cm): "); ancho = sc.nextDouble();
                    tamanoValido = true; break;
                default:
                    System.out.println(">> ERROR: Opcion de tamaño no valida. Intente de nuevo.");
            }
        }

        // --- Seleccion de capas ---
        int numCapas = 0;
        double factorCapas = 0;
        boolean capasValidas = false;
        while (!capasValidas) {
            System.out.println("\n--- Numero de capas ---");
            System.out.println("1. 2 Capas\n2. 4 Capas\n3. 6 Capas\n4. 12 Capas");
            System.out.print("Seleccione opcion (1-4): ");
            int opcionCapas = sc.nextInt();

            switch (opcionCapas) {
                case 1: numCapas = 2; factorCapas = 1.0; capasValidas = true; break;
                case 2: numCapas = 4; factorCapas = 1.5; capasValidas = true; break;
                case 3: numCapas = 6; factorCapas = 2.2; capasValidas = true; break;
                case 4: numCapas = 12; factorCapas = 5.0; capasValidas = true; break;
                default:
                    System.out.println(">> ERROR: Opcion de capas no valida. Intente de nuevo.");
            }
        }

        // --- Seleccion de acabado ---
        String tipoAcabado = "";
        double extraAcabado = 0;
        boolean acabadoValido = false;
        while (!acabadoValido) {
            System.out.println("\n--- Acabado Superficial ---");
            System.out.println("1. HASL \n2. ENIG");
            System.out.print("Seleccione opcion (1-2): ");
            int opcionAcabado = sc.nextInt();

            switch (opcionAcabado) {
                case 1: tipoAcabado = "HASL"; extraAcabado = 0.00; acabadoValido = true; break;
                case 2: tipoAcabado = "ENIG"; extraAcabado = 5.00; acabadoValido = true; break;
                default:
                    System.out.println(">> ERROR: Acabado no disponible. Intente de nuevo.");
            }
        }

        // --- Seleccion de ensamble ---
        String tipoEns = "";
        double factorEns = 0;
        boolean ensValido = false;
        while (!ensValido) {
            System.out.println("\n--- Tipo de Ensamble ---");
            System.out.println("1. SMT\n2. THT\n3. MIXTO");
            System.out.print("Seleccione opcion (1-3): ");
            int opcionEns = sc.nextInt();

            switch (opcionEns) {
                case 1: tipoEns = "SMT"; factorEns = 1.0; ensValido = true; break;
                case 2: tipoEns = "THT"; factorEns = 1.2; ensValido = true; break;
                case 3: tipoEns = "MIXTO"; factorEns = 1.5; ensValido = true; break;
                default:
                    System.out.println(">> ERROR: Tipo de ensamble no reconocido. Intente de nuevo.");
            }
        }

        System.out.print("\nNumero estimado de componentes por tarjeta: ");
        int numComp = sc.nextInt();

        // --- Lógica de Validación ---
        if (cantidad < 10){
            dictamen = "Factible con revision";
            recargo = 30.00;
            observaciones += "- Cantidad menor al minimo: se aplico recargo.\n";
        } else if (cantidad >= 1000){
            dictamen = "No factible";
            observaciones += "- La cantidad excede la capacidad de produccion actual.\n";
        } 

        if (opcionTamanoSeleccionada == 5 && (largo > 30 || ancho > 30)){
            dictamen = "No factible";
            observaciones += "- Tamaño fuera de estandar: maximo permitido 30x30 cm.\n";
        } 

        // --- Calculos ---
        double area = largo * ancho;
        double subtotalFab = ((area * 0.15 * factorCapas) + extraAcabado) * cantidad;
        double subtotalEns = (numComp * 0.05 * factorEns) * cantidad;
        
        // Validación de Densidad (Componentes por cm2)
        double densidad = numComp / area;
        if (densidad > 3.0){
            dictamen = "No factible";
            observaciones += "- Densidad critica: demasiados componentes para el tamaño.\n";
        } else if (densidad > 1.5){
            // Solo sube a Revisión si no estaba ya en No Factible
            if (!dictamen.equals("No factible")) dictamen = "Factible con revision";
            observaciones += "- Densidad alta: requiere revision de trazado de pistas.\n";
        }

        double totalGlobal = subtotalFab + subtotalEns + recargo;

        // --- Ticket ---
        System.out.println("\n============================================");
        System.out.println("      DICTAMEN: " + dictamen);
        System.out.println("============================================");
        System.out.printf("Cliente: %-20s ID: %s\n", cliente, idCotizacion);
        System.out.printf("PCB: %d Capas, %s | Ensamble: %s\n", numCapas, tipoAcabado, tipoEns);
        System.out.printf("Dimensiones: %.2f cm2 | Lote: %d pcs\n", area, cantidad);
        System.out.println("--------------------------------------------");
        
        // Si no es factible, lo mantenemos como "Estimado"
        System.out.printf("SUBTOTAL FABRICACIÓN:        $%10.2f\n", subtotalFab);
        System.out.printf("SUBTOTAL ENSAMBLE:           $%10.2f\n", subtotalEns);
        if (recargo > 0) {
            System.out.printf("RECARGO LOTE PEQUEÑO:        $%10.2f\n", recargo);
        }
        System.out.println("--------------------------------------------");
        System.out.printf("TOTAL ESTIMADO:              $%10.2f\n", totalGlobal);
        System.out.println("--------------------------------------------");
        
        if (!observaciones.isEmpty()) {
            System.out.println("OBSERVACIONES TÉCNICAS:");
            System.out.print(observaciones);
        }
        System.out.println("============================================");

        sc.close();
    }
}
