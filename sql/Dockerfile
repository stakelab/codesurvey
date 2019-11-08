FROM mysql/mysql-server:latest

RUN mkdir /tmp/seeding

COPY structure.sql /tmp/seeding
COPY seed.sql /tmp/seeding

RUN echo '#!/usr/bin/sh' > /docker-entrypoint-initdb.d/initialize.sh
RUN echo 'mysql -u root --password="$MYSQL_ROOT_PASSWORD" --database="$MYSQL_DATABASE" < /tmp/seeding/structure.sql' >> /docker-entrypoint-initdb.d/initialize.sh
RUN echo 'mysql -u root --password="$MYSQL_ROOT_PASSWORD" --database="$MYSQL_DATABASE" < /tmp/seeding/seed.sql' >> /docker-entrypoint-initdb.d/initialize.sh
RUN chmod +x /docker-entrypoint-initdb.d/initialize.sh

WORKDIR /
