FTP_USER=cadox8@cadox8.cf
FTP_PASSWORD=123abc4d
BRANCH_A_DEPLOY=master
NO_DEPLOY_MESSAGE=NoDeploy

if [[ "$TRAVIS_COMMIT_MESSAGE" == *"$NO_DEPLOY_MESSAGE"* ]]; then
  echo "Esta commit ha sido marcada como no lista para subir al servidor"
else
    # Solo deploy al server si el branch es el deseado
    if [ "$TRAVIS_BRANCH" == "$BRANCH_A_DEPLOY" ]; then
        for file in *.jar; do
            echo  Subiendo $file al servidor [185.116.215.111]
            curl --ftp-create-dirs -T $file -u $FTP_USER:$FTP_PASSWORD ftp://185.116.215.111/public_html/repo/pa/$file
        done
        echo " Todos los archivos han sido subidos"
    else
        echo "El branch $TRAVIS_BRANCH no está preparado para ser subido al servidor"
    fi
    echo "Deploy al servidor terminado"
fi
