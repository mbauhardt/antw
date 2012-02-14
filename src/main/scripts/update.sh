rootDir=~/.antw
sourceDir=${rootDir}/source

pushd ${sourceDir} >/dev/null
git pull
./gradlew clean installProject
popd >/dev/null
