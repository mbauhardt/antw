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
/usr/bin/env git clone ${url} ${sourceDir}
/usr/bin/env pushd ${sourceDir} >/dev/null
/usr/bin/env git checkout --track -b ${releaseBranch} origin/${releaseBranch}
/usr/bin/env ./gradlew clean installProject
/usr/bin/env popd >/dev/null
 