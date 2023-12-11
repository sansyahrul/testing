import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server is running and waiting for clients...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);

                // Handle client in a separate thread
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())
        ) {
            while (true) {
                // Receive input from the client
                PermutationRequest request = (PermutationRequest) ois.readObject();

                // Calculate permutation
                int result = calculatePermutation(request.getN(), request.getR());

                // Send the result back to the client
                oos.writeInt(result);
                oos.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected: " + socket);
        }
    }

    private static int calculatePermutation(int n, int r) {
        // Implement your permutation calculation logic here
        // For simplicity, this example uses a factorial-based approach
        int result = factorial(n) / factorial(n - r);
        return result;
    }

    private static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
