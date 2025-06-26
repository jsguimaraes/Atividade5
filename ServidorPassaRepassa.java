import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorPassaRepassa {
    private static final int PORTA = 10000;
    private static final int PONTOS_PARA_VENCER = 30;
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket servidorSocket = new ServerSocket(PORTA);
        System.out.println("Servidor 'Passa ou Repassa' iniciado na porta " + PORTA);
        System.out.println("Aguardando a conexão de dois jogadores...");

        try {
            while (true) {
                // Implemente um Servidor de Rede que receba dois Clientes (jogadores) na porta 10000. 
                Socket jogador1Socket = servidorSocket.accept();
                System.out.println("Jogador 1 conectou. Aguardando Jogador 2...");
                Socket jogador2Socket = servidorSocket.accept();
                System.out.println("Jogador 2 conectou. Iniciando nova partida.");

                Partida partida = new Partida(jogador1Socket, jogador2Socket);
                pool.execute(partida);
            }
        } finally {
            servidorSocket.close();
            pool.shutdown();
        }
    }

    static class Partida implements Runnable {
        private Socket j1Socket, j2Socket;
        private PrintWriter outJ1, outJ2;
        private BufferedReader inJ1, inJ2;
        private int pontuacaoJ1, pontuacaoJ2;
        private String nomeJ1, nomeJ2;

        public Partida(Socket j1, Socket j2) {
            this.j1Socket = j1;
            this.j2Socket = j2;
        }

        @Override
        public void run() {
            try {
                setup();
                boolean jogarNovamente;
                do {
                    iniciarPartida();
                    declararVencedor();
                    jogarNovamente = perguntarSeQuerJogarNovamente();
                } while (jogarNovamente);

            } catch (IOException e) {
                System.out.println("Um jogador desconectou. A partida foi encerrada.");
            } finally {
                try {
                    j1Socket.close();
                    j2Socket.close();
                } catch (IOException e) {
                    // Silencioso
                }
            }
        }

        private void setup() throws IOException {
            outJ1 = new PrintWriter(j1Socket.getOutputStream(), true);
            inJ1 = new BufferedReader(new InputStreamReader(j1Socket.getInputStream()));
            outJ2 = new PrintWriter(j2Socket.getOutputStream(), true);
            inJ2 = new BufferedReader(new InputStreamReader(j2Socket.getInputStream()));

            // O Servidor será o administrador do jogo e só iniciará uma partida após dois clientes enviarem via rede o seu nome. 
            outJ1.println("MSG:BEM-VINDO! Por favor, digite seu nome:");
            nomeJ1 = inJ1.readLine();
            outJ2.println("MSG:BEM-VINDO! Por favor, digite seu nome:");
            nomeJ2 = inJ2.readLine();
            
            outJ1.println("MSG:Bem-vindo, " + nomeJ1 + "! Você jogará contra " + nomeJ2 + ".");
            outJ2.println("MSG:Bem-vindo, " + nomeJ2 + "! Você jogará contra " + nomeJ1 + ".");
        }
        
        private void iniciarPartida() throws IOException {
            this.pontuacaoJ1 = 0;
            this.pontuacaoJ2 = 0;
            GerenciadorPerguntas gerenciador = new GerenciadorPerguntas();
            // O Servidor sorteará o primeiro jogador para iniciar a rodada. 
            int jogadorDaVez = new Random().nextInt(2); 

            // Vence o jogo quem somar 30 pontos primeiro. 
            while (pontuacaoJ1 < PONTOS_PARA_VENCER && pontuacaoJ2 < PONTOS_PARA_VENCER) {
                enviarPlacar();
                Pergunta pergunta = gerenciador.proximaPergunta();
                
                enviarParaAmbos("PERGUNTA:" + pergunta.toString());

                if (jogadorDaVez == 0) {
                    rodada(outJ1, inJ1, outJ2, inJ2, pergunta, nomeJ1, 1);
                } else {
                    rodada(outJ2, inJ2, outJ1, inJ1, pergunta, nomeJ2, 2);
                }
                
                // Os jogadores alternam entre as rodadas. 
                jogadorDaVez = (jogadorDaVez + 1) % 2;
                pausa();
            }
        }
        
        private void rodada(PrintWriter outAtivo, BufferedReader inAtivo, PrintWriter outOponente, BufferedReader inOponente, Pergunta p, String nomeAtivo, int idAtivo) throws IOException {
            outAtivo.println("JOGAR:Sua vez! Envie 'RESPONDER - N°' ou 'PASSA'.");
            outOponente.println("AGUARDE:Aguarde a jogada de " + nomeAtivo + ".");
            
            String resposta = inAtivo.readLine();
            String[] partes = resposta.split("\\s+", 2);
            String comando = partes[0].toUpperCase();

            if (comando.equals("RESPONDER")) {
                int opcao = extrairOpcao(partes[1]);
                processarResposta(p, opcao, idAtivo, 5, -5); // Resposta de primeira
            } else if (comando.equals("PASSA")) {
                enviarParaAmbos("MSG:" + nomeAtivo + " passou a vez!");
                outOponente.println("JOGAR:A pergunta foi passada para você! Envie 'RESPONDER - N°' ou 'REPASSA'.");
                outAtivo.println("AGUARDE:Você passou. Aguarde a jogada do oponente.");

                String respOponente = inOponente.readLine();
                partes = respOponente.split("\\s+", 2);
                comando = partes[0].toUpperCase();

                if (comando.equals("RESPONDER")) {
                    int opcao = extrairOpcao(partes[1]);
                    processarResposta(p, opcao, (idAtivo == 1 ? 2 : 1), 7, -5); // Resposta a um PASSA
                } else if (comando.equals("REPASSA")) {
                    enviarParaAmbos("MSG:A pergunta foi REPASSADA! " + nomeAtivo + " deve responder.");
                    outAtivo.println("JOGAR:Você é obrigado a responder! 'RESPONDER - N°'.");
                    outOponente.println("AGUARDE:Oponente repassou. Aguarde a resposta.");
                    
                    String respostaFinal = inAtivo.readLine();
                    partes = respostaFinal.split("\\s+", 2);
                    int opcao = extrairOpcao(partes[1]);
                    processarResposta(p, opcao, idAtivo, 10, -3); // Resposta a um REPASSA
                }
            }
        }
        
        private int extrairOpcao(String parte) {
            // Extrai o número da opção, ex: de "Opção 1" ou "- 1"
            return Integer.parseInt(parte.replaceAll("[^0-9]", ""));
        }

        private void processarResposta(Pergunta p, int respostaJogador, int idJogador, int pontosAcerto, int pontosErro) {
            String autorDaJogada = (idJogador == 1) ? nomeJ1 : nomeJ2;
            if (p.isRespostaCorreta(respostaJogador)) {
                if (idJogador == 1) pontuacaoJ1 += pontosAcerto; else pontuacaoJ2 += pontosAcerto;
                enviarParaAmbos("RESULTADO:" + autorDaJogada + " ACERTOU! (+" + pontosAcerto + " pontos)");
            } else {
                 if (idJogador == 1) pontuacaoJ1 += pontosErro; else pontuacaoJ2 += pontosErro;
                 enviarParaAmbos("RESULTADO:" + autorDaJogada + " ERROU! (" + pontosErro + " pontos)");
            }
        }
        
        private void enviarPlacar() {
            String placar = "PLACAR:" + nomeJ1 + ": " + pontuacaoJ1 + " | " + nomeJ2 + ": " + pontuacaoJ2;
            enviarParaAmbos(placar);
        }
        
        private void declararVencedor() {
            enviarPlacar();
            String vencedorMsg;
            // O Servidor deve informar aos jogadores o fim do jogo e também o jogador vencedor. 
            if (pontuacaoJ1 >= PONTOS_PARA_VENCER) {
                vencedorMsg = "FIM_JOGO:" + nomeJ1 + " venceu a partida!";
            } else if (pontuacaoJ2 >= PONTOS_PARA_VENCER) {
                vencedorMsg = "FIM_JOGO:" + nomeJ2 + " venceu a partida!";
            } else {
                 vencedorMsg = "FIM_JOGO:A partida terminou inesperadamente."; // Fallback
            }
            enviarParaAmbos(vencedorMsg);
        }

        private boolean perguntarSeQuerJogarNovamente() throws IOException {
            enviarParaAmbos("NOVA_PARTIDA:Deseja jogar novamente? (S/N)");
            String resp1 = inJ1.readLine();
            String resp2 = inJ2.readLine();
            if (resp1 != null && resp1.equalsIgnoreCase("S") && resp2 != null && resp2.equalsIgnoreCase("S")) {
                enviarParaAmbos("MSG:Ambos concordaram! Uma nova partida vai começar!");
                pausa();
                return true;
            } else {
                enviarParaAmbos("MSG:Um dos jogadores não quis continuar. Fim de jogo. Obrigado por jogar!");
                return false;
            }
        }

        private void enviarParaAmbos(String mensagem) {
            outJ1.println(mensagem);
            outJ2.println(mensagem);
        }
        
        private void pausa() {
            try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}