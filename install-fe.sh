#! /bin/bash
sudo yum update -y
sudo yum install git
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.34.0/install.sh | bash
. ~/.nvm/nvm.sh
nvm install node
git clone https://github.com/Taco-Jefes/taco-jefes-ui.git && cd taco-jefes-ui && npm install
export NODE_OPTIONS=--openssl-legacy-provider
npm run build
npm install -g serve
sudo yum install libcap-devel -y
sudo setcap cap_net_bind_service=+ep `readlink -f \`which node\``
(serve -s build -l 80&)
