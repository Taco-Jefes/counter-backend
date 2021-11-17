#! /bin/bash
sudo yum update -y
sudo rpm --import https://yum.corretto.aws/corretto.key
sudo curl -L -o /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo
sudo yum install -y java-17-amazon-corretto-devel
wget https://aws-project-jar-bucket.s3.amazonaws.com/tacos-eaten-0.0.1-SNAPSHOT.jar
DB_HOST="terraform-20211117201202191800000001.cpxqvf2wpeui.us-east-1.rds.amazonaws.com" DB_NAME="tacodb" DB_PWD="tacotaco" DB_USER="postgres" java -jar tacos-eaten-0.0.1-SNAPSHOT.jar
