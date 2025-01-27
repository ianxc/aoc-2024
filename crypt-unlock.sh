set -e;
git-crypt unlock <(echo "$GIT_CRYPT" | base64 --decode);
