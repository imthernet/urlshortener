# App for shortening URLs

# Cassandra management

## Create keyspace

```shell
docker exec -it cassandra-db bash -c "cqlsh -u cassandra -p cassandra"
CREATE KEYSPACE app_keyspace WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
exit;
```

Adjust replication_factor for number of nodes.

