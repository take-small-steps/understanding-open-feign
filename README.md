# 코드 분석


빌드 시점에

`class FeignClientsRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware`

FeignClientsRegistrar 를 통해 @EnableFeignClient 와 @FeignClient 를 찾아 빈 등록을 한다.

```java
public void registerFeignClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
		Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableFeignClients.class.getName());
		final Class<?>[] clients = attrs == null ? null : (Class<?>[]) attrs.get("clients");
		if (clients == null || clients.length == 0) {
			ClassPathScanningCandidateComponentProvider scanner = getScanner();
			scanner.setResourceLoader(this.resourceLoader);
			scanner.addIncludeFilter(new AnnotationTypeFilter(FeignClient.class));
			Set<String> basePackages = getBasePackages(metadata);
			for (String basePackage : basePackages) {
				candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
			}
		}
		else {
			for (Class<?> clazz : clients) {
				candidateComponents.add(new AnnotatedGenericBeanDefinition(clazz));
			}
		}

		for (BeanDefinition candidateComponent : candidateComponents) {
			if (candidateComponent instanceof AnnotatedBeanDefinition beanDefinition) {
				// verify annotated class is an interface
				AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
				Assert.isTrue(annotationMetadata.isInterface(), "@FeignClient can only be specified on an interface");

				Map<String, Object> attributes = annotationMetadata
						.getAnnotationAttributes(FeignClient.class.getCanonicalName());

				String name = getClientName(attributes);
				String className = annotationMetadata.getClassName();
				registerClientConfiguration(registry, name, className, attributes.get("configuration"));

				registerFeignClient(registry, annotationMetadata, attributes);
			}
		}
	}
```


```java
	private void registerFeignClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
			Map<String, Object> attributes) {
		String className = annotationMetadata.getClassName();
		if (String.valueOf(false).equals(
				environment.getProperty("spring.cloud.openfeign.lazy-attributes-resolution", String.valueOf(false)))) {
			eagerlyRegisterFeignClientBeanDefinition(className, attributes, registry);
		}
		else {
			lazilyRegisterFeignClientBeanDefinition(className, attributes, registry);
		}
	}
```

FeignClient 를 eager 로 빈 등록하는 방식과 lazy 하게 빈등록하는 방법이 있다.

차이는 무엇인가?

`FeignClientFactoryBean` ?

vs

`BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactoryBean.class);` ??


문서를 보니 

`For Spring Cloud Contract test integration, lazy attribute resolution should be used.`

Spring Cloud Contract 테스트를 위해 lazy 를 사용해야 한다. 

https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#aot-and-native-image-support

또한 여기에서 lazy 을 확인할 수 있는데, AOT transformations and native images 을 사용하기 위해서이다.

AOT 에 대해서는 추후에 좀더 알아보자.

---


동작될 때는 `FeignInvocationHandler` 에서 처리하게 된다.  InvocationHandler 인걸 보니까 AOP 의해 동작되는 것으로 보인다.

해당 Invocation 을 따라가면

executeAndDecode 을 만날 수 있고, Request 처리를 한다.

--

트러블 슈팅

`StringBuilder builder = new StringBuilder().append(response.body());`
StringBuilder 에 따라 오류를 일으킬 수 있는데, Feign 은 로깅 레벨을 Application.yml 에서 설정할 수 있다. Response.getBody() 해서 가져온 데이터가 

기대했던 String 값이 아닐 수 있다.


---

