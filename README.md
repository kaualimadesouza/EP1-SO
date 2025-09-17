# EP1 Sistemas Operacionais

## Sumário

- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Compilar e Executar (javac, jar e java)](#como-compilar-e-executar-javac-jar-e-java)
- [Como Compilar e Executar (IntelliJ IDEA)](#como-compilar-e-executar-intellij-idea)

## Estrutura do Projeto

```yaml
├─ src/
│ └─ Escalonador.java
├─ programas/
│ ├─ file1.txt
│ └─ file2.txt
└─ README.md
```

- O código-fonte Java fica dentro da pasta `src/`.
- Os arquivos de texto ficam dentro da pasta `programas/`.
- Ao gerar o JAR, os arquivos da pasta `programas/` também serão incluídos.

---

## Como Compilar e Executar (javac, jar e java)

### 1. Compilar
No diretório raiz do projeto (onde ficam `src/` e `programas/`), execute:

```bash
javac -d out src/*.java
```
Isso cria a pasta `out/` com os arquivos `.class` compilados.

2. Gerar o JAR
Para empacotar tudo (classes + pasta `programas/`) em um JAR:

```bash
jar cfe escalonador.jar Escalonador -C out . -C . programas
```

`-C out .` adiciona os arquivos compilados ao JAR.

`-C . programas` adiciona a pasta `programas/` ao JAR.

3. Executar
```bash
java -jar escalonador.jar
```

## Como compilar e executar (IntelliJ IDEA)

1. **Abrir Estrutura do Projeto**  
   No menu principal, selecione **Arquivo > Estrutura do Projeto...** (ou pressione `Ctrl+Alt+Shift+S`).

2. **Navegar até Módulos**  
   No painel da esquerda, selecione **Módulos**.

3. **Selecionar o Módulo**  
   No painel central, selecione o módulo específico em que você está trabalhando. Caso não exista, clique em "+", e adicione o diretório de trabalho como módulo Java.

4. **Ir para a Aba Fontes (Sources)**  
   Clique na aba **Fontes**.

5. **Marcar a Pasta como Recursos**
    - Localize, na árvore de diretórios, a pasta que você deseja copiar para o JAR (por exemplo, `programas/`).
    - Clique com o botão direito na pasta e selecione **Marcar Diretório Como > Raiz de Recursos (Resources Root)**.

6. **Definir Caminho Relativo de Saída**
    - Os recursos precisam ser colocados em um subdiretório específico dentro do JAR.
    - Selecione a pasta marcada como Resources Root.
    - Clique no ícone de lápis próximo ao caminho da pasta.
    - Digite o caminho relativo desejado no campo **Caminho Relativo de Saída** (por exemplo, `programas`).

7. **Aplicar e Fechar**  
   Clique em **Aplicar** e depois **OK** para salvar as alterações.
