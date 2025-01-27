set -e;
sudo apt update -q;
sudo apt install -y git-crypt;
echo ${{ secrets.GIT_CRYPT }} | base64 --decode | git-crypt unlock /dev/stdin;
