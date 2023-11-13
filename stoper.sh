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

image_id=$(sudo docker ps | grep "$IMAGE_NAME" | awk '{print $1}')

sudo docker stop $image_id

sudo docker rm $image_id