package com.example.gestaodeeventos.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
                createTablesIfNotExist();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    private static void createTablesIfNotExist() {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cpf TEXT NOT NULL UNIQUE, " +
                "cep TEXT NOT NULL, " +
                "nome TEXT NOT NULL, " +
                "email TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL, " +
                "data_nascimento DATE NOT NULL" +
                ");";

        String createOrganizadorTableSQL = "CREATE TABLE IF NOT EXISTS organizador (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FOREIGN KEY (id) REFERENCES user(id)" +
                ");";

        String createInstituicaoTableSQL = "CREATE TABLE IF NOT EXISTS instituicao (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cnpj TEXT NOT NULL UNIQUE, " +
                "estado TEXT NOT NULL, " +
                "cidade TEXT NOT NULL, " +
                "bairro TEXT NOT NULL, " +
                "rua TEXT NOT NULL, " +
                "numeroResidencial INTEGER NOT NULL, " +
                "telefone INTEGER NOT NULL, " +
                "email TEXT NOT NULL" +
                ");";

        String createCategoriaTableSQL = "CREATE TABLE IF NOT EXISTS categoria (" +
                "nome TEXT PRIMARY KEY, " +
                "descricao TEXT" +
                ");";

        String createEventoTableSQL = "CREATE TABLE IF NOT EXISTS evento (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "expectativaParticipantes INTEGER NOT NULL, " +
                "mapaURL TEXT NOT NULL, " +
                "data DATE NOT NULL, " +
                "modalidade TEXT NOT NULL, " +
                "instituicao_id INTEGER NOT NULL, " +
                "categoria_nome TEXT NOT NULL, " +
                "FOREIGN KEY (instituicao_id) REFERENCES instituicao(id), " +
                "FOREIGN KEY (categoria_nome) REFERENCES categoria(nome)" +
                ");";

        String createOrganizadorEventoTableSQL = "CREATE TABLE IF NOT EXISTS organizador_evento (" +
                "organizador_id INTEGER NOT NULL, " +
                "evento_id INTEGER NOT NULL, " +
                "PRIMARY KEY (organizador_id, evento_id), " +
                "FOREIGN KEY (organizador_id) REFERENCES organizador(id), " +
                "FOREIGN KEY (evento_id) REFERENCES evento(id)" +
                ");";

        String createUserEventoTableSQL = "CREATE TABLE IF NOT EXISTS user_evento (" +
                "user_id INTEGER NOT NULL, " +
                "evento_id INTEGER NOT NULL, " +
                "PRIMARY KEY (user_id, evento_id), " +
                "FOREIGN KEY (user_id) REFERENCES user(id), " +
                "FOREIGN KEY (evento_id) REFERENCES evento(id)" +
                ");";

        String createInscricaoTableSQL = "CREATE TABLE IF NOT EXISTS inscricao (" +
                "user_id INTEGER NOT NULL, " +
                "evento_id INTEGER NOT NULL, " +
                "PRIMARY KEY (user_id, evento_id), " +
                "FOREIGN KEY (user_id) REFERENCES user(id), " +
                "FOREIGN KEY (evento_id) REFERENCES evento(id)" +
                ");";

        String createAtividadeTableSQL = "CREATE TABLE IF NOT EXISTS atividade (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "data DATE NOT NULL, " +
                "local TEXT, " +
                "evento_id INTEGER NOT NULL, " +
                "FOREIGN KEY (evento_id) REFERENCES evento(id)" +
                ");";

        String createColaboradorTableSQL = "CREATE TABLE IF NOT EXISTS colaborador (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FOREIGN KEY (id) REFERENCES user(id)" +
                ");";

        String createAtividadeColaboradorTableSQL = "CREATE TABLE IF NOT EXISTS atividade_colaborador (" +
                "atividade_id INTEGER NOT NULL, " +
                "colaborador_id INTEGER NOT NULL, " +
                "PRIMARY KEY (atividade_id, colaborador_id), " +
                "FOREIGN KEY (atividade_id) REFERENCES atividade(id), " +
                "FOREIGN KEY (colaborador_id) REFERENCES colaborador(id)" +
                ");";

        String createCertificadoTableSQL = "CREATE TABLE IF NOT EXISTS certificado (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "inscricao_user_id INTEGER NOT NULL, " +
                "inscricao_evento_id INTEGER NOT NULL, " +
                "atividade_id INTEGER NOT NULL, " +
                "FOREIGN KEY (inscricao_user_id, inscricao_evento_id) REFERENCES inscricao(user_id, evento_id), " +
                "FOREIGN KEY (atividade_id) REFERENCES atividade(id)" +
                ");";

        String createFeedbackTableSQL = "CREATE TABLE IF NOT EXISTS feedback (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "evento_id INTEGER NOT NULL, " +
                "comentario TEXT NOT NULL, " +
                "FOREIGN KEY (user_id) REFERENCES user(id), " +
                "FOREIGN KEY (evento_id) REFERENCES evento(id)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTableSQL);
            stmt.execute(createOrganizadorTableSQL);
            stmt.execute(createInstituicaoTableSQL);
            stmt.execute(createCategoriaTableSQL);
            stmt.execute(createEventoTableSQL);
            stmt.execute(createOrganizadorEventoTableSQL);
            stmt.execute(createUserEventoTableSQL);
            stmt.execute(createInscricaoTableSQL);
            stmt.execute(createAtividadeTableSQL);
            stmt.execute(createColaboradorTableSQL);
            stmt.execute(createAtividadeColaboradorTableSQL);
            stmt.execute(createCertificadoTableSQL);
            stmt.execute(createFeedbackTableSQL);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static boolean isOrganizer(int userId) {
        String query = "SELECT COUNT(*) FROM organizador WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
