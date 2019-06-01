package server;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class HttpServer extends Thread {
    private Socket socket;
    private Scanner scanner;
    private PrintWriter out;

    public HttpServer(Socket socket) throws IOException {
        this.socket = socket;
        InputStream inputStream = socket.getInputStream();
        OutputStream writerStream = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(writerStream);
        Reader reader = new InputStreamReader(inputStream);

        this.scanner = new Scanner(reader);
        this.out = new PrintWriter(writer);
    }

    @Override
    public void run() {
        String method = scanner.next();
        String requestUrl = scanner.next();
        File file = new File(requestUrl.substring(1));

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            DataOutputStream binaryOut = new DataOutputStream(socket.getOutputStream());
            binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
            binaryOut.writeBytes("Content-Type: image/png\r\n");
            binaryOut.writeBytes("Content-Length: " + data.length);
            binaryOut.writeBytes("\r\n\r\n");
            binaryOut.write(data);
            binaryOut.close();
            System.out.println("Http request get file: " + file + " done.");
        } catch (IOException e) {
            out.println("HTTP/1.0 404 OK");
            out.close();
            System.out.println("Http request get file: " + file + " Not Found");
        } finally {
            out.close();
            scanner.close();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
