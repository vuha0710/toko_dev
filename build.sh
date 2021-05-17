#!/bin/bash

REGION="ap-southeast-1"
ACCOUNT_ID="266564908162"
SERVICE="backoffice_service"
USERNAME="AWS"
CWD=$(pwd)

# Build image
#aws ecr get-login-password --region ap-southeast-1 | docker login --username $USERNAME --password-stdin $ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com
docker build -t $ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com/$SERVICE .
#docker push $ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com/$SERVICE

