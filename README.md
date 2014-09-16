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
Filters all fields but defined 

> fields={list}

```bash
GET <base_url>/documents/?fields=id,resourceUri
```

####limit
Limits the number of results

> limit={number}

```bash
GET <base_url>/documents/?limit=5
```

####offset
Sets the result offset. Starts at `0`

> offset={number}

```bash
GET <base_url>/documents/?offset=1
```
