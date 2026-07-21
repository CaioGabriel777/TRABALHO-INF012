# ---------- build ----------
FROM node:20-alpine AS build
WORKDIR /app

# URLs dos microservicos usadas no build (React embute em build time).
# Como o browser roda na maquina do usuario, aponta para as portas do host.
ARG REACT_APP_INVENTORY_URL=http://localhost:8081
ARG REACT_APP_CLIENTE_URL=http://localhost:8082
ARG REACT_APP_COMPRAS_URL=http://localhost:8083
ENV REACT_APP_INVENTORY_URL=$REACT_APP_INVENTORY_URL
ENV REACT_APP_CLIENTE_URL=$REACT_APP_CLIENTE_URL
ENV REACT_APP_COMPRAS_URL=$REACT_APP_COMPRAS_URL

COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

# ---------- serve ----------
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
