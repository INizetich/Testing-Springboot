FROM ubuntu:latest
LABEL authors="Tomas"

ENTRYPOINT ["top", "-b"]