# Trabalho Prático AEDS III

## Nome dos integrantes

 - Antônio Drumond Cota de Sousa
 - Laura Menezes Heráclito Alves
 - Davi Ferreira Puddo
 - Raquel de Parde Motta
   
## Links dos trabalhos anteriores
- [TP1](https://github.com/AntonioDrumond/AED3_TP01/)

## Introdução

Neste segundo trabalho prático da disciplina, o sistema PUCFlix foi expandido para incorporar o gerenciamento de Atores e, principalmente, para implementar um relacionamento N:N entre as entidades Séries e Atores. O objetivo central foi modelar e implementar a capacidade de associar múltiplos atores a múltiplas séries, refletindo cenários reais onde um ator participa de várias produções e uma produção possui vários atores

Para gerenciar essa relação bidirecional de forma eficiente, foram utilizadas duas Árvores B+ como estruturas de índice. Uma árvore organiza os dados pelo par (idSerie, idAtor), permitindo consultas rápidas sobre quais atores participam de uma determinada série. A segunda árvore organiza pelo par (idAtor, idSerie), possibilitando a consulta inversa: em quais séries um determinado ator trabalhou. Além da implementação do relacionamento, foi desenvolvido o CRUD completo (Criar, Ler, Atualizar, Excluir) para a entidade Ator.

A integração dessa nova funcionalidade seguiu o padrão MVC (Modelo, Visão, Controle), mantendo a separação de responsabilidades. A interface do usuário foi atualizada para incluir um menu dedicado aos Atores e funcionalidades dentro do menu de Séries para vincular e desvincular atores. Foram implementadas regras de consistência para garantir a integridade referencial, como impedir a exclusão de um ator que esteja vinculado a alguma série e remover automaticamente os vínculos ator-série quando uma série é excluída.

## Experiência

Este trabalho consolidou e expandiu a experiência adquirida no TP1. Tendo já estabelecido a base do sistema com o relacionamento 1:N e as estruturas de dados iniciais (Tabela Hash e Árvore B+), o foco agora foi na complexidade adicional do relacionamento N:N. A necessidade de gerenciar duas estruturas de índice (as duas Árvores B+) para o mesmo relacionamento foi um aprendizado prático importante sobre como garantir consultas eficientes em ambas as direções (Série -> Atores e Ator -> Séries).

A manipulação simultânea das duas Árvores B+ para manter a consistência dos vínculos durante as operações de inclusão (de vínculo), exclusão de série e exclusão de ator (com verificação) reforçou a importância de um design cuidadoso das operações de atualização de dados.

A separação clara entre Modelo, Visão e Controle novamente facilitou o desenvolvimento e a depuração, permitindo que o grupo trabalhasse em partes distintas do sistema com menos conflitos. A experiência prática com Árvores B+ foi aprofundada, agora utilizando-as especificamente para gerenciar relações complexas, o que proporcionou uma visão mais concreta de sua aplicabilidade além da indexação primária.

## Desafios

O principal desafio técnico deste trabalho foi a correta implementação e gerenciamento do relacionamento N:N utilizando duas Árvores B+. Garantir que ambas as árvores fossem atualizadas atomicamente (ou pelo menos consistentemente) em todas as operações de vínculo e desvínculo foi complexo. Por exemplo, ao associar um ator a uma série, era necessário inserir o par (idSerie, idAtor) na primeira árvore e o par (idAtor, idSerie) na segunda. Da mesma forma, a exclusão de uma série exigia a remoção de todas as entradas correspondentes em ambas as árvores.

Outro desafio foi a implementação das regras de integridade referencial de forma eficiente. A verificação que impede a exclusão de um ator caso ele esteja vinculado a alguma série exigiu uma consulta em uma das Árvores B+ ((idAtor, idSerie)) antes de permitir a operação de exclusão do ator. Implementar essa lógica de forma integrada ao fluxo do CRUD de Atores exigiu atenção aos detalhes.

## Testes e validação

Durante o desenvolvimento, realizamos testes manuais e automatizados para garantir a integridade das operações e a consistência dos dados. Foram simulados diferentes cenários de uso, como tentativa de exclusão de atores vinculados, vinculações duplicadas e remoção de séries com múltiplos atores associados. 

## Resultados

Conseguimos implementar com sucesso todas as funcionalidades requeridas para este trabalho prático. O sistema PUCFlix agora gerencia não apenas Séries e Episódios, mas também Atores, e implementa corretamente o relacionamento N:N entre Séries e Atores. As principais funcionalidades entregues incluem:

- CRUD completo para Atores: É possível incluir, buscar, alterar e excluir atores, respeitando as regras de negócio.
- Relacionamento N:N com Duas Árvores B+: O vínculo entre séries e atores é gerenciado por duas Árvores B+, garantindo consultas eficientes em ambas as direções.
- Vinculação Série-Ator: Implementamos a funcionalidade que permite associar atores existentes a uma série (integrada ao menu de Séries).
- Consultas Bidirecionais: O sistema permite visualizar facilmente todos os atores de uma determinada série e todas as séries nas quais um determinado ator participou.
- Consistência de Dados: As regras de integridade referencial foram implementadas:
	- A exclusão de um ator só é permitida se ele não estiver vinculado a nenhuma série.
	- A exclusão de uma série remove automaticamente todos os seus vínculos com atores nas duas Árvores B+.

O resultado é um sistema mais robusto e completo, que modela de forma mais realista as relações encontradas em plataformas de streaming. A implementação prática do relacionamento N:N e o aprofundamento no uso de Árvores B+ foram os principais ganhos técnicos e de aprendizado deste trabalho. O sistema está funcional e atende aos requisitos especificados.

## Checklist

- [x] As operações de inclusão, busca, alteração e exclusão de atores estão implementadas e funcionando corretamente? **SIM**.

- [x] O relacionamento entre séries e atores foi implementado com árvores B+ e funciona corretamente, assegurando a consistência entre as duas entidades? **SIM**.

- [x] É possível consultar quais são os atores de uma série? **SIM**.

- [x] É posssível consultar quais são as séries de um ator? **SIM**.

- [x] A remoção de séries remove os seus vínculos de atores? **SIM**.
	- Assim como fizemos com episódio do TP01, não é permitido excluir uma série (ou alterar o nome) de uma série que possua atores linkados à ela. Para conveniência do usuário, adicionamos uma opção no MenuAtores de excluir todos os atores ligados à determinada série de uma vez. Assim, o usuário deve realizar essa ação de deletar todos os atores que trabalham na série para ser capaz de excluir a série.

- [x] A inclusão de um ator em uma série em um episódio se limita aos atores existentes? 
	- A remoção de um ator checa se há alguma série vinculado a ele? Como optamos por não fazer uma classe "Atuação", mas sim ligar diretamente a entidade "Ator" à entidade "Série" correspondente logo no seu momento de criação (inserção de novo ator), cada ator já "nasce" obrigatoriamente vinculado a uma série (e, por outro lado, nunca existirá um ator sem série pois não permitimos a exclusão de uma série com atores vinculados a ela).

- [x] O trabalho está funcionando corretamente? **SIM**

- [x] O trabalho está completo? **SIM**

- [x] O trabalho é original e não a cópia de um trabalho de outro grupo? **SIM**
