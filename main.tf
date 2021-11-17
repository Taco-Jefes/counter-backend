terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region = "us-east-1"
}

# Create a VPC
resource "aws_vpc" "aws-project-vpc" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "aws-project-vpc"
  }
}

# Create a subnet
resource "aws_subnet" "aws-project-subnet-private" {
  vpc_id                  = aws_vpc.aws-project-vpc.id
  cidr_block              = "10.0.2.0/24"

  tags = {
    Name = "aws-project-subnet-private"
  }
}

resource "aws_subnet" "aws-project-subnet-public" {
  vpc_id                  = aws_vpc.aws-project-vpc.id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = true

  tags = {
    Name = "aws-project-subnet-public"
  }
}

# Create an internet gateway
resource "aws_internet_gateway" "aws-project-gateway" {
  vpc_id = aws_vpc.aws-project-vpc.id

  tags = {
    Name = "aws-project-gateway"
  }
}

# Create a route table
resource "aws_route_table" "aws-project-route-table" {
  vpc_id = aws_vpc.aws-project-vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.aws-project-gateway.id
  }

  tags = {
    Name = "aws-project-route-table"
  }
}

# Route table association
resource "aws_route_table_association" "a" {
  subnet_id      = aws_subnet.aws-project-subnet-public.id
  route_table_id = aws_route_table.aws-project-route-table.id
}

# Create security group
resource "aws_security_group" "aws-project-security-group-db" {
  name        = "allow_inbound_db"
  description = "Allow inbound traffic"
  vpc_id      = aws_vpc.aws-project-vpc.id

  ingress {
    description = "Backend API Connection to DB"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["10.0.2.0/24"]
  }

  ingress {
    description = "SSH from public subnet"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["10.0.1.0/24"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "aws-project-security-group-db"
  }
}

# Create security group
resource "aws_security_group" "aws-project-security-group-backend" {
  name        = "allow_inbound_be"
  description = "Allow inbound traffic and db connection"
  vpc_id      = aws_vpc.aws-project-vpc.id

  ingress {
    description = "Backend API Connection to DB"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    security_groups = [aws_security_group.aws-project-security-group-db.id]
#    cidr_blocks = ["10.0.2.0/24"]
  }

  ingress {
    description = "SSH from public subnet"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["10.0.1.0/24"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "aws-project-security-group-backend"
  }
}