# Blog API V1
## Simple blog api V1 based on Spring Boot

### Installation guide

#### 1. Run docker command to start postgreSQL database:
``docker run --name=main-pg --env=POSTGRES_USER=postgres --env=POSTGRES_PASSWORD=postgres --env=POSRGRES_DB=postgres -p 5432:5432 postgres``

#### 2. Run BlogJpaApiApplication.class

###### Tables will create automatically by JPA / HIBERNATE

###### Path URL http://localhost:8080/api/v1
