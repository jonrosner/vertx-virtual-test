# Vert.x virtual threads database test

Start a postgres using docker:

```bash
docker run --name test -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=db -d postgres:alpine
```

Add a table:

```sql
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);
```
