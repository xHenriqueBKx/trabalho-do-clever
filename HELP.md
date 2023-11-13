dependências necessárias:
	mvn (maven do java)
	java -jar
	docker

extensões VsCode:
	Java extension pack
	Java Language Support
	Language Support for Java
	Maven for Java
	Project Manager for Java
	Spring Boot Dashboard
	Spring Boot Extension Pack
	Spring Boot Tools
	Spring Initializr Java Support
	Test Runner for Java

SÓ linux

para iniciar precisar escrever o terminal:
	sudo sh ./starter.sh
	
para encerrar:
	sudo sh .stoper.sh
	
	
	

SPRING BOOT:
	demo
	  |--- target
	  |     |----demo-0.0.1-SNAPSHOT.war   (para executar: java -jar arq.war || para atualizar: mvn package)
	  |
	  |--- src
	        |----main
	              |--- resources
	              |      |-------- templates -> index.html
                  |      |-------- static    -> outros arquivos secundarios
      			  |
      			  |--- java
      			        |--- com
      			              |-- app
      			              |    |-- App.java (principal)
      			              |
      			              |-- controller
      			                     |------- UserController.java (router)
      			                     |------- CorpoRequest.java (Obj de referencia para request)    

