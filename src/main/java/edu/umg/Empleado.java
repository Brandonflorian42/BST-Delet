package edu.umg;

public class Empleado implements Comparable<Empleado> {
    private int id;

    public Empleado(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Empleado otroEmpleado) {
        // Comparar por ID
        return Integer.compare(this.id, otroEmpleado.getId());
    }
}
