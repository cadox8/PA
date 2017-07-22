FTP_USER=alpha
FTP_PASSWORD=vivalapepa123
BRANCH_A_DEPLOY=master
NO_DEPLOY_MESSAGE=NoDeploy

if [[ "$TRAVIS_COMMIT_MESSAGE" == *"$NO_DEPLOY_MESSAGE"* ]]; then
  echo "Esta commit ha sido marcada como no lista para subir al servidor"
else
    if [ "$TRAVIS_BRANCH" == "$BRANCH_A_DEPLOY" ]; then
        for file in *.jar; do
            echo Subiendo $file al servidor 217.182.180.247
            curl --ftp-create-dirs -T $file -u $FTP_USER:$FTP_PASSWORD ftp://217.182.180.247/Servidor/Plugins/$file
        done
        echo "Todos los archivos han sido subidos"
    else
        echo "El branch $TRAVIS_BRANCH no está preparado para ser subido al servidor"
    fi
    echo "Deploy al servidor terminado"
fi