package modelo;

import java.io.*;
import java.sql.*;

public class Letra {

    private final static String url = "jdbc:mysql://localhost:3306/spotify";
    private final static String user = "root";
    private final static String password = "";
    private final static String rutaA = "C:\\Users\\jesug\\OneDrive\\Documentos\\Clase\\1º DAM (2º Curso)\\Prog\\ProyectoFinal\\src\\main\\java\\modelo\\letra.txt";

    private Statement state;

    private ResultSet rs;

    private String id;
    private String ruta;
    private String idCancion;

    public Letra() {



    }

    public Letra(String id, String ruta, String idCancion) {

        this.id = id;
        this.ruta = ruta;
        this.idCancion = idCancion;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(String idCancion) {
        this.idCancion = idCancion;
    }

    public void consultas() throws IOException {

        Connection conex = null;

        String nombreBD = "SELECT * FROM letra";

        File f = null;

        FileWriter fw = null;

        BufferedWriter bw = null;

        PrintWriter pw = null;

        try {

            f = new File(rutaA);

            fw = new FileWriter(f);

            bw = new BufferedWriter(fw);

            pw = new PrintWriter(bw);

            conex = conexion();

            if (conex != null) {

                leerLetra();

                state = conex.createStatement();

                rs = state.executeQuery(

                        nombreBD

                );

                while (rs.next()) {

                    pw.print(rs.getString("id") + ";");
                    pw.print(rs.getString("ruta") + ";");
                    pw.print(rs.getString("cancion_id") + ";");
                    pw.println();

                    pw.flush();

                }

            }

        } catch (SQLException | FileNotFoundException sqle) {

            System.out.println(sqle.getMessage());

        } finally {

            try {

                if (fw != null && pw != null) {

                    fw.close();

                    pw.close();

                }

            } catch (IOException ioe) {

                System.out.println(ioe.getMessage());

            }

        }

    }

    public void leerLetra() {

        Connection conex = null;

        String linea = "";
        String ruta;
        String update;

        File f = null;

        FileReader fr = null;

        BufferedReader br = null;

        try {

            conex = conexion();

            if (conex != null) {

                ruta = "C:\\Users\\jesug\\OneDrive\\Documentos\\Clase\\1º DAM (2º Curso)\\Prog\\ProyectoFinal\\src\\main\\java\\modelo\\1.txt";

                f = new File(ruta);

                fr = new FileReader(f);

                br = new BufferedReader(fr);

                for (int j = 0; j < f.length(); j++) {

                    linea += br.readLine();

                    if (linea != null) {

                        if (f.length() < 255) {

                            update = "UPDATE letra SET ruta = '" + linea + "' WHERE id = 1";

                            state = conex.createStatement();

                            state.executeUpdate(

                                    update

                            );

                        }

                    }

                }

            }

        } catch (SQLException | FileNotFoundException sqle) {

            System.out.println(sqle.getMessage());

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (fr != null) {

                    fr.close();

                }

            } catch (IOException e) {

                System.out.println("Fallo en la entrada/salida.");

            }

        }

    }

    private Connection conexion() throws SQLException {

        Connection conex = null;

        try {

            conex = DriverManager.getConnection(url, user, password);

        } catch (SQLException sqle) {

            System.out.println(sqle.getMessage());

        }

        return conex;

    }

}
