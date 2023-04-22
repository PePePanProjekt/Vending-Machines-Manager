FROM node as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . ./
ARG configuration=frontend
RUN npm run build --configuration $configuration
#Stage2
FROM docker.io/library/nginx:latest
COPY --from=build /app/dist/frontend/ /usr/share/nginx/html
COPY /nginx-custom.conf /etc/nginx/conf.d/default.conf
COPY /domain.pem /etc/nginx/certs/fullchain.pem
COPY /domain.key /etc/nginx/certs/privkey.pem
ENTRYPOINT ["nginx","-g","daemon off;"]
