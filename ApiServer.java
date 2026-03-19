import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ApiServer {
    private static HttpServer server = null;

    public static void startServer() {
        if (server != null) {
            System.out.println("⚠️ Server is already running!");
            return;
        }
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/api/students", new StudentsHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("\n✅ REST API Server is successfully running at: http://localhost:8080/api/students");
            System.out.println("   (You can open this URL in your web browser or hit it with curl or React)");
        } catch (IOException e) {
            System.out.println("❌ Failed to start server: " + e.getMessage());
        }
    }

    static class StudentsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // Enable CORS for frontend apps

            if ("GET".equals(exchange.getRequestMethod())) {
                String response = getStudentsJson();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "{\"error\": \"Method not supported\"}";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private String getStudentsJson() {
            StringBuilder json = new StringBuilder("[\n");
            String sql = "SELECT * FROM students";
            try (Connection conn = DatabaseHelper.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        json.append(",\n");
                    }
                    json.append("  {\n")
                        .append("    \"id\": \"").append(rs.getString("id")).append("\",\n")
                        .append("    \"name\": \"").append(rs.getString("name")).append("\",\n")
                        .append("    \"branch\": \"").append(rs.getString("branch")).append("\"\n")
                        .append("  }");
                    first = false;
                }
            } catch (Exception e) {
                return "[{\"error\": \"" + e.getMessage() + "\"}]";
            }
            json.append("\n]");
            return json.toString();
        }
    }
}
