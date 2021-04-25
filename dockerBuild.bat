@echo off

docker rmi -f guilhermejr/api-livros

docker builder prune -f

docker build -t guilhermejr/api-livros . -f docker/Dockerfile

docker push guilhermejr/api-livros

pause
