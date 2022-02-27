# App Name: File-Server:


## Requirements: 
```
1. Upload a file
ex. POST /files/<name>, Content-Type: multipart/form-data
2. Delete a file
ex. DELETE /files/<name>
3. List uploaded files (if a file is uploaded then deleted it should not be listed)
ex. GET /files may return a list of files: [file1.txt, file2.txt, ..]
```

## Programming Language and Framework used:
```
Language: Java 8

Framework: Spring Boot

Build: Maven Project

FileStorage: System File-directory
```
 

## Project Build and Run steps:

1. Make sure atleast Java 8 JRE is available. Open project root directory in a command line and run:

```java
./mvnw package && java -jar target/file-server-0.0.1-SNAPSHOT.jar
```
This will package file-server project into an executable jar file.

2. From project root directory run Docker file from terminal, make sure docker is running in your system:

```
docker build -t fileserver:latest .
```
For details, please refer `Dockerfile` available in root directory of the project

3. Verify that docker image is created:

```
C:\Java-2021\file-server\file-server>docker images
REPOSITORY       TAG       IMAGE ID       CREATED              SIZE
fileserver       latest    4e2f0c7f078a   About a minute ago   122MB
```

4. Run the container and map port (I use default tomcat 8080):

```
C:\Java-2021\file-server\file-server>docker run -p 8080:8080 fileserver
```

5. Make sure tomcat is started and application is running.

```
Tomcat started on port(s): 8080 (http) with context path
```
We can look for above console output from step-4 command excution

## Application execution instructions:

- Upload a file

```
curl -X POST http://localhost:8080/upload/ -F files=@ideas.txt
```

- Upload Result:

```
[{"fileName":"ideas.txt"}]
```

- List Uploaded files

```
curl -X GET http://localhost:8080/files/
```

- List result

```
[{"fileName":"ideas.txt"}]
```

- Delete a file

```
curl -X DELETE http://localhost:8080/delete/ -F files=@ideas.txt
```

- Result of deleted file

```
[{"fileName":"ideas.txt successfully deleted"}]
```

- Try deleting an non-existing file

```
curl -X DELETE http://localhost:8080/delete/ -F files=@ideas.txt
```

- Result of delete of a non-existing file

```
[{"fileName":"ideas.txt not found"}]
```

# Note:
1. I have used `Curl` as a command line tool (CLI) to interact with fileserver endpoints.

2. Time-required: It took me about 3~4 days to complete this project. Per day I was working about 3 hours. Still I was unable to finish integration test for `/delete` operation. I would like to further enhance this to make a second micro-service as a CLI for interation with fileserver endpoints.  
