Publicise
=========

Documents
=========
Gets all available documents

```bash
GET <base_url>/documents
```

###parameter
####fields
filters all fields but defined 

> fields=<list>

```bash
GET <base_url>/documents/?fields=id,resourceUri
```

####limit
limits the number of results

> limit=<number>

```bash
GET <base_url>/documents/?limit=5
```

####offset
sets the result offset. starts at `0`

> offset=<number>

```bash
GET <base_url>/documents/?offset=1
```
