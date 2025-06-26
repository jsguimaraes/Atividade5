import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Classe para representar uma única pergunta
class Pergunta {
    private String enunciado;
    private String[] opcoes;
    private int respostaCorreta; // índice da opção correta

    public Pergunta(String enunciado, String[] opcoes, int respostaCorreta) {
        this.enunciado = enunciado;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }

    public boolean isRespostaCorreta(int resposta) {
        return resposta == this.respostaCorreta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(enunciado).append("\n");
        for (int i = 0; i < opcoes.length; i++) {
            sb.append(i).append(") ").append(opcoes[i]).append("\n");
        }
        return sb.toString();
    }
}

// Classe para gerenciar a lista de perguntas
public class GerenciadorPerguntas {
    private List<Pergunta> perguntas;
    private int perguntaAtualIndex;

    public GerenciadorPerguntas() {
        this.perguntas = new ArrayList<>();
        this.perguntaAtualIndex = -1;
        inicializarPerguntas();
        embaralharPerguntas(); // As perguntas serão selecionadas de modo aleatório
    }

    // As perguntas deverão ser selecionadas de modo aleatório, sem repetição a cada rodada;
    private void inicializarPerguntas() {
        
        // --- Perguntas sobre Java ---
        perguntas.add(new Pergunta("Qual palavra-chave em Java é usada para criar uma subclasse?", new String[]{"extends", "implements", "super", "new"}, 0));
        perguntas.add(new Pergunta("O que o método 'hashCode()' da classe Object retorna?", new String[]{"Um endereço de memória", "Um código de identificação inteiro", "Uma string única", "O nome da classe"}, 1));
        perguntas.add(new Pergunta("Qual das seguintes coleções NÃO permite elementos duplicados em Java?", new String[]{"ArrayList", "LinkedList", "HashSet", "Vector"}, 2));
        perguntas.add(new Pergunta("Em Java, qual modificador de acesso torna um membro visível apenas dentro da própria classe?", new String[]{"public", "protected", "default", "private"}, 3));
        perguntas.add(new Pergunta("O que é JRE?", new String[]{"Java Repeating Environment", "Java Runtime Environment", "Java Real-time Execution", "Java Resource Editor"}, 1));
        perguntas.add(new Pergunta("Qual interface deve ser implementada para que uma classe possa ser executada como uma thread?", new String[]{"Runnable", "Serializable", "Cloneable", "Comparable"}, 0));
        perguntas.add(new Pergunta("A instrução 'finally' em um bloco try-catch-finally é sempre executada?", new String[]{"Sim, sempre", "Apenas se uma exceção ocorrer", "Apenas se nenhuma exceção ocorrer", "Não, é opcional e raramente executa"}, 0));
        perguntas.add(new Pergunta("Qual é o valor padrão de uma variável de instância do tipo boolean em Java?", new String[]{"true", "false", "null", "0"}, 1));
        perguntas.add(new Pergunta("O que a palavra-chave 'static' significa para um método em Java?", new String[]{"O método só pode ser chamado uma vez", "O método pertence à instância da classe", "O método pode ser modificado em tempo de execução", "O método pertence à classe, não a uma instância"}, 3));
        perguntas.add(new Pergunta("Qual classe é a superclasse de todas as outras classes em Java?", new String[]{"System", "Class", "Object", "Main"}, 2));
        perguntas.add(new Pergunta("Como se declara uma variável que não pode ser modificada em Java?", new String[]{"static", "const", "final", "private"}, 2));
        perguntas.add(new Pergunta("Qual é a principal diferença entre '==' e '.equals()' para objetos em Java?", new String[]{"Nenhuma, são idênticos", "'==' compara referências, '.equals()' compara conteúdo", "'==' compara conteúdo, '.equals()' compara referências", "'==' é mais rápido que '.equals()'"}, 1));
        perguntas.add(new Pergunta("O que é 'garbage collection' (coleta de lixo) em Java?", new String[]{"Um processo para limpar o código-fonte", "Um processo para otimizar loops", "Um processo automático de gerenciamento de memória", "Uma ferramenta para deletar arquivos temporários"}, 2));
        perguntas.add(new Pergunta("Qual dos seguintes não é um tipo primitivo em Java?", new String[]{"int", "float", "String", "char"}, 2));
        perguntas.add(new Pergunta("Para que serve a anotação '@Override'?", new String[]{"Para indicar que um método é obsoleto", "Para indicar que um método sobrescreve um método da superclasse", "Para criar uma sobrecarga de método", "Para definir o autor do código"}, 1));

        // --- Perguntas sobre Lógica de Programação e Algoritmos ---
        perguntas.add(new Pergunta("Qual estrutura de dados opera no sistema LIFO (Last-In, First-Out)?", new String[]{"Fila (Queue)", "Pilha (Stack)", "Lista (List)", "Árvore (Tree)"}, 1));
        perguntas.add(new Pergunta("Qual estrutura de dados opera no sistema FIFO (First-In, First-Out)?", new String[]{"Fila (Queue)", "Pilha (Stack)", "Grafo (Graph)", "Tabela Hash (Hash Table)"}, 0));
        perguntas.add(new Pergunta("O que é recursividade em programação?", new String[]{"Uma função que chama a si mesma", "Um loop que nunca termina", "Uma variável que muda de tipo", "Um tipo especial de classe"}, 0));
        perguntas.add(new Pergunta("Qual é a complexidade de tempo de uma busca binária em um array ordenado?", new String[]{"O(1)", "O(log n)", "O(n)", "O(n^2)"}, 1));
        perguntas.add(new Pergunta("Qual algoritmo de ordenação tem a pior complexidade de caso de O(n^2)?", new String[]{"Merge Sort", "Quick Sort", "Bubble Sort", "Heap Sort"}, 2));
        perguntas.add(new Pergunta("O que significa 'pseudocódigo'?", new String[]{"Um código que não funciona", "Uma linguagem de programação antiga", "Uma forma de escrever algoritmos que se assemelha a uma linguagem de programação", "Um código criptografado"}, 2));
        perguntas.add(new Pergunta("Em um loop 'for (int i=0; i<5; i++)', quantas vezes o corpo do loop será executado?", new String[]{"4 vezes", "5 vezes", "6 vezes", "Infinitas vezes"}, 1));
        perguntas.add(new Pergunta("Qual operador lógico representa 'OU'?", new String[]{"&&", "||", "!", "=="}, 1));
        perguntas.add(new Pergunta("Qual é o propósito de uma instrução 'switch-case'?", new String[]{"Criar um loop condicional", "Substituir uma série de 'if-else if'", "Declarar múltiplas variáveis", "Executar operações matemáticas"}, 1));
        perguntas.add(new Pergunta("O que é uma variável 'flag'?", new String[]{"Uma variável que armazena texto", "Uma constante matemática", "Uma variável booleana para controlar o fluxo de um programa", "Um tipo de erro de compilação"}, 2));

        // --- Perguntas sobre Redes e Protocolos ---
        perguntas.add(new Pergunta("O que significa a sigla HTTP?", new String[]{"HyperText Transfer Protocol", "High-Tech Transfer Protocol", "HyperText Transmission Protocol", "HyperText Transfer Package"}, 0));
        perguntas.add(new Pergunta("Qual protocolo é usado para enviar e-mails?", new String[]{"FTP", "HTTP", "SMTP", "POP3"}, 2));
        perguntas.add(new Pergunta("Qual protocolo é considerado 'não orientado à conexão'?", new String[]{"TCP", "IP", "UDP", "HTTP"}, 2));
        perguntas.add(new Pergunta("Em qual camada do modelo OSI os roteadores operam principalmente?", new String[]{"Física", "Enlace", "Rede", "Transporte"}, 2));
        perguntas.add(new Pergunta("O que é um endereço IP?", new String[]{"Um identificador para uma página da web", "Um número que identifica unicamente um dispositivo em uma rede", "Um tipo de cabo de rede", "O nome de um computador"}, 1));
        perguntas.add(new Pergunta("A porta padrão para HTTPS é:", new String[]{"80", "21", "22", "443"}, 3));
        perguntas.add(new Pergunta("O que o comando 'ping' faz?", new String[]{"Baixa um arquivo da internet", "Mede a latência e a conectividade entre dois pontos da rede", "Mostra as configurações de rede do computador", "Abre uma conexão remota"}, 1));
        perguntas.add(new Pergunta("O que é DNS?", new String[]{"Dynamic Network System", "Data Naming Service", "Domain Name System", "Digital Naming Standard"}, 2));
        perguntas.add(new Pergunta("Qual é a principal função de um Firewall?", new String[]{"Acelerar a conexão com a internet", "Monitorar e controlar o tráfego de rede", "Armazenar senhas de forma segura", "Compactar arquivos para transmissão"}, 1));
        perguntas.add(new Pergunta("O que é um Socket em programação de redes?", new String[]{"Um tipo de conector físico", "Um protocolo de segurança", "Um ponto final de uma comunicação bidirecional entre dois programas na rede", "Um software de monitoramento"}, 2));

        // --- Perguntas sobre Banco de Dados e SQL ---
        perguntas.add(new Pergunta("Qual comando SQL é usado para extrair dados de um banco de dados?", new String[]{"GET", "OPEN", "SELECT", "EXTRACT"}, 2));
        perguntas.add(new Pergunta("Qual cláusula SQL é usada para filtrar resultados?", new String[]{"FILTER", "WHERE", "GROUP BY", "HAVING"}, 1));
        perguntas.add(new Pergunta("Qual comando é usado para adicionar um novo registro a uma tabela?", new String[]{"ADD", "INSERT INTO", "UPDATE", "CREATE"}, 1));
        perguntas.add(new Pergunta("O que é uma 'Primary Key' (Chave Primária)?", new String[]{"Uma senha para acessar a tabela", "Uma coluna que identifica unicamente cada registro na tabela", "A primeira coluna de qualquer tabela", "Um índice para ordenação"}, 1));
        perguntas.add(new Pergunta("Qual tipo de JOIN retorna todos os registros da tabela esquerda e os registros correspondentes da tabela direita?", new String[]{"INNER JOIN", "RIGHT JOIN", "FULL OUTER JOIN", "LEFT JOIN"}, 3));
        perguntas.add(new Pergunta("O que 'DDL' significa em SQL?", new String[]{"Data Definition Language", "Data Distribution Language", "Dynamic Data Logic", "Direct Data Link"}, 0));
        perguntas.add(new Pergunta("Qual comando SQL remove uma tabela inteira do banco de dados?", new String[]{"DELETE TABLE", "REMOVE TABLE", "DROP TABLE", "CLEAR TABLE"}, 2));
        perguntas.add(new Pergunta("Qual função de agregação SQL retorna o número de linhas?", new String[]{"SUM()", "MAX()", "COUNT()", "AVG()"}, 2));
        perguntas.add(new Pergunta("O que é um banco de dados NoSQL?", new String[]{"Um banco de dados que não usa SQL", "Um banco de dados que não armazena dados", "Um tipo de banco de dados não relacional", "Um banco de dados sem segurança"}, 2));
        perguntas.add(new Pergunta("O que a sigla 'ORM' significa?", new String[]{"Object-Relational Mapping", "Object-Resource Model", "Ordered-Record Management", "Object-Relational Model"}, 0));

        // --- Perguntas sobre Desenvolvimento Web (Frontend & Backend) ---
        perguntas.add(new Pergunta("O que significa HTML?", new String[]{"Hyper Text Markup Language", "High-level Textual Markup Language", "Hyperlink and Text Markup Language", "Home Tool Markup Language"}, 0));
        perguntas.add(new Pergunta("Qual linguagem é usada para estilizar páginas web?", new String[]{"HTML", "JavaScript", "CSS", "Python"}, 2));
        perguntas.add(new Pergunta("Qual das opções a seguir é um framework JavaScript popular para front-end?", new String[]{"Django", "React", "Laravel", "Spring"}, 1));
        perguntas.add(new Pergunta("O que significa a sigla 'API' em programação?", new String[]{"Application Programming Interface", "Advanced Program Integration", "Application Protocol Interface", "Automated Programming Instance"}, 0));
        perguntas.add(new Pergunta("O que é um 'framework' de backend?", new String[]{"Uma biblioteca de design de interface", "Um editor de código para servidores", "Uma estrutura que facilita o desenvolvimento de aplicações do lado do servidor", "Um tipo de servidor web"}, 2));
        perguntas.add(new Pergunta("Qual tag HTML é usada para criar um link?", new String[]{"<link>", "<a>", "<href>", "<url>"}, 1));
        perguntas.add(new Pergunta("O que é 'design responsivo'?", new String[]{"Um design que responde rapidamente aos cliques", "Um design que se adapta a diferentes tamanhos de tela", "Um design com muitas animações", "Um padrão de design de software"}, 1));
        perguntas.add(new Pergunta("JSON é um formato para...", new String[]{"Estilizar texto", "Definir a estrutura de uma página", "Armazenar e transportar dados", "Executar scripts"}, 2));
        perguntas.add(new Pergunta("Qual é a principal função do JavaScript em uma página web?", new String[]{"Definir a estrutura do conteúdo", "Controlar a aparência e o layout", "Adicionar interatividade e comportamento dinâmico", "Gerenciar o banco de dados"}, 2));
        perguntas.add(new Pergunta("Node.js permite executar JavaScript em qual ambiente?", new String[]{"Apenas no navegador", "No servidor", "Em dispositivos móveis", "Em sistemas embarcados"}, 1));
        
        // --- Perguntas sobre Conceitos Gerais de Software ---
        perguntas.add(new Pergunta("O que é 'Git'?", new String[]{"Um editor de texto", "Uma linguagem de programação", "Um sistema de controle de versão", "Um tipo de banco de dados"}, 2));
        perguntas.add(new Pergunta("Qual comando Git é usado para enviar alterações para um repositório remoto?", new String[]{"git pull", "git commit", "git push", "git clone"}, 2));
        perguntas.add(new Pergunta("O que é 'Software como um Serviço' (SaaS)?", new String[]{"Um software que você compra e instala", "Um modelo de licenciamento e entrega de software baseado em assinatura", "Um tipo de hardware", "Uma metodologia de desenvolvimento"}, 1));
        perguntas.add(new Pergunta("O que significa 'código aberto' (open source)?", new String[]{"Um código que só funciona em sistemas abertos", "Um código que é gratuito, mas não pode ser modificado", "Um código cujo código-fonte está disponível para qualquer pessoa ver, modificar e distribuir", "Um software sem interface gráfica"}, 2));
        perguntas.add(new Pergunta("O que é um 'bug' em software?", new String[]{"Uma funcionalidade secreta", "Um erro ou falha no código que causa um resultado inesperado", "Um comentário no código", "Uma medida de segurança"}, 1));
        perguntas.add(new Pergunta("O que é 'depuração' (debugging)?", new String[]{"O processo de escrever código novo", "O processo de encontrar e corrigir bugs", "O processo de compilar o código", "O processo de testar a interface do usuário"}, 1));
        perguntas.add(new Pergunta("O que é uma IDE?", new String[]{"Internal Development Engine", "Integrated Development Environment", "Interface Design Engine", "Independent Driver Extension"}, 1));
        perguntas.add(new Pergunta("Qual dos seguintes é um princípio do manifesto ágil?", new String[]{"Documentação abrangente sobre software funcional", "Seguir um plano sobre responder a mudanças", "Indivíduos e interações sobre processos e ferramentas", "Negociação de contratos sobre colaboração com o cliente"}, 2));
        perguntas.add(new Pergunta("O que é 'refatoração' de código?", new String[]{"Apagar e reescrever todo o código", "Mudar o nome das variáveis", "Adicionar novos recursos", "Melhorar a estrutura interna do código sem alterar seu comportamento externo"}, 3));
        perguntas.add(new Pergunta("O que é um teste de unidade?", new String[]{"Um teste que verifica o sistema inteiro", "Um teste manual feito pelo usuário final", "Um teste que verifica a menor parte testável de uma aplicação, isoladamente", "Um teste de desempenho da rede"}, 2));
        
        // --- Mais Perguntas de Java ---
        perguntas.add(new Pergunta("Qual destas classes é usada para ler dados de um arquivo em Java?", new String[]{"FileWriter", "FileInputStream", "FileCreator", "File"}, 1));
        perguntas.add(new Pergunta("O que é uma expressão lambda em Java?", new String[]{"Um tipo de comentário", "Uma forma de criptografia", "Uma função anônima", "Um tipo de loop"}, 2));
        perguntas.add(new Pergunta("Qual o resultado da expressão '\"Hello\" instanceof Object' em Java?", new String[]{"true", "false", "Erro de compilação", "Lança uma exceção"}, 0));
        perguntas.add(new Pergunta("Para que serve a classe 'StringBuilder' em Java?", new String[]{"Para criar interfaces gráficas", "Para manipulação eficiente de strings", "Para gerenciar threads", "Para se conectar a bancos de dados"}, 1));
        perguntas.add(new Pergunta("Em Java, uma classe declarada como 'abstract' pode ser instanciada?", new String[]{"Sim, com a palavra-chave 'new'", "Não, nunca", "Sim, mas apenas uma vez", "Apenas se tiver um construtor"}, 1));
        perguntas.add(new Pergunta("Qual o nome do pacote que contém as classes fundamentais de Java, como Object e String?", new String[]{"java.util", "java.io", "java.net", "java.lang"}, 3));
        perguntas.add(new Pergunta("O que é Polimorfismo?", new String[]{"A capacidade de um objeto ter múltiplas formas", "Herança de múltiplas classes", "Ocultar a complexidade do sistema", "A capacidade de uma classe ter apenas um método"}, 0));
        perguntas.add(new Pergunta("Qual é a diferença entre uma classe 'abstract' e uma 'interface' em Java 8?", new String[]{"Interfaces não podem ter métodos com implementação, classes abstratas sim", "Classes abstratas podem ter variáveis de instância, interfaces não", "Uma classe pode implementar várias interfaces, mas estender apenas uma classe abstrata", "Todas as alternativas estão corretas"}, 3));
        perguntas.add(new Pergunta("O que faz o método 'join()' de uma Thread?", new String[]{"Inicia a execução da thread", "Faz a thread atual esperar até que a outra thread termine", "Interrompe a execução da thread", "Verifica se a thread está ativa"}, 1));
        perguntas.add(new Pergunta("Qual estrutura de dados é representada pela classe 'HashMap'?", new String[]{"Lista encadeada", "Tabela de espalhamento (hash table)", "Árvore binária", "Pilha"}, 1));

        // --- Mais Perguntas de Lógica e Estruturas de Dados ---
        perguntas.add(new Pergunta("Qual o objetivo principal da Notação Big O?", new String[]{"Descrever a eficiência ou complexidade de um algoritmo", "Calcular o número exato de operações", "Medir o tempo de execução em segundos", "Definir o tamanho da memória usada"}, 0));
        perguntas.add(new Pergunta("Em uma árvore binária de busca, onde se encontra o menor valor?", new String[]{"Na raiz", "No nó mais à direita da sub-árvore direita", "No nó mais à esquerda da sub-árvore esquerda", "Em qualquer folha"}, 2));
        perguntas.add(new Pergunta("O que é um 'ponteiro nulo' (null pointer)?", new String[]{"Um ponteiro que aponta para o endereço zero", "Um ponteiro que não aponta para nenhum endereço de memória válido", "Um ponteiro que foi deletado", "Um erro de sintaxe"}, 1));
        perguntas.add(new Pergunta("Qual é a principal desvantagem de um array em comparação com uma lista encadeada?", new String[]{"Acesso lento aos elementos", "Tamanho fixo", "Não permite elementos duplicados", "Uso ineficiente de memória"}, 1));
        perguntas.add(new Pergunta("O algoritmo de Dijkstra é usado para resolver qual problema?", new String[]{"Ordenação de um array", "Encontrar o caminho mais curto em um grafo com pesos não-negativos", "Busca em profundidade", "Balanceamento de uma árvore"}, 1));
        perguntas.add(new Pergunta("O que é um 'deadlock' (impasse)?", new String[]{"Um erro de compilação", "Uma situação onde dois ou mais processos estão esperando um pelo outro indefinidamente", "Uma falha de hardware", "Um loop infinito"}, 1));
        perguntas.add(new Pergunta("Qual das seguintes opções NÃO é um paradigma de programação?", new String[]{"Orientado a Objetos", "Funcional", "Procedural", "Relacional"}, 3));
        perguntas.add(new Pergunta("O que é 'casting' ou 'type casting'?", new String[]{"Um método para criar objetos", "Converter uma variável de um tipo de dado para outro", "Apagar uma variável da memória", "Um operador matemático"}, 1));
        perguntas.add(new Pergunta("Um compilador faz o quê?", new String[]{"Executa o código linha por linha", "Traduz o código-fonte para código de máquina de uma só vez", "Interpreta e executa o código-fonte em tempo real", "Remove bugs do código automaticamente"}, 1));
        perguntas.add(new Pergunta("O que é um 'hash collision' (colisão de hash)?", new String[]{"Quando duas senhas são iguais", "Quando uma função hash produz o mesmo resultado para duas entradas diferentes", "Um erro ao calcular o hash", "Quando a tabela hash está cheia"}, 1));

        // --- Perguntas Variadas ---
        perguntas.add(new Pergunta("O que significa 'regex'?", new String[]{"Recurso Extra de GUI", "Regular Expression", "Registered Expert", "Related Execution"}, 1));
        perguntas.add(new Pergunta("Qual empresa originalmente desenvolveu a linguagem de programação Java?", new String[]{"Microsoft", "IBM", "Sun Microsystems", "Apple"}, 2));
        perguntas.add(new Pergunta("Linux é um(a)...", new String[]{"Linguagem de programação", "Navegador web", "Sistema operacional", "Hardware de computador"}, 2));
        perguntas.add(new Pergunta("O que é 'boilerplate code'?", new String[]{"Código que é escrito uma única vez", "Seções de código que precisam ser incluídas em muitos lugares com pouca ou nenhuma alteração", "O código principal de uma aplicação", "Código de teste"}, 1));
        perguntas.add(new Pergunta("Qual das opções abaixo é um sistema de build para projetos Java?", new String[]{"NPM", "Pip", "Maven", "Composer"}, 2));
        perguntas.add(new Pergunta("O que é encapsulamento em POO?", new String[]{"A ideia de agrupar dados e os métodos que operam nesses dados em uma única unidade (classe)", "Permitir que uma classe herde de outra", "A capacidade de um objeto ter múltiplas formas", "Criar múltiplas threads"}, 0));
        perguntas.add(new Pergunta("Em REST, qual método HTTP é tipicamente usado para atualizar um recurso existente?", new String[]{"GET", "POST", "PUT", "DELETE"}, 2));
        perguntas.add(new Pergunta("O que é 'localhost'?", new String[]{"Um servidor remoto", "Um nome de domínio para o próprio computador", "Um protocolo de rede", "Uma ferramenta de depuração"}, 1));
    }

    private void embaralharPerguntas() {
        Collections.shuffle(this.perguntas);
        this.perguntaAtualIndex = -1;
    }

    public Pergunta proximaPergunta() {
        perguntaAtualIndex++;
        // Garante que não se repitam até todas passarem
        if (perguntaAtualIndex >= perguntas.size()) {
            embaralharPerguntas();
            perguntaAtualIndex = 0;
        }
        return perguntas.get(perguntaAtualIndex);
    }
}