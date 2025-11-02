# 📒 Agenda de Amigos

Aplicação desenvolvida para a disciplina **Programação Orientada a Objetos** da **Faculdade de Engenharia de Sorocaba (FACENS)**.  
O projeto consiste em uma agenda digital que permite **cadastrar, listar, buscar e consultar aniversários de amigos**, aplicando conceitos fundamentais de **orientação a objetos** em Java.

## 🎯 Objetivos do Projeto

- Aplicar os conceitos de **classes, objetos, encapsulamento e herança**.
- Demonstrar o uso de **ArrayList** para gerenciamento dinâmico de dados.
- Implementar **métodos de manipulação e formatação de dados** com boas práticas de programação.
- Integrar **entrada e saída de dados via JOptionPane**, tornando a interface simples e funcional.
- Utilizar **JavaDoc** para padronizar a documentação de código.

## 🧩 Estrutura do Projeto
```
src/
└── org/sant/
├── Agenda.java # Classe principal (UI e fluxo do programa)
├── service/
│ └── GerenciarAmigo.java # Lógica de negócios e gerenciamento de amigos
├── domain/
│ └── entity/
│ └──Amigo.java # Representa o amigo (dados pessoais e cálculos)
│ └──Endereco.java # Representa o endereço do amigo
```

## ⚙️ Funcionalidades

| Funcionalidade | Descrição |
|----------------|------------|
| **Cadastrar amigo** | Permite adicionar um novo amigo com nome, celular, e-mail, endereço e data de nascimento. |
| **Buscar amigo pelo nome** | Exibe as informações completas de um amigo, incluindo quantos dias faltam para seu aniversário. |
| **Listar aniversariantes do mês** | Mostra todos os amigos que fazem aniversário no mês informado. |
| **Listar todos os amigos** | Exibe todos os contatos cadastrados com separadores. |
| **Sair** | Encerra o programa com uma mensagem final. |

## 🧠 Conceitos Aplicados

- **Encapsulamento** – uso de getters/setters e classes independentes.
- **Composição de objetos** – `Amigo` contém um objeto `Endereco`.
- **Coleções (ArrayList)** – armazenamento dinâmico de amigos.
- **Streams e Lambdas** – para filtragem e formatação.
- **API java.time e java.util.GregorianCalendar** – para cálculos de aniversário.
- **Tratamento de exceções** – via `try/catch` e `Optional`.
- **Boas práticas de validação e SRP (Single Responsibility Principle)**.

## 🖥️ Execução

### ✅ Pré-requisitos
- **Java 17+**
- **IDE** (IntelliJ IDEA, Eclipse, NetBeans ou VS Code com extensão Java)

### ▶️ Passos
1. Clone este repositório:
   ```bash
   git clone https://github.com/seuusuario/agenda-de-amigos.git
   ```
2. Abra o projeto na sua IDE.
3. Compile e execute a classe principal:
    ``` 
    org.sant.Agenda
    ```
4. Interaja com a aplicação via janelas de diálogo (JOptionPane).

## 📚 Documentação Técnica (JavaDoc)

Toda a aplicação possui documentação JavaDoc detalhada, cobrindo:

- Descrição de classes, métodos e atributos.
- Explicações sobre parâmetros (@param), retornos (@return) e exceções (@throws).
- Relações entre classes (@see, @link).

Para gerar a documentação localmente:
```bash
javadoc -d docs -sourcepath src org.sant
```

## 🧾 Licença

Este projeto foi desenvolvido com fins acadêmicos e educacionais sob a para a disciplina de Programação Orientada a Objetos.
Distribuição livre para fins de aprendizado.