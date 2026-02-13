# PI-1: Cotización de PCB
**Objetivo:** Este programa calcula el costo de fabricación de placas de circuito impreso (PCB) basado en dimensiones y materiales.

### 1. Requisitos
* **JDK:** Versión 11 o superior.
* **IDE:** IntelliJ, Eclipse o ejecución por terminal.

### 2. Cómo Compilar y Ejecutar
Para compilar desde la terminal:
`javac src/CotizacionPCB.java`

Para ejecutar:
`java -cp src CotizacionPCB`

### Reglas de Negocio y Costos Aplicados
El sistema evalúa la viabilidad técnica mediante los siguientes criterios:

* **Costo de Fabricación:** `((Área * 0.15 * FactorCapas) + ExtraAcabado) * Cantidad`.
* **Costo de Ensamble:** `(NumComponentes * 0.05 * FactorEnsamble) * Cantidad`.
* **Dictamen de Factibilidad:**
    * **No Factible:** Si la densidad de componentes supera los 3.0/cm², si el tamaño excede los 30x30cm o si el lote es de 1000+ unidades.
    * **Factible con Revisión:** Si el lote es menor a 10 unidades (aplica recargo de $30) o si la densidad de componentes es alta (>1.5/cm²).
    * **Factible:** Proyectos que cumplen con todos los estándares industriales.

### 4. Ejemplos de Entrada/Salida
* **Entrada:** Ancho: 10cm, Largo: 10cm, Material: FR4.
* **Salida:** "El costo total de su PCB es: $60.00. Dictamen: Aprobado para producción."
