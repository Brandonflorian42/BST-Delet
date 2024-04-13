package edu.umg;

public interface IBST<T> {
    void insertar(Empleado emp);

    boolean existe(int id);

    void eliminar(int id);
}
