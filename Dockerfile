FROM library/postgres:9.2.19
ADD sctipts/init.sql /docker-entrypoint-initdb.d/
