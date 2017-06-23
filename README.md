# docker-workshop

Testing the application by building and running locally (without Docker):

```bash
mvn spring-boot:run
```

Open http://localhost:8080/greeting

## Using with Docker

This section is given in a seperate presentation for the workshop.

## Endpoints

- `/`: Index page
- `/fail`: Terminate the application (simulate crash)
- `/greeting`: Shows a hello message
- `/health`: Shows health status

## Resources

- `Dockerfile`: https://docs.docker.com/engine/reference/builder/
- `docker build`: https://docs.docker.com/engine/reference/commandline/build/
- `docker run`: https://docs.docker.com/engine/reference/run/
- `docker-compose`: https://docs.docker.com/compose/reference/overview/ and https://docs.docker.com/compose/compose-file/
- docs.docker.com/engine/userguide/eng-image/dockerfile_best-practices/

## Workshop cases

### Cloning the repo

```bash
git clone https://github.com/capraconsulting/docker-workshop-app.git
cd docker-workshop-app
```

---

### Running application

#### Build outside Docker

```bash
# Build executable (shaded) jar
mvn clean package

# Check for jar file
ls target
```

#### Run outside Docker

```bash
# Run the application
java -jar target/gs-rest-service-0.1.0.jar

# Open in web browser
# http://localhost:8080/
```

#### Build and run Docker image

```bash
# Build image, save as 'workshop'
docker build -t workshop .

# Verify image is built by looking at images we have available
docker images

# Run application
docker run -it -p 8080:8080 workshop

# Open in web browser
# http://localhost:8080/
# http://<docker-machine-ip>:8080/ for windows

# Terminate by Ctrl+C later
# Leave open for now
```

#### Looking at the container

```bash
# List running containers
# Notice ID of container
docker ps

# Look at details about the container
# Replace <container> by first part of sha ID or the name the container has received
docker inspect <container>

# Look at resources in use by running containers
docker stats
```

##### Looking inside the Container

```bash
# Start shell in the running container
docker exec -it <container> sh

# Look at running processes
ps

# Look around in the filesystem
cd /
ls

# Look at memory details
free -m

# Look at IP details
ip a

# Check which user you are
id
```

#### Stopping the container

```bash
# Press Ctrl+C in the container
# or

# Stop the container
docker stop <container-id>
```

#### Looking at the stopped container

```bash
docker ps -a
```

#### Remove the container

You can reference the container by either its name or
part or whole container ID.

```bash
docker rm <container>
```

---

### Distribution of images

For this section you will need credentials provided for running `docker login`.
It will be given seperately.

#### Modify the application

E.g. modify `src/resources/index.html` and put some content there.

#### Build application and Docker image

See previous sections. A short recap:

```bash
mvn clean package
docker build -t workshop .
```

#### Push image to registry

```bash
# Tag the image
# Replace <your-string> with something of your choice
docker tag 427085930992.dkr.ecr.eu-west-1.amazonaws.com/workshop:<your-string>

# Push the image
docker push 427085930992.dkr.ecr.eu-west-1.amazonaws.com/workshop:<your-string>
```

#### Pull image from another machine

```bash
# Don't really need this first time, `docker run` will do this for us
docker pull 427085930992.dkr.ecr.eu-west-1.amazonaws.com/workshop:<your-string>

# Run the app and open the web browser
docker run -it -p 8080:8080 427085930992.dkr.ecr.eu-west-1.amazonaws.com/workshop:<your-string>
```

#### Do another change and repeat

Try change something more and push and pull the new build.

---

### Docker as a Development Tool

#### Using a postgres database by using Docker

```bash
# Run a postgres container
docker run -it --name mydatabase postgres

# Note: We are giving it a name. When it stops, we can't `docker run` it again
# as it already exists, but we can start it again with `docker start mydatabase`
# or delete it with `docker rm mydatabase` before trying to run again.

# Start the application and connect to the database
docker run -it -p 8080:8080 --link mydatabase -e SPRING_DATASOURCE_URL=jdbc:postgresql://mydatabase/postgres -e SPRING_DATASOURCE_USERNAME=postgres workshop

# Start another application on another port
docker run -it -p 8081:8080 --link mydatabase -e SPRING_DATASOURCE_URL=jdbc:postgresql://mydatabase/postgres -e SPRING_DATASOURCE_USERNAME=postgres workshop

# Verify the greeting endpoint return value stay in sync between the two servers.
```

#### docker-compose

When using docker-compose the file `docker-compose.yml` will be read
which contains the setup for this application.

```bash
# Start a database service in background
docker-compose up -d database

# Build a new version of the application
mvn clean package
docker-compose build app

# Start the application
docker-compose up -d app

# Try run a shell in the application container
docker-compose exec app sh

# Start two more instances of the application
docker-compose scale app=3

# Find out which ports are in use
# Open the web browser at the ports and look at the greeting endpoints
docker-compose ps

# After finishing, bring down all services and clean up
# Note: -v deletes any data volumes as well (the database)
docker-compose down -v
```

#### Build environment with Docker

Using same image as Jenkins.

```bash
# Build image
docker build -t workshop-dev -f jenkins/Dockerfile .

# Run image and mount working directory
# Also publish port so we can run application as well
docker run -it -v $PWD:/usr/src/app -p 8080:8080 workshop-dev

# You are now inside the container, all following commands should be run inside container

# Build the application using provided Maven in container
mvn clear package

# Build again, notice cache is present
mvn clear package

# Run the application
# We are using java provided by the container!
# Verify you can reach the web page at port 8080
java -jar target/

# Exit the application and container
```

---

### Docker as the Deployment Unit

```bash
# Good vs. bad images
# Build Dockerfile-bloated and compare with usual Dockerfile
docker build -t workshop-bloated -f Dockerfile-bloated .

# Inspecting image
docker inspect <image>

# Looking at history
docker history --no-trunc <image>

# Visualize images
docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock nate/dockviz images -t -i
```

---

### Cleanup

```bash
# Since newer versions of Docker this is more easy
# This is a bit of a "magic" command
docker system prune

# More manual cleanup

# Listing exited containers
docker ps -f "status=exited"

# Deleting a container
docker rm <container>

# List images not tagged (usually replaced by newer images)
docker images -f dangling=true

# Delete a image
docker rmi <image>

# List volumes not referenced
docker volume ls -f dangling=true

# Deleting a volume
docker volume rm <volume>

# Example of chaining
# Delete all dangling images
docker images -f dangling=true -q | xargs --no-run-if-empty docker rmi
```
