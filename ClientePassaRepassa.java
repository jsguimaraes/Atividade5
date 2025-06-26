import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientePassaRepassa {
    // Altere para o IP do servidor se estiver em outra máquina
    private static final String SERVER_IP = "127.0.0.1"; 
    private static final int SERVER_PORT = 10000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            System.out.println("Conectado ao servidor 'Passa ou Repassa'.");

            // Thread para escutar mensagens do servidor
            ServerListener listener = new ServerListener(socket.getInputStream());
            new Thread(listener).start();

            // Thread principal para enviar mensagens para o servidor
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 Scanner scanner = new Scanner(System.in)) {
                
                while (socket.isConnected()) {
                    if (scanner.hasNextLine()) {
                        String input = scanner.nextLine();
                        out.println(input);
                    }
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + SERVER_IP);
        } catch (IOException e) {
            System.err.println("Não foi possível conectar ao servidor. Verifique se ele está no ar.");
        }
    }
}

class ServerListener implements Runnable {
    private BufferedReader in;

    public ServerListener(InputStream inputStream) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        try {
            String msgDoServidor;
            while ((msgDoServidor = in.readLine()) != null) {
                String[] parts = msgDoServidor.split(":", 2);
                String command = parts[0];
                String payload = parts.length > 1 ? parts[1] : "";

                switch (command) {
                    case "PERGUNTA":
                        System.out.println("\n------------------------------");
                        System.out.println(payload.replace("\\n", "\n"));
                        break;
                    case "MSG":
                        System.out.println(">> Servidor: " + payload);
                        break;
                    case "JOGAR":
                        System.out.println("\n>>> SUA VEZ: " + payload);
                        System.out.print(">>> Sua jogada: ");
                        break;
                    case "AGUARDE":
                    case "PLACAR":
                    case "RESULTADO":
                        System.out.println("--- " + payload + " ---");
                        break;
                    case "FIM_JOGO":
                        System.out.println("\n############################");
                        System.out.println("### " + payload + " ###");
                        System.out.println("############################");
                        break;
                    case "NOVA_PARTIDA":
                        System.out.println("\n? " + payload);
                        System.out.print("? Sua resposta (S/N): ");
                        break;
                    default:
                        System.out.println(msgDoServidor);
                }
            }
        } catch (IOException e) {
            System.out.println("\nConexão com o servidor foi perdida. O jogo terminou.");
        } finally {
            System.out.println("Pressione Enter para sair.");
        }
    }
}