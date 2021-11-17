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
  availability_zone = "us-east-1e"
  cidr_block              = "10.0.2.0/24"

  tags = {
    Name = "aws-project-subnet-private"
  }
}

# Create a subnet
resource "aws_subnet" "aws-project-subnet-private-2" {
  vpc_id                  = aws_vpc.aws-project-vpc.id
  availability_zone = "us-east-1c"
  cidr_block              = "10.0.3.0/24"

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

resource "aws_db_subnet_group" "aws-project-db-subnet-group" {
  name       = "backend_db_group"
  subnet_ids = [aws_subnet.aws-project-subnet-private.id, aws_subnet.aws-project-subnet-private-2.id]

  tags = {
    Name = "aws-project-db-subnet-group"
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

#resource "aws_eip" "lb" {
#  instance = aws_instance.web.id
#  vpc      = true
#}

# Create NAT gateway
#resource "aws_nat_gateway" "aws-project-nat-gw" {
#  allocation_id = aws_eip.lb.id
#  connectivity_type = "public"
#  subnet_id     = aws_subnet.aws-project-subnet-private.id
#
#  tags = {
#    Name = "aws-project-nat-gw"
#  }

  # To ensure proper ordering, it is recommended to add an explicit dependency
  # on the Internet Gateway for the VPC.
#  depends_on = [aws_internet_gateway.aws-project-gateway]
#}

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
    cidr_blocks = ["10.0.2.0/24", "10.0.1.0/24"]
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

# Launch RDS Instance
resource "aws_db_instance" "aws-project-db" {
  allocated_storage    = 10
  engine               = "postgres"
  instance_class       = "db.t3.micro"
  name                 = "tacodb"
  username             = "postgres"
  password             = "tacotaco"
  parameter_group_name = "default.postgres13"
  db_subnet_group_name = "backend_db_group"
  vpc_security_group_ids = [aws_security_group.aws-project-security-group-db.id]

    tags = {
      Name = "aws-project-db"
    }
}

# Output DB endpoint
output "db-endpoint" {
  value = aws_db_instance.aws-project-db.endpoint
}

# Launch EC2 T2 Micro Instance
resource "aws_instance" "aws-project-ec2-be" {
  ami                         = "ami-04ad2567c9e3d7893"
  instance_type               = "t2.micro"
  subnet_id                   = aws_subnet.aws-project-subnet-private.id
  vpc_security_group_ids      = [aws_security_group.aws-project-security-group-backend.id]
  user_data                   = file("install.sh")
  key_name = "test-db-project"

  tags = {
    Name = "aws-project-ec2-be"
  }
}

output "ec2-backedend-ip" {
  value = aws_instance.aws-project-ec2-be.public_ip
}
