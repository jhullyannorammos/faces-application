
/* Em SQL, podemos fazer uma consulta que busque automóveis cujo ano seja maior ou igual a 2010: */
select * from T_Automoveis where ano_modelo >= 2010;

/*No SQL, utilizamos o nome da tabela e o nome da coluna. Contudo, para essa mesma pesquisa na JPQL, precisaremos usar apenas as informações da entidade:*/
select a from Automovel a where a.anoModelo >= 2010;

select * from Automovel auto 
left outer join Modelo modelo on auto.modelo_id = modelo.id
left outer join Marca marca on modelo.marca_id = marca.id
where marca.nome = 'Ferrari';

select * from Marca marca
left outer join Modelo modelo on marca.id = modelo.marca_id
where modelo.descricao like '%911%';

/*Para esse fim temos o join fetch. Usando essa instrução faze-mos uma consulta como se fosse fetch=EAGER no relacionamento. Nossa consulta
então ficaria assim:*/
select marca from Marca marca join fetch marca.modelos;

select a from Automovel a where a.anoModelo > (select AVG(auto.anoModelo) from Automovel auto);
select a.marca, COUNT(a) from Automovel a GROUP BY a.marca HAVING COUNT(a) > 10;

select m from Marca m;
select marca from Marca marca join marca.modelos modelo where modelo.cilindradas > 500;
select marca from Marca marca where EXISTS (select a from Automovel a where a.modelo.marca = marca and a.preco >= 1000000);
/*INDEX(obj): Retorna a posição de um determinado elemento quando ele estiver em uma lista ordenada:*/
select candidato from Concurso concurso join concurso.candidatosAprovados candidato where INDEX(candidato) < 5;

