set -e;
sudo apt update -q;
sudo apt install -y git-crypt;
(base64 --decode < GIT_CRYPT) | git-crypt unlock /dev/stdin;
