# Using HTTPIE

Here are the following curl requests I did:

```
GET /get_roles HTTP/1.1
Host: localhost:8083
User-Agent: HTTPie
```

```
POST /create_user?idRole=2&username=tata&password=1234 HTTP/1.1
Content-Length: 0
Host: localhost:8084
User-Agent: HTTPie
```

```
POST /connect?username=helper&password=1234 HTTP/1.1
Content-Length: 0
Host: localhost:8084
User-Agent: HTTPie
```

```
GET /get_user?id=1 HTTP/1.1
Host: localhost:8084
User-Agent: HTTPie
```

```
POST /create_request?idUser=1&message=Hello+World%21&token=BPKlLSR86G_73Oej9cfoiD_3lYlB8S9kV1czL0AQnIqFThyLB_vz13-t_QoqwrpjBPvRDrlboqiVvaKZBBUP-9qVHbXD-RGqY87fuFjKJ6YE1m-aBoHs9WqaTRDsNeMi HTTP/1.1
Content-Length: 0
Content-Type: application/json
Host: localhost:8082
User-Agent: HTTPie
```

```
PUT /change_status?idUser=3&idMessage=1&status=5&token=IV41wX5OqflIsmayFilN-D_iB_G8Fk_Bbrm_vaKEMOSVjog1sN2VnLWJ25LhmKeZawgOMc7boE_bC4_vmEnRNPcwQ6L-FYFiAPbc33GumL5n588bhLMUyfFkDxsbdLkJ HTTP/1.1
Content-Length: 0
Host: localhost:8082
User-Agent: HTTPie
```

```
GET /get_request HTTP/1.1
Host: localhost:8082
User-Agent: HTTPie
```

```
POST /create_feedback?idUser=2&idRequest=1&message=No+problem+bro%21&token=u-xBlJnbRnlQF5BMwrYV2bQc9NrCwAf4i2ywcRWbCT9-1skheL25qG3NyK4sCHaGgYAGJQtNCh4LMjnOPDDyvj-F9NDwSo04ttT2ra1rR0XymdjCUSDgFJeBJHB37S1x HTTP/1.1
Content-Length: 0
Host: localhost:8081
User-Agent: HTTPie
```

```
GET /get_feedback?idRequest=1 HTTP/1.1
Host: localhost:8081
User-Agent: HTTPie
```