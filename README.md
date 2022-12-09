# Service_1

For Consul, we should run it by command: 
`./consul agent -dev`

And add annotation in main method.
`@EnableDiscoveryClient`

And add config.

```
spring.cloud.consul.host=localhost
spring.cloud.consul.port=8500
spring.cloud.consul.discovery.service-name=${spring.application.name}
spring.cloud.consul.discovery.instance-id=${spring.application.name}-${server.port}
spring.cloud.consul.discovery.register-health-check=false
```

If we want to run the second instance, then we just change the config and run it again.