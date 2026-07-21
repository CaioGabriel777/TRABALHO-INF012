# Frontend — INF012

Interface web (React.js + JavaScript + React Router) para os microserviços Spring Boot do projeto.

## Tecnologias

- React 18 (Create React App)
- React Router 6
- Axios
- CSS puro (sem Tailwind / sem biblioteca de UI)

## Como rodar

```bash
cd frontend
npm install
npm start
```

A aplicação sobe em `http://localhost:3000`.

## Configuração

As URLs dos microserviços ficam no arquivo `.env` (portas expostas pelo `docker-compose.yml`):

```
REACT_APP_INVENTORY_URL=http://localhost:8081   # produtos, categorias, fornecedores
REACT_APP_CLIENTE_URL=http://localhost:8082     # clientes, endereços
REACT_APP_COMPRAS_URL=http://localhost:8083     # compras
```

> Suba o backend antes com `docker compose up` na raiz do projeto.

## ⚠️ CORS

Os serviços Spring Boot ainda **não têm CORS habilitado**. Sem isso o navegador
bloqueia as requisições vindas do `localhost:3000`. Adicione em cada serviço
(`inventory`, `cliente`, `compras`) uma configuração como:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

## Estrutura

```
src/
├── api/          # chamadas HTTP (axios) por recurso
├── components/   # componentes reutilizáveis (layout, produto, cliente, compra)
├── pages/        # uma página por rota
├── hooks/        # hooks de carregamento de dados
├── routes/       # definição das rotas
└── styles/       # CSS global
```
```
