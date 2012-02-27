#!/bin/bash

url=git@github.com:mbauhardt/antw.git
rootDir=~/.antw
sourceDir=${rootDir}/source
releaseBranch=latest

if [ -d ${rootDir} ]
then
  echo "You already have antw installed. You'll need to remove ${rootDir} if you want to install."
  exit
fi

echo "Cloning antw..."
git clone ${url} ${sourceDir}
pushd ${sourceDir} >/dev/null
git checkout --track -b ${releaseBranch} origin/${releaseBranch}
./gradlew clean installProject
popd >/dev/null
 