import  java.sql.*;
public  class  DatabaseConnection{

    private Connection c;

    public DatabaseConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MariaDB non trouvé");
            c = null;
            return;
        }
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/DUEL_SUR_LA_TOILE","user","user");
            System.out.println("Connexion BD réussie !");
        } catch (SQLException ex) {
            System.out.println("Echec de connexion à MariaDB");
            System.out.println("Msg:"+ex.getMessage ()+ex.getErrorCode ());
            c = null;
            return;
        }
    }
}