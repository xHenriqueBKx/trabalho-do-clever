#!/bin/bash

if [ "$EUID" -ne 0 ]; then
    echo "TEM QUE USAR SUDO"
    exit 1
fi

if [ -f .env ]; then
    source ./.env || exit 1
else
    echo "Sem Variaveis de Ambiente"
    exit 1
fi

cd ./demo || exit 1

mvn package

if [ $? -ne 0 ]; then
    echo "Erro ao executar o comando: 'mvn package'"
    exit 1
fi

cd ../
cp ./demo/target/demo-0.0.1-SNAPSHOT.war "$(dirname "$(realpath "$0")")"

if sudo docker images | grep "$IMAGE_NAME"; then
    image_id=$(sudo docker images | grep "$IMAGE_NAME" | awk '{print $3}')

    sudo docker rmi $image_id
fi

sudo docker build -t $IMAGE_NAME .

if [ $? -ne 0 ]; then
    echo "Erro ao criar a imagem"
    exit 1
fi

sudo docker run -dp 127.0.0.1:8080:8080 $IMAGE_NAME

rm demo-0.0.1-SNAPSHOT.war

echo -e "\033[0;32mPressione Enter Para Continuar\033[0m"
read

clear

echo "CONTAINER ID   IMAGE        COMMAND                CREATED          STATUS          PORTS                      NAMES"

sudo docker ps | grep $IMAGE_NAME