package edu.umg;

public class BST implements IBST<Empleado> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BST.class);

    private Empleado valor;
    private BST izdo, dcho;
    private BST padre;

    public BST() {
        this.valor = null;
        this.izdo = null;
        this.dcho = null;
        this.padre = null;
    }

    @Override
    public void insertar(Empleado emp) {
        insertarImp(emp, null);
    }

    private void insertarImp(Empleado emp, BST padre) {
        if (valor == null) {
            this.valor = emp;
            this.padre = padre;
        } else {
            if (emp.compareTo(valor) < 0) {
                if (izdo == null) {
                    izdo = new BST();
                }
                izdo.insertarImp(emp, this);
            } else if (emp.compareTo(valor) > 0) {
                if (dcho == null) {
                    dcho = new BST();
                }
                dcho.insertarImp(emp, this);
            } else {
                throw new RuntimeException("Insertando elemento duplicado");
            }
        }
    }

    @Override
    public boolean existe(int id) {
        if (valor != null) {
            if (id == valor.getId()) {
                return true;
            } else if (id < valor.getId() && izdo != null) {
                return izdo.existe(id);
            } else if (id > valor.getId() && dcho != null) {
                return dcho.existe(id);
            }
        }
        return false;
    }

    @Override
    public void eliminar(int id) {
        eliminarImpl(id);
    }

    private void eliminarImpl(int id) {
        if (valor != null) {
            if (id == valor.getId()) {
                eliminarNodoActual();
            } else if (id < valor.getId() && izdo != null) {
                izdo.eliminar(id);
            } else if (id > valor.getId() && dcho != null) {
                dcho.eliminar(id);
            }
        }
    }

    private void eliminarNodoActual() {
        if (izdo != null && dcho != null) {
            // Caso 1: Nodo con dos hijos
            BST sucesor = dcho.encontrarMinimo();
            valor = sucesor.valor;
            dcho.eliminar(valor.getId());
        } else if (izdo != null || dcho != null) {
            // Caso 2: Nodo con un hijo
            BST hijo = (izdo != null) ? izdo : dcho;
            if (padre != null) {
                reemplazarNodoPorHijo(hijo);
            } else {
                copiarNodo(hijo);
            }
        } else {
            // Caso 3: Nodo sin hijos
            eliminarNodoHoja();
        }
    }

    private void reemplazarNodoPorHijo(BST hijo) {
        if (this == padre.izdo) {
            padre.izdo = hijo;
        } else {
            padre.dcho = hijo;
        }
        hijo.padre = padre;
    }

    private void copiarNodo(BST nodo) {
        valor = nodo.valor;
        izdo = nodo.izdo;
        dcho = nodo.dcho;
    }

    private void eliminarNodoHoja() {
        if (padre != null) {
            if (this == padre.izdo) {
                padre.izdo = null;
            } else {
                padre.dcho = null;
            }
        } else {
            valor = null;
        }
    }

    private BST encontrarMinimo() {
        if (izdo == null) {
            return this;
        } else {
            return izdo.encontrarMinimo();
        }
    }

    // Métodos de recorrido inOrden, preOrden y postOrden
    public void inOrden() {
        if (izdo != null) {
            izdo.inOrden();
        }
        LOGGER.info("{}", valor);
        if (dcho != null) {
            dcho.inOrden();
        }
    }

    public void preOrden() {
        LOGGER.info("{}", valor);
        if (izdo != null) {
            izdo.preOrden();
        }
        if (dcho != null) {
            dcho.preOrden();
        }
    }

    public void postOrden() {
        if (izdo != null) {
            izdo.postOrden();
        }
        if (dcho != null) {
            dcho.postOrden();
        }
        LOGGER.info("{}", valor);
    }
}
