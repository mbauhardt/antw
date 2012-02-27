rootDir=~/.antw
sourceDir=${rootDir}/source

/usr/bin/env pushd ${sourceDir} >/dev/null
/usr/bin/env git pull
/usr/bin/env ./gradlew clean installProject
/usr/bin/env popd >/dev/null
