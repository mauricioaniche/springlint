# SpringLint

O SpringLint é uma ferramenta simples que executa métricas de código e análise de [Bad Smells](http://vschettino.com.br/blog/refatoracao_principais_bad_smells) em seus sistemas Spring.

A ferramenta é baseada de PHD de Maurício Aniche.

## Uso

```
java -jar springlint
-o,--output <arg> Caminho do diretório para a saída.
-otype <arg> Tipo de saída: 'csv', 'html'
-p,--project <arg> Caminho do projeto.

```

Ou utilizando o maven:

```
mvn com.github.mauricioaniche:springlint-maven-plugin:0.5:springlint

```

Agora basta verificar o html gerado.

## Interpretando as métricas de código

O Springlint compara suas classes com milhares de outras classes de referência, extraidas de 120 sistemas Spring MVC.

- Quadrados que estao com a cor vermelha escura, significam que sua classe está dentro de 10% das piores classes (classes com valores mais altos) identificados nos sistemas.

- Quadrados com a cor vermelho claro (10-20%), amarelo (20-30%) e verde (30% -100%), são as outras categorias.

Além disso, o tamanho do quadrado é proporcional ao valor da métrica: quanto maior o quadrado, maior o valor da métrica. Na prática, você deve se preocupar com as classes vermelhas.

Diferente de outras ferramentas, o SpringLint usa diferentes limites para cada camada da arquitetura MVC, por exemplo, se a classe é um Controlador, ele usa [26,29,34] como limites para o acoplamento, enquanto que para as Entidades [16,20,25].

Usamos as seguintes métricas da [suíte CK](https://github.com/mauricioaniche/ck):

-  **Weighted Methods Per Class (WMC)**. De forma clara, é a soma da complexidade de cada método em uma classe. Geralmente representada pelo algoritmo desenvolvido por [Thomas J. McCabe em 1976](https://pt.wikipedia.org/wiki/Complexidade_ciclom%C3%A1tica), ela mede a quantidade de caminhos de execução independentes de cada método. 

-  **Number of Methods (NOM)**. O número de métodos que existem em uma classe também é outra métrica comum para explicar a complexidade de uma classe. Algumas ferramentas usam NOM ao invés do algoritmo de Thomas J. McCabe para representar WMC.

-  **Coupling Between Object Classes (CBO)**. O número de classes que uma classe depende. A conta leva em consideração classes usadas tanto por bibliotecas externas quanto classes do próprio projeto.

-  **Response for a Class (RFC)**. É a contagem de todas as invocações de método que ocorrem em uma classe.

-  **Lack of Cohesion of Methods (LCOM)**. Como o nome sugere, mede a falta de coesão em uma classe. A ideia por trás da métrica é contar o número de interseções entre métodos e atributos. Quanto maior o número, menos coesa é a classe.

## Interpretando os Bad Smells

Springlint também detecta diferentes bad smells em sistemas Spring MVC. Atualmente são suportadas 6:

-  **Promiscuous Controller**: Quando um Controller ofereçe muitas ações.
-  **Brain Controller**: Quando um Controller também ofereçe muito controle de fluxo.
-  **Meddling Service**: Um Service que acessa diretamente o banco de dados.
-  **Brain repositories**: Lógica complexa nos Repositories.
-  **Laborious Repository Method**: Um método em um Repository que executa múltiplas ações no banco de dados.

-  **Fat Repository**: Um Repository que gerência muitas entidades.

Nossos estudos mostram que as classes afetadas por estes bad smells são mais propenças a mudança e defeitos. Então, você precisa se livrar deles.

## Trabalhos relacionados

* Alves, Tiago L., Christiaan Ypma, and Joost Visser. "Deriving metric thresholds from benchmark data."

Software Maintenance (ICSM), 2010 IEEE International Conference on. IEEE, 2010.


* Chidamber, Shyam R., and Chris F. Kemerer. "A metrics suite for object oriented design."

Software Engineering, IEEE Transactions on 20.6 (1994): 476-493.

## Autor

Maurício Aniche (m.f.aniche at tudelft dot nl, @mauricioaniche)

*Colaboradores (não ordenado):*  

- Christoph Treude

- Arie van Deursen

- Andy Zaidman

- Gabriele Bavota

- Marco Aurélio Gerosa  

## Licença

This software is licensed under the Apache 2.0 License.