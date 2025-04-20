package Persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

import Dados.Agencia;

public class PersistAgencias {
    private static final String AGENCIAS_FILE = "agencias.dat";
    
    public static void salvarAgencias(List<Agencia> agencias) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(AGENCIAS_FILE))) {
            oos.writeObject(agencias);
        } catch (IOException e) {
            System.err.println("Erro ao salvar agências: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Agencia> carregarAgencias() {
        File f = new File(AGENCIAS_FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<Agencia>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar agências: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
